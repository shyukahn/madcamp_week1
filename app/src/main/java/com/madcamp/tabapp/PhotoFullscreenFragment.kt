package com.madcamp.tabapp

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madcamp.tabapp.data.Review
import com.madcamp.tabapp.databinding.FragmentPhotoFullscreenBinding

class PhotoFullscreenFragment(private val review: Review) : Fragment(R.layout.fragment_photo_fullscreen) {
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
            fullScreen.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        return binding.root
    }
}