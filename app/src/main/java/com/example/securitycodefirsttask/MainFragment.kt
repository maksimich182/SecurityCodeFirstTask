package com.example.securitycodefirsttask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainFragment : Fragment() {

    var fileMenegerTestList = listOf<FileManagerElement>(
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_file),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_file),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_file),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_file),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_directory),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_file),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_directory),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_directory),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_directory),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_file),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_file),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_file),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_directory),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_file),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_directory),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_file),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_directory),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_file),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_directory),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_file),
        FileManagerElement("asdfff", "asdfasdf", R.drawable.ic_file)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.file_manager_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = FileManagerElementAdapter(fileMenegerTestList)
    }


}