package com.example.securitycodefirsttask

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader

class FileSystemWorkVM : ViewModel() {
    private var isStarted: MutableLiveData<Boolean>? = null
    private var dataForScreen: MutableLiveData<String>? = null

    fun getStatusData(): LiveData<Boolean> {
        if (isStarted == null) {
            isStarted = MutableLiveData(false)
        }
        return isStarted as LiveData<Boolean>
    }

    fun getFileData(): LiveData<String> {
        if (dataForScreen == null) {
            dataForScreen = MutableLiveData()
        }
        return dataForScreen as LiveData<String>
    }

    fun getDataFromFileAsync(context: Context, uri: Uri) =
        viewModelScope.launch(Dispatchers.IO) {
            isStarted?.postValue(true)
            val inputStream = context.contentResolver.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (withContext(Dispatchers.IO) {
                    reader.readLine()
                }.also { line = it } != null) {
                stringBuilder.append(line)
            }
            isStarted?.postValue(false)
            dataForScreen?.postValue(stringBuilder.toString())
        }


    fun saveDataToFileAsync(context: Context, data: String, uri: Uri) =
        viewModelScope.launch(Dispatchers.IO) {
            isStarted?.postValue(true)
            val outputStream = context.contentResolver?.openOutputStream(uri)
            outputStream?.use { stream ->
                stream.write(data.toByteArray())
                stream.close()
            }
            isStarted?.postValue(false)
        }
}