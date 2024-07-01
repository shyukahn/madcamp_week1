package com.madcamp.tabapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.madcamp.tabapp.adapters.ContactAdapter
import com.madcamp.tabapp.data.Bookmark
import com.madcamp.tabapp.data.database.InitDb
import com.madcamp.tabapp.databinding.FragmentContactsBinding
import com.madcamp.tabapp.data.model.ContactModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStreamReader

class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var storeContactListAdapter: ContactAdapter
    private val contactList: ArrayList<ContactModel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)

        loadContactsFromJson()
        setupRecyclerView()
        setupSearchView()

        insertBookmarks()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadContactsFromJson() {
        val inputStream = resources.openRawResource(R.raw.contacts)
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<ContactModel>>() {}.type
        val contacts: List<ContactModel> = Gson().fromJson(reader, type)
        contactList.addAll(contacts)
        reader.close()
    }

    private fun setupRecyclerView() {
        storeContactListAdapter = ContactAdapter(contactList, requireContext())
        binding.breadStoreRv.apply {
            adapter = storeContactListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupSearchView() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                storeContactListAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun insertBookmarks() {
        CoroutineScope(Dispatchers.IO).launch {
            val bookmarkDao = InitDb.appDatabase.bookmarkDao()
            val bookmarks = contactList.map { contact ->
                Bookmark(bakeryId = contact.storeId, isBookmarked = contact.isBookmarked)
            }
            for (bookmark in bookmarks) {
                val existingBookmark = bookmarkDao.getBookmark(bookmark.bakeryId)
                if (existingBookmark == null) {
                    bookmarkDao.insert(bookmark)
                } else {
                    bookmarkDao.update(bookmark)
                }
            }
        }
    }
}
