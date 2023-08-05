package com.example.securitycodefirsttask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var bottomPanelFragment: BottomPanelFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomPanelFragment = supportFragmentManager.findFragmentById(R.id.bottomPanelFragment) as BottomPanelFragment
        bottomPanelFragment?.bottomPanelClickListener = object : IBottomPanelClickListener{
            override fun onSaveClick(view: View) {
                Toast.makeText(this@MainActivity, "Кнопка нажата", Toast.LENGTH_SHORT).show()
            }

            override fun onOpenClick(view: View) {
                Toast.makeText(this@MainActivity, "Кнопка нажата", Toast.LENGTH_SHORT).show()
            }

            override fun onCreateClick(view: View) {
                Toast.makeText(this@MainActivity, "Кнопка нажата", Toast.LENGTH_SHORT).show()
            }

        }
    }
}