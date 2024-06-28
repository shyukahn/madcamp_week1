package com.madcamp.tabapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.madcamp.tabapp.adapter.ContactAdapter
import com.madcamp.tabapp.databinding.FragmentContactsBinding
import com.madcamp.tabapp.model.ContactModel

class ContactsFragment:Fragment(R.layout.fragment_contacts) {

    private lateinit var binding: FragmentContactsBinding
    private val contactList: ArrayList<ContactModel> = arrayListOf(
        ContactModel(1,"성심당 본점", "1588-8069"),
        ContactModel(2, "성심당 대전역점", "042-220-4138"),
        ContactModel(3, "당신을 위한 빵집", "010-6785-5949"),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(layoutInflater)

        binding.breadStoreRv.apply {
            adapter = ContactAdapter(contactList)
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }
}
