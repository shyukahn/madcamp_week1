package com.madcamp.tabapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.madcamp.tabapp.R
import com.madcamp.tabapp.databinding.ContactItemBinding
import com.madcamp.tabapp.model.ContactModel

class ContactAdapter(private val contactList: ArrayList<ContactModel>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>(), Filterable {

    private var filteredContactList = ArrayList<ContactModel>()
    private var itemFilter = ItemFilter()

    init {
        filteredContactList.addAll(contactList)
    }

    class ViewHolder(private val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root){

        val storeImage = binding.storeImage
        val storeName = binding.storeName
        val storeNumber = binding.storeNumber
        val storeLocation = binding.storeLocation
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = filteredContactList[position]

//        holder.storeImage.setImageResource(contact.storeImage) // Resources$NotFoundException
        holder.storeImage.setImageResource(R.drawable.test_store_image)
        holder.storeName.text = contact.storeName
        holder.storeNumber.text = contact.storeNumber
        holder.storeLocation.text = contact.storeLocation
    }

    override fun getItemCount(): Int {
        return filteredContactList.size
    }

    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()

            //검색이 필요 없을 경우를 위해 원본 배열을 복제
            val filteredList: ArrayList<ContactModel> = ArrayList<ContactModel>()
            //공백 제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = contactList
                results.count = contactList.size

                return results
            //공백 제외 2글자 이하인 경우 -> 이름으로만 검색
            } else if (filterString.trim { it <= ' ' }.length <= 2) {
                for (contact in contactList) {
                    if (contact.storeName.contains(filterString)) {
                        filteredList.add(contact)
                    }
                }
            //그 외의 경우(공백 제외 2글자 초과) -> 이름/전화번호로 검색
            } else {
                for (contact in contactList) {
                    if (contact.storeName.contains(filterString) || contact.storeNumber.contains(filterString)) {
                        filteredList.add(contact)
                    }
                }
            }
            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            filteredContactList.clear()
            filteredContactList.addAll(filterResults.values as ArrayList<ContactModel>)
            notifyDataSetChanged()
        }
    }
}