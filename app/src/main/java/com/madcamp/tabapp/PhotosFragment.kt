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
import com.madcamp.tabapp.adapters.PhotosAdapter
import com.madcamp.tabapp.data.Review
import com.madcamp.tabapp.data.database.InitDb
import com.madcamp.tabapp.dialogs.ReviewDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PhotosFragment : Fragment(R.layout.fragment_photos) {
    private lateinit var binding: FragmentPhotosBinding
    private lateinit var photosAdapter: PhotosAdapter
    private val pickMultipleMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        uri?.let {
            val review = Review(
                name = "",
                reviewText = "",
                imageUri = uri.toString(),
                writer = "admin",
                profileUri = "android.resource://com.madcamp.tabapp/" + R.drawable.profile_image,
                isAdminUser = true
            )
            ReviewDialog(requireContext(), photosAdapter, review, -1).show()
        }
    }
    private val reviewDao = InitDb.appDatabase.reviewDao()
    private val reviewListDeferred = CoroutineScope(Dispatchers.IO).async {
        reviewDao.getAllReviews().toMutableList()
    }
    private lateinit var reviewList: MutableList<Review>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val defaultReview = Review(
            name = "성심당",
            reviewText = "Good",
            imageUri = "android.resource://com.madcamp.tabapp/" + R.drawable.sungsimdang,
            writer = "admin",
            profileUri = "android.resource://com.madcamp.tabapp/" + R.drawable.profile_image,
            isAdminUser = false
        )
        CoroutineScope(Dispatchers.IO).launch {
            reviewList = reviewListDeferred.await()
            if (reviewList.isEmpty()) {
                addDefaultReview(defaultReview)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotosBinding.inflate(inflater, container, false)
        photosAdapter = PhotosAdapter(requireContext(), reviewList, R.id.mainFrameLayout)

        binding.rvPhotos.apply {
            adapter = photosAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        binding.addButton.setOnClickListener {
            pickMultipleMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        return binding.root
    }

    private fun addDefaultReview(defaultReview: Review) {
        val id = CoroutineScope(Dispatchers.IO).async {
            reviewDao.insert(defaultReview)
        }
        CoroutineScope(Dispatchers.IO).launch {
            reviewList.add(defaultReview.getCopyWithNewId(id.await()))
        }
    }
}
