package com.example.securitycodefirsttask

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainFragment : Fragment() {
    private lateinit var saveFileWithResult: ActivityResultLauncher<Intent>
    private lateinit var openFileWithResult: ActivityResultLauncher<Intent>

    private lateinit var textView: TextView
    private lateinit var editText: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var scrollTextView: ScrollView

    //private lateinit var fileSystemWorkVM: FileSystemWorkVM
    //private lateinit var statusFileProcess: LiveData<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fileSystemWorkVM: FileSystemWorkVM = ViewModelProvider(this).get<FileSystemWorkVM>(FileSystemWorkVM::class.java)
        val statusFileProcess: LiveData<Boolean> = fileSystemWorkVM.getStatusData()
        statusFileProcess.observe(this, Observer<Boolean> { isStarted ->
            if(isStarted == true){
                startFileProcessAnimation()
            } else{
                stopFileProcessAnimation()
            }
        })

        val fileData: LiveData<String> = fileSystemWorkVM.getFileData()
        fileData.observe(this, Observer<String>{data-> textView.text = data })



        saveFileWithResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                lifecycleScope.launch {
                    startFileProcessAnimation()
                    lifecycleScope.launch {
                        saveDataToFile(
                            result.data?.data!!,
                            editText.text.toString()
                        )
                    }.join()
                    stopFileProcessAnimation()
                    Toast
                        .makeText(context, "Файл сохранен", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast
                    .makeText(context, "Не удалось сохранить файл", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        openFileWithResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->

            if (result.resultCode == Activity.RESULT_OK) {
                lifecycleScope.launch {
                    fileSystemWorkVM.getDataFromFileAsync(requireContext(), result.data?.data!!)
                }
            } else {
                Toast
                    .makeText(context, "Не удалось открыть файл", Toast.LENGTH_SHORT)
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
        val createBtn = view.findViewById<Button>(R.id.create_btn)
        editText = view.findViewById<EditText>(R.id.edit_text)
        textView = view.findViewById<TextView>(R.id.text_view)
        scrollTextView = view.findViewById<ScrollView>(R.id.scroll_text_view)
        progressBar = view.findViewById(R.id.progressBar)

        saveBtn.setOnClickListener {
            val intent = Intent().setAction(Intent.ACTION_CREATE_DOCUMENT)
            intent.type = "text/plain"
            saveFileWithResult.launch(intent)
        }

        openBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "text/plain"
            openFileWithResult.launch(intent)
            editText.visibility = View.GONE
            scrollTextView.visibility = View.VISIBLE
            textView.visibility = View.VISIBLE
        }

       createBtn.setOnClickListener{
           if(editText.text.toString() != ""){
               AlertDialog.Builder(context)
                   .setTitle("Внимание!")
                   .setMessage("Текущий файл не будет сохранен")
                   .setPositiveButton("Продолжить"){_,_ ->
                       scrollTextView.visibility = View.GONE
                       editText.visibility = View.VISIBLE
                       editText.setText("")
                   }
                   .setNegativeButton("Отмена"){_,_ ->}
                   .create()
                   .show()
           }
       }
    }

    private suspend fun saveDataToFile(uri: Uri, data: String) = withContext(Dispatchers.IO) {
        launch {
            val outputStream = context?.contentResolver?.openOutputStream(uri)
            outputStream?.use { stream ->
                stream.write(data.toByteArray())
                stream.close()
            }
        }
    }

    private suspend fun getDataFromFile(uri: Uri): String = withContext(Dispatchers.IO) {
        val data: Deferred<String> = async {
            val inputStream = context?.contentResolver?.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            stringBuilder.toString()
        }
        data.await()
    }

    private fun startFileProcessAnimation() {
        progressBar.visibility = View.VISIBLE

        val rotateAnimation = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.repeatCount = Animation.INFINITE
        rotateAnimation.duration = 1000

        progressBar.startAnimation(rotateAnimation)
    }

    private fun stopFileProcessAnimation() {
        progressBar.clearAnimation()
        progressBar.visibility = View.GONE
    }
}
