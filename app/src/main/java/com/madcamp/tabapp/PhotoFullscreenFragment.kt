package com.madcamp.tabapp

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madcamp.tabapp.data.Review
import com.madcamp.tabapp.data.database.InitDb
import com.madcamp.tabapp.databinding.FragmentPhotoFullscreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PhotoFullscreenFragment(private val review: Review) : Fragment(R.layout.fragment_photo_fullscreen) {
    private lateinit var binding: FragmentPhotoFullscreenBinding
    private val userDao = InitDb.appDatabase.userDao()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userDeferred = CoroutineScope(Dispatchers.IO).async {
            userDao.getUserById(review.userId)
        }
        binding = FragmentPhotoFullscreenBinding.inflate(inflater, container, false)
        binding.apply {
            CoroutineScope(Dispatchers.Main).launch {
                val user = userDeferred.await()
                profileImage.setImageURI(Uri.parse(user.profileUri))
                userName.text = user.nickname
            }
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