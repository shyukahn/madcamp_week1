package com.madcamp.tabapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.madcamp.tabapp.databinding.FragmentPhotosBinding
import com.madcamp.tabapp.data.model.PhotoModel
import com.madcamp.tabapp.adapters.PhotosAdapter
import com.madcamp.tabapp.dialogs.ReviewDialog

class PhotosFragment : Fragment(R.layout.fragment_photos) {
    private lateinit var binding: FragmentPhotosBinding
    private lateinit var photosAdapter: PhotosAdapter
    private val photoList = ArrayList<PhotoModel>()
    private val pickMultipleMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        uri?.let {
            ReviewDialog(requireContext(), photosAdapter, uri).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repeat(2) {
            photoList.add(PhotoModel("성심당", id = R.drawable.sungsimdang))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotosBinding.inflate(inflater, container, false)
        photosAdapter = PhotosAdapter(this, photoList)

        binding.rvPhotos.apply {
            adapter = photosAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        binding.addButton.setOnClickListener {
            pickMultipleMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        return binding.root
    }
}
