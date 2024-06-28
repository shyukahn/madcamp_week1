package com.madcamp.tabapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.madcamp.tabapp.databinding.FragmentPhotosBinding

class PhotosFragment : Fragment(R.layout.fragment_photos) {
    private lateinit var binding: FragmentPhotosBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotosBinding.inflate(layoutInflater)

        val photoList = ArrayList<Int>((1..40).toList())
        binding.rvPhotos.apply {
            adapter = PhotosAdapter(requireContext(), photoList)
            layoutManager = GridLayoutManager(context, 2)
        }

        return binding.root
    }
}
