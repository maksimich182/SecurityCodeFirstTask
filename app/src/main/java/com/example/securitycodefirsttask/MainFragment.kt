package com.example.securitycodefirsttask

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageState
import android.provider.DocumentsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class MainFragment : Fragment() {
    private lateinit var saveFileWithResult: ActivityResultLauncher<Intent>
    private lateinit var openFileWithResult: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        saveFileWithResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                saveDataToFile(result.data?.data!!, view?.findViewById<EditText>(R.id.edit_text)?.text.toString())
                Toast
                    .makeText(context, "Файл сохранен", Toast.LENGTH_LONG)
                    .show()
            }
            else{
                Toast
                    .makeText(context, "Не удалось сохранить файл", Toast.LENGTH_LONG)
                    .show()
            }
        }

        openFileWithResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                view?.findViewById<TextView>(R.id.text_view)?.text = getDataFromFile(result.data?.data!!)
            }
            else{
                Toast
                    .makeText(context, "Не удалось открыть файл", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val saveBtn = view.findViewById<Button>(R.id.save_btn)
        val openBtn = view.findViewById<Button>(R.id.open_btn)
        val editText = view.findViewById<EditText>(R.id.edit_text)

        saveBtn.setOnClickListener {
            val intent = Intent().setAction(Intent.ACTION_CREATE_DOCUMENT)
            intent.type = "text/plain"
            saveFileWithResult.launch(intent)
        }


        openBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "text/plain"
            openFileWithResult.launch(intent)
            view.findViewById<EditText>(R.id.edit_text)?.visibility = View.GONE
            view.findViewById<TextView>(R.id.text_view)?.visibility = View.VISIBLE
        }


    }

    private fun saveDataToFile(uri: Uri, data: String) {
        val outputStream = context?.contentResolver?.openOutputStream(uri)
        outputStream?.use { stream ->
            stream.write(data.toByteArray())
            stream.close()
        }
    }

    private fun getDataFromFile(uri: Uri) : String {
        val inputStream = context?.contentResolver?.openInputStream(uri)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        while(reader.readLine().also{line = it} != null){
            stringBuilder.append(line)
        }
        return stringBuilder.toString()
    }


}
