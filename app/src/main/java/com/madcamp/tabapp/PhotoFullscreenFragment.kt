package com.madcamp.tabapp

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.madcamp.tabapp.adapters.PhotosAdapter
import com.madcamp.tabapp.data.Review
import com.madcamp.tabapp.databinding.FragmentPhotoFullscreenBinding
import com.madcamp.tabapp.dialogs.ReviewDialog

class PhotoFullscreenFragment(
    private val photosAdapter: PhotosAdapter,
    private val review: Review,
    private val layoutId: Int
) : Fragment(R.layout.fragment_photo_fullscreen) {
    private lateinit var binding: FragmentPhotoFullscreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoFullscreenBinding.inflate(inflater, container, false)
        binding.apply {
            profileImage.setImageURI(Uri.parse(review.profileUri))
            userName.text = review.writer
            reviewImage.setImageURI(Uri.parse(review.imageUri))
            reviewTitle.text = review.name
            reviewText.text = review.reviewText
            editButton.setOnClickListener { view ->
                showPopupMenu(view)
            }
            fullScreen.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        return binding.root
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        if (review.isAdminUser) {
            // Editable review
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.popup_edit -> {
                        val reviewDialog =
                            ReviewDialog(requireContext(), photosAdapter, review, layoutId)
                        reviewDialog.setOnDismissListener {
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                        reviewDialog.show()
                        return@setOnMenuItemClickListener true
                    }
                    R.id.popup_remove -> {
                        showRemoveDialog(review, layoutId)
                        return@setOnMenuItemClickListener true
                    }
                    else -> false
                }
            }
            activity?.menuInflater?.inflate(R.menu.review_popup, popup.menu)
            popup.show()
        } else {
            // Non-editable review
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.popup_report -> {
                        Toast.makeText(requireContext(), "리뷰가 신고되었습니다", Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.popBackStack()
                        return@setOnMenuItemClickListener true
                    }

                    R.id.popup_block -> {
                        Toast.makeText(requireContext(), "리뷰가 차단되었습니다", Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.popBackStack()
                        return@setOnMenuItemClickListener true
                    }
                    else -> false
                }
            }
            activity?.menuInflater?.inflate(R.menu.review_popup_no_edit, popup.menu)
            popup.show()
        }
    }

    private fun showRemoveDialog(review: Review, position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Remove")
            .setMessage("Remove this review?")
            .setPositiveButton("yes") { _, _ ->
                photosAdapter.removeReview(review, position)
                Toast.makeText(context, "Successfully removed review", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
            .setNegativeButton("no") { _, _ -> }
            .create()
            .show()
    }
}