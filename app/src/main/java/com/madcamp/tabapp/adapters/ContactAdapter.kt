package com.madcamp.tabapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.madcamp.tabapp.R
import com.madcamp.tabapp.data.model.ContactModel
import com.madcamp.tabapp.data.Bookmark
import com.madcamp.tabapp.data.database.InitDb
import com.madcamp.tabapp.databinding.ContactItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactAdapter(private val contactList: ArrayList<ContactModel>, private val context: Context) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>(), Filterable {

    private var filteredContactList = ArrayList<ContactModel>()
    private var itemFilter = ItemFilter()

    init {
        filteredContactList.addAll(contactList)
    }

    class ViewHolder(private val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: ContactModel, context: Context) {
            binding.storeName.text = contact.storeName
            binding.storeNumber.text = contact.storeNumber
            binding.storeAddress.text = contact.storeAddress
            binding.storeImage.setImageResource(R.drawable.test_store_image)
            // TODO: Fix Resources$NotFoundException
            // binding.storeImage.setImageResource(contact.storeImage)
            setBookmarkIcon(binding.starStoreBtn, contact.isBookmarked)

            binding.callStoreBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contact.storeNumber}"))
                context.startActivity(intent)
            }
            binding.shareStoreBtn.setOnClickListener {
                val shareText = """
                    추천 빵집을 소개합니다!
                    🏠 이름: ${contact.storeName}
                    📞 전화번호: ${contact.storeNumber}
                    📍 위치: ${contact.storeAddress}
                    """.trimIndent()

                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(intent, "공유하기"))
            }
        }

        private fun setBookmarkIcon(starStoreBtn: ImageButton, isBookmarked: Boolean) {
            if (isBookmarked) {
                starStoreBtn.setImageResource(R.drawable.ic_round_star)
            } else {
                starStoreBtn.setImageResource(R.drawable.ic_round_star_outline)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = filteredContactList[position]
        holder.bind(contact, context)
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

            // 검색이 필요 없을 경우를 위해 원본 배열을 복제
            val filteredList: ArrayList<ContactModel> = ArrayList()
            // 공백 제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = contactList
                results.count = contactList.size

                return results
                // 공백 제외 2글자 이하인 경우 -> 이름으로만 검색
            } else if (filterString.trim { it <= ' ' }.length <= 2) {
                for (contact in contactList) {
                    if (contact.storeName.contains(filterString)) {
                        filteredList.add(contact)
                    }
                }
                // 그 외의 경우(공백 제외 2글자 초과) -> 이름/전화번호로 검색
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
