package com.example.securitycodefirsttask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TextViewModel: ViewModel() {
    var text = MutableLiveData<String>("")
}