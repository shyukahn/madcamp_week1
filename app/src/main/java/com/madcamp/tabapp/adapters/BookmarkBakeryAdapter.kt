package com.madcamp.tabapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.madcamp.tabapp.R
import com.madcamp.tabapp.data.Bookmark
import com.madcamp.tabapp.data.database.InitDb
import com.madcamp.tabapp.data.model.ContactModel
import com.madcamp.tabapp.databinding.BookmarkBakeryItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookmarkBakeryAdapter(private val bakeryList: ArrayList<ContactModel>, private val context: Context) :
    RecyclerView.Adapter<BookmarkBakeryAdapter.ViewHolder>() {
    class ViewHolder(private val binding: BookmarkBakeryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bakery: ContactModel, context: Context) {
            binding.storeName.text = bakery.storeName
            binding.storeAddress.text = bakery.storeAddress
            Glide.with(binding.storeThumbnail.context)
                .load(bakery.storeThumbnail)
                .into(binding.storeThumbnail)

            binding.callStoreBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${bakery.storeNumber}"))
                context.startActivity(intent)
            }

            binding.shareStoreBtn.setOnClickListener {
                val shareText = """
                    추천 빵집을 소개합니다!
                    🏠 이름: ${bakery.storeName}
                    📞 전화번호: ${bakery.storeNumber}
                    📍 위치: ${bakery.storeAddress}
                """.trimIndent()

                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(intent, "공유하기"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BookmarkBakeryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bakery = bakeryList[position]
        holder.bind(bakery, context)
    }

    override fun getItemCount(): Int {
        return bakeryList.size
    }
}
