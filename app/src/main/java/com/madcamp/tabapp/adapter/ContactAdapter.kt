package com.madcamp.tabapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.madcamp.tabapp.R
import com.madcamp.tabapp.databinding.ContactItemBinding
import com.madcamp.tabapp.model.ContactModel

class ContactAdapter(private val contactList: ArrayList<ContactModel>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root){

        val storeImage = binding.storeImage
        val storeName = binding.storeName
        val storeNumber = binding.storeNumber
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactList[position]

//        holder.storeImage.setImageResource(contact.storeImage) // Resources$NotFoundException
        holder.storeImage.setImageResource(R.drawable.test_store_image)
        holder.storeName.text = contact.storeName
        holder.storeNumber.text = contact.storeNumber
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}