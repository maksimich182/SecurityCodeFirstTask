package com.example.securitycodefirsttask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FileManagerElementAdapter(private val _context: Context, private val elements: List<FileManagerElement>, private val onElementCLickListener: OnElementClickListener) :
    RecyclerView.Adapter<FileManagerElementAdapter.FileManagerViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(_context)

    interface OnElementClickListener {
        fun onElementClick(element: FileManagerElement, position: Int)
    }

    class FileManagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val elementTypeImage: ImageView = itemView.findViewById(R.id.item_type_image)
        val elementName: TextView = itemView.findViewById(R.id.item_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileManagerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.file_manager_item, parent, false)
        return FileManagerViewHolder(itemView)
    }

    override fun getItemCount(): Int = elements.count()

    override fun onBindViewHolder(holder: FileManagerViewHolder, position: Int) {
        val element: FileManagerElement = elements[position]
        holder.elementName.text = element.name
        holder.elementTypeImage.setImageResource(element.typeImage)

        holder.itemView.setOnClickListener {
            onElementCLickListener.onElementClick(
                element,
                position
            )
        }
    }
}