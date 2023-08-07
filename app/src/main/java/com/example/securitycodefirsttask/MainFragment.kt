package com.example.securitycodefirsttask

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainFragment : Fragment() {
    private lateinit var saveFileWithResult: ActivityResultLauncher<Intent>
    private lateinit var openFileWithResult: ActivityResultLauncher<Intent>

    private lateinit var textView: TextView
    private lateinit var editText: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        saveFileWithResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                lifecycleScope.launch {
                    startLoadingAnimation()
                    lifecycleScope.launch {
                        saveDataToFile(
                            result.data?.data!!,
                            editText.text.toString()
                        )
                    }.join()
                    stopLoadingAnimation()
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
                    startLoadingAnimation()
                    lifecycleScope.launch {
                        textView.text = getDataFromFile(result.data?.data!!)
                    }.join()
                    stopLoadingAnimation()
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
        editText = view.findViewById<EditText>(R.id.edit_text)
        textView = view.findViewById<TextView>(R.id.text_view)
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
            textView.visibility = View.VISIBLE
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

    private fun startLoadingAnimation() {
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

    private fun stopLoadingAnimation() {
        progressBar.clearAnimation()
        progressBar.visibility = View.GONE
    }
}
