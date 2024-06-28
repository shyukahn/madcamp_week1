package com.madcamp.tabapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.madcamp.tabapp.databinding.PhotoItemBinding

class PhotosAdapter(val context: Context, val photoList: ArrayList<Int>) : RecyclerView.Adapter<PhotosAdapter.Holder>() {
    inner class Holder(val binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val src = binding.rvPhotoItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.src.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.sungsimdang))
    }

    override fun getItemCount(): Int = photoList.size
}