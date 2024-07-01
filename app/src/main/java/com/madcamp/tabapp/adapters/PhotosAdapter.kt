package com.madcamp.tabapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.madcamp.tabapp.PhotoFullscreenFragment
import com.madcamp.tabapp.R
import com.madcamp.tabapp.data.InitDb
import com.madcamp.tabapp.data.Review
import com.madcamp.tabapp.databinding.PhotoItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotosAdapter(
    private val fragment: Fragment,
    private val reviewList: MutableList<Review>
    ) : RecyclerView.Adapter<PhotosAdapter.Holder>() {
    private val reviewDao = InitDb.appDatabase.reviewDao()

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
        val review = reviewList[position]
        holder.item.setOnClickListener {
            val fragmentManager = fragment.requireActivity().supportFragmentManager
            val fullscreenFragment = PhotoFullscreenFragment(Uri.parse(review.imageUri))
            fragmentManager
                .beginTransaction()
                .replace(R.id.mainFrameLayout, fullscreenFragment, "PHOTOS_FULLSCREEN")
                .addToBackStack(null)
                .commit()
        }
        holder.src.setImageURI(Uri.parse(review.imageUri))
        holder.text.text = review.name
    }

    override fun getItemCount(): Int = reviewList.size

    fun addReview(review: Review) {
        CoroutineScope(Dispatchers.IO).launch {
            reviewDao.insert(review)
        }
        reviewList.add(review)
        notifyItemInserted(reviewList.size - 1)
    }
}