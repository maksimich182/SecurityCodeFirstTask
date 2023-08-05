package com.example.securitycodefirsttask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.button.MaterialButton


class BottomPanelFragment : Fragment(R.layout.fragment_bottom_panel), IBottomPanel {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_panel, container, false)
    }

    override fun onSaveClick(view: View) {
        Toast.makeText(requireContext(), "Кнопка нажата", Toast.LENGTH_SHORT).show()
    }

    override fun onOpenClick(view: View) {
        Toast.makeText(requireContext(), "Кнопка нажата", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateClick(view: View) {
        Toast.makeText(requireContext(), "Кнопка нажата", Toast.LENGTH_SHORT).show()
    }


}

interface IBottomPanel {
    fun onSaveClick(view: View)
    fun onOpenClick(view: View)
    fun onCreateClick(view: View)
}