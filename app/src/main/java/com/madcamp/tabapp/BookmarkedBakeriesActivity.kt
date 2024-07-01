package com.madcamp.tabapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.madcamp.tabapp.adapters.BookmarkBakeryAdapter
import com.madcamp.tabapp.data.database.InitDb
import com.madcamp.tabapp.data.model.ContactModel
import com.madcamp.tabapp.databinding.ActivityBookmarkedBakeriesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class BookmarkedBakeriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkedBakeriesBinding
    private lateinit var bookmarkBakeryAdapter: BookmarkBakeryAdapter
    private val bookmarkBakeryList: ArrayList<ContactModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkedBakeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadBakeriesFromJson()
        filterBookmarkedBakeries()
    }

    private fun setupRecyclerView() {
        bookmarkBakeryAdapter = BookmarkBakeryAdapter(bookmarkBakeryList, this)
        binding.bookmarkedBakeriesRv.apply {
            adapter = bookmarkBakeryAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun loadBakeriesFromJson() {
        val inputStream = resources.openRawResource(R.raw.contacts)
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<ContactModel>>() {}.type
        val contacts: List<ContactModel> = Gson().fromJson(reader, type)
        bookmarkBakeryList.addAll(contacts)
        reader.close()
    }

    private fun filterBookmarkedBakeries() {
        CoroutineScope(Dispatchers.IO).launch {
            val bookmarkDao = InitDb.appDatabase.bookmarkDao()
            val bookmarks = bookmarkDao.getAllBookmarks()
            val bookmarkedBakeryIds = bookmarks.map { it.bakeryId }.toSet()

            val filteredList = bookmarkBakeryList.filter { it.storeId in bookmarkedBakeryIds }

            withContext(Dispatchers.Main) {
                bookmarkBakeryList.clear()
                bookmarkBakeryList.addAll(filteredList)
                bookmarkBakeryAdapter.notifyDataSetChanged()
            }
        }
    }
}