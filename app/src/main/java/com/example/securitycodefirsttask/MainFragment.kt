package com.example.securitycodefirsttask

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MainFragment : Fragment() {
    private lateinit var saveFileWithResult: ActivityResultLauncher<Intent>
    private lateinit var openFileWithResult: ActivityResultLauncher<Intent>

    private var newFileContent: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        saveFileWithResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                saveDataToFile(result.data?.data!!, newFileContent)
                Toast
                    .makeText(context, result.data?.data.toString(), Toast.LENGTH_LONG)
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

        saveBtn.setOnClickListener {
            newFileContent = view.findViewById<EditText>(R.id.edit_text).text.toString()
            val intent = Intent().setAction(Intent.ACTION_CREATE_DOCUMENT)
            intent.type = "text/plain"
            saveFileWithResult.launch(intent)
        }


        openBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
            openFileWithResult.launch(intent)
        }


    }

    private fun saveDataToFile(uri: Uri, data: String) {
        val outputStream = context?.contentResolver?.openOutputStream(uri)
        outputStream?.use { stream ->
            stream.write(data.toByteArray())
            stream.close()
        }
    }



}
