package com.madcamp.tabapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.madcamp.tabapp.PhotoFullscreenFragment
import com.madcamp.tabapp.R
import com.madcamp.tabapp.data.Review
import com.madcamp.tabapp.data.database.InitDb
import com.madcamp.tabapp.databinding.PhotoItemBinding
import com.madcamp.tabapp.dialogs.ReviewDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
            showReviewInfo(review)
        }
        holder.item.setOnLongClickListener {
            showEditOrRemoveDialog(review, position)
            return@setOnLongClickListener true
        }
        holder.src.setImageURI(Uri.parse(review.imageUri))
        holder.text.text = review.name
    }

    override fun getItemCount(): Int = reviewList.size

    fun addReview(review: Review) {
        val newIdDeferred = CoroutineScope(Dispatchers.IO).async {
            reviewDao.insert(review)
        }
        CoroutineScope(Dispatchers.Main).launch {
            val newReview = review.getCopyWithNewId(newIdDeferred.await())
            reviewList.add(newReview)
            notifyItemInserted(reviewList.size - 1)
        }
    }

    fun removeReview(review: Review, position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            reviewDao.delete(review)
        }
        reviewList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }

    fun updateReview(review: Review, position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            reviewDao.update(review)
        }
        reviewList[position] = review
        notifyItemChanged(position)
    }

    private fun showReviewInfo(review: Review) {
        val fragmentManager = fragment.requireActivity().supportFragmentManager
        val fullscreenFragment = PhotoFullscreenFragment(review)
        fragmentManager
            .beginTransaction()
            .replace(R.id.mainFrameLayout, fullscreenFragment, "PHOTOS_FULLSCREEN")
            .addToBackStack(null)
            .commit()
    }

    private fun showEditOrRemoveDialog(review: Review, position: Int) {
        AlertDialog.Builder(this.fragment.requireContext())
            .setItems(arrayOf("Edit", "Remove")) { _, which ->
                when (which) {
                    0 -> ReviewDialog(fragment.requireContext(), this, review, position).show()
                    1 -> showRemoveDialog(review, position)
                }
            }
            .create()
            .show()
    }

    private fun showRemoveDialog(review: Review, position: Int) {
        AlertDialog.Builder(this.fragment.requireContext())
            .setTitle("Remove")
            .setMessage("Remove this review?")
            .setPositiveButton("yes") { _, _ ->
                removeReview(review, position)
                Toast.makeText(fragment.requireContext(), "Successfully removed review", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("no") { _, _ -> }
            .create()
            .show()
    }
}