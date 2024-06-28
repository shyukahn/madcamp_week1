package com.madcamp.tabapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.madcamp.tabapp.databinding.FragmentPhotosBinding
import com.madcamp.tabapp.phototab.PhotoModel
import com.madcamp.tabapp.phototab.PhotosAdapter

class PhotosFragment : Fragment(R.layout.fragment_photos) {
    private lateinit var binding: FragmentPhotosBinding
    private val photoList = ArrayList<PhotoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repeat(32) {
            photoList.add(PhotoModel(R.drawable.sungsimdang, "성심당"))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotosBinding.inflate(inflater, container, false)

        binding.rvPhotos.apply {
            adapter = PhotosAdapter(this@PhotosFragment, photoList)
            layoutManager = GridLayoutManager(context, 2)
        }

        return binding.root
    }
}
