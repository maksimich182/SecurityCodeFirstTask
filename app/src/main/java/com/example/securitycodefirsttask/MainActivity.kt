package com.example.securitycodefirsttask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext

class MainActivity : AppCompatActivity(), IBottomPanel {
    var bottomPannel: IBottomPanel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomPannel = supportFragmentManager.findFragmentById(R.id.bottomPanelFragment) as IBottomPanel
    }


    override fun onSaveClick(view: View) {
        bottomPannel?.onSaveClick(view)
    }

    override fun onOpenClick(view: View) {
        bottomPannel?.onOpenClick(view)
    }

    override fun onCreateClick(view: View) {
        bottomPannel?.onCreateClick(view)
    }
}