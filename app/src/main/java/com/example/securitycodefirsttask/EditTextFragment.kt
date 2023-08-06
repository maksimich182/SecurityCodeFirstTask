package com.example.securitycodefirsttask

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

class EditTextFragment : Fragment(R.layout.fragment_edit_text) {
    //private val textModel: TextViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_text, container, false)
        //var textView = view.findViewById(R.id.edit_text) as EditText
//        textModel.text.observe(viewLifecycleOwner, Observer {
//            value -> textView.setText(value)
//        })
//
//        textView.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                textModel.text.value = s.toString()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//        })

            //return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textField = view.findViewById(R.id.edit_text) as EditText
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Error", "OnDestroyFragmentMethod")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Error", "OnDestroyFragmentViewMethod")
    }

}