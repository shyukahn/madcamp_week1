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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotosFragment : Fragment(R.layout.fragment_photos) {
    private lateinit var binding: FragmentPhotosBinding
    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var reviewList: MutableList<Review>
    private val reviewDao = InitDb.appDatabase.reviewDao() // reviewDao를 클래스 레벨에서 정의

    private val pickMultipleMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        uri?.let {
            val review = Review(
                name = "",
                reviewText = "",
                imageUri = uri.toString(),
                writer = "대빵이",
                profileUri = "android.resource://com.madcamp.tabapp/" + R.drawable.profile_image,
                isAdminUser = true
            )
            ReviewDialog(requireContext(), photosAdapter, review, -1).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotosBinding.inflate(inflater, container, false)

        binding.addButton.setOnClickListener {
            pickMultipleMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        photosAdapter = PhotosAdapter(requireContext(), emptyList<Review>().toMutableList(), R.id.fullscreenFragmentContainer)
        binding.rvPhotos.apply {
            adapter = photosAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        return binding.root
    }

    override fun onStart() {
        CoroutineScope(Dispatchers.IO).launch {
            reviewList = reviewDao.getAllReviews().toMutableList()

            withContext(Dispatchers.Main) {
                if (reviewList.isEmpty()) {
                    initReviews()
                } else {
                    photosAdapter.resetReviewData(reviewList)
                }
            }
        }
        super.onStart()
    }

    private fun setupRecyclerView() {
        photosAdapter = PhotosAdapter(requireContext(), reviewList, R.id.fullscreenFragmentContainer)
        binding.rvPhotos.apply {
            adapter = photosAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

    }

    private fun initReviews() {
        val defaultReviewList = listOf(
            Review(
                name = "최고의 맛과 서비스",
                reviewText = "성심당 본점은 항상 신선한 빵과 친절한 서비스로 유명하죠. 특히 튀김소보로가 최고예요.",
                imageUri = "android.resource://com.madcamp.tabapp/" + R.drawable.review1,
                writer = "민수잇",
                profileUri = "android.resource://com.madcamp.tabapp/" + R.drawable.profile_image,
                isAdminUser = false
            ),
            Review(
                name = "여행 중 간식으로 딱!",
                reviewText = "대전역에서 기차 타기 전에 들렀는데, 빵이 너무 맛있어서 놀랐어요.",
                imageUri = "android.resource://com.madcamp.tabapp/" + R.drawable.review2,
                writer = "여행자",
                profileUri = "android.resource://com.madcamp.tabapp/" + R.drawable.profile_image,
                isAdminUser = false
            ),
            Review(
                name = "아늑하고 맛있는 빵집",
                reviewText = "당신을 위한 빵집은 아늑한 분위기에서 맛있는 빵을 즐길 수 있어서 좋아요.",
                imageUri = "android.resource://com.madcamp.tabapp/" + R.drawable.review3,
                writer = "달콤한하루",
                profileUri = "android.resource://com.madcamp.tabapp/" + R.drawable.profile_image,
                isAdminUser = false
            ),
            Review(
                name = "캘리포니아의 맛",
                reviewText = "캘리포니아 베이커리 카페는 미국 느낌의 빵이 많아서 좋아요. 맛도 훌륭합니다.",
                imageUri = "android.resource://com.madcamp.tabapp/" + R.drawable.review4,
                writer = "캘리러버",
                profileUri = "android.resource://com.madcamp.tabapp/" + R.drawable.profile_image,
                isAdminUser = false
            ),
            Review(
                name = "전통의 맛",
                reviewText = "인유단 베이커리는 전통의 맛을 그대로 느낄 수 있는 빵이 많아서 좋아요.",
                imageUri = "android.resource://com.madcamp.tabapp/" + R.drawable.review5,
                writer = "전통맛",
                profileUri = "android.resource://com.madcamp.tabapp/" + R.drawable.profile_image,
                isAdminUser = false
            ),
            Review(
                name = "따뜻한 분위기",
                reviewText = "따뜻한 분위기에서 빵을 즐길 수 있어서 자주 방문해요.",
                imageUri = "android.resource://com.madcamp.tabapp/" + R.drawable.review6,
                writer = "따뜻함",
                profileUri = "android.resource://com.madcamp.tabapp/" + R.drawable.profile_image,
                isAdminUser = false
            ),
            Review(
                name = "품질 좋은 빵",
                reviewText = "빵 품질이 너무 좋아서 항상 만족스럽습니다. 강추!",
                imageUri = "android.resource://com.madcamp.tabapp/" + R.drawable.review7,
                writer = "품질우선",
                profileUri = "android.resource://com.madcamp.tabapp/" + R.drawable.profile_image,
                isAdminUser = false
            ),
            Review(
                name = "건강한 빵",
                reviewText = "슬로우 브레드는 건강한 재료로 만든 빵이라 안심하고 먹을 수 있어요.",
                imageUri = "android.resource://com.madcamp.tabapp/" + R.drawable.review8,
                writer = "헬시러버",
                profileUri = "android.resource://com.madcamp.tabapp/" + R.drawable.profile_image,
                isAdminUser = false
            ),
            Review(
                name = "고급스러운 분위기",
                reviewText = "백화점 안에 있어서 그런지 분위기가 고급스럽고 빵도 최고예요.",
                imageUri = "android.resource://com.madcamp.tabapp/" + R.drawable.review9,
                writer = "고급진삶",
                profileUri = "android.resource://com.madcamp.tabapp/" + R.drawable.profile_image,
                isAdminUser = false
            )
        )

        CoroutineScope(Dispatchers.IO).launch {
            for (review in defaultReviewList) {
                val id = reviewDao.insert(review)
                reviewList.add(review.copy(id = id)) // 복사된 ID로 리뷰 리스트 업데이트
            }
            withContext(Dispatchers.Main) {
                setupRecyclerView()
            }
        }
    }
}
