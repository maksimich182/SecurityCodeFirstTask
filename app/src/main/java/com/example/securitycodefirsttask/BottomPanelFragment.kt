package com.example.securitycodefirsttask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class BottomPanelFragment : Fragment(R.layout.fragment_bottom_panel) {
    var bottomPanelClickListener: IBottomPanelClickListener? = null
    var saveBtn: Button? = null
    var openBtn: Button? = null
    var createBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_panel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveBtn = view.findViewById(R.id.save_btn)
        openBtn = view.findViewById(R.id.open_btn)
        createBtn = view.findViewById(R.id.create_btn)

        saveBtn?.setOnClickListener { bottomPanelClickListener?.onSaveClick(view) }
        openBtn?.setOnClickListener { bottomPanelClickListener?.onOpenClick(view) }
        createBtn?.setOnClickListener { bottomPanelClickListener?.onCreateClick(view) }
    }
}

interface IBottomPanelClickListener {
    fun onSaveClick(view: View)
    fun onOpenClick(view: View)
    fun onCreateClick(view: View)
}