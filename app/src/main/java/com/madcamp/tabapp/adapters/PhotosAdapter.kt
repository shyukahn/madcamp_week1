package com.madcamp.tabapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.madcamp.tabapp.PhotoFullscreenFragment
import com.madcamp.tabapp.R
import com.madcamp.tabapp.data.PhotoModel
import com.madcamp.tabapp.databinding.PhotoItemBinding

class PhotosAdapter(val fragment: Fragment, val photoList: ArrayList<PhotoModel>) : RecyclerView.Adapter<PhotosAdapter.Holder>() {
    inner class Holder(binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val item = binding.rvPhotoItem
        val src = binding.rvPhotoImage
        val text = binding.rvPhotoText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val photo = photoList[position]
        holder.item.setOnClickListener {
            val fragmentManager = fragment.requireActivity().supportFragmentManager
            val fullscreenFragment = PhotoFullscreenFragment(photo)
            fragmentManager
                .beginTransaction()
                .replace(R.id.mainFrameLayout, fullscreenFragment, "PHOTOS_FULLSCREEN")
                .addToBackStack(null)
                .commit()
        }
        if (photo.isId) {
            holder.src.setImageResource(photo.id!!)
        } else {
            holder.src.setImageURI(photo.uri)
        }
        holder.text.text = photo.text
    }

    override fun getItemCount(): Int = photoList.size

    fun addPhoto(photoModel: PhotoModel) {
        photoList.add(photoModel)
        notifyItemInserted(photoList.size - 1)
    }
}