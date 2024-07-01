package com.madcamp.tabapp

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madcamp.tabapp.data.model.PhotoModel
import com.madcamp.tabapp.databinding.FragmentPhotoFullscreenBinding

class PhotoFullscreenFragment(private val uri: Uri) : Fragment(R.layout.fragment_photo_fullscreen) {
    private lateinit var binding: FragmentPhotoFullscreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoFullscreenBinding.inflate(inflater, container, false)
        val view = binding.fullscreenPhoto
        view.apply {
            setImageURI(uri)
            setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        return binding.root
    }
}