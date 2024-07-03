package com.madcamp.tabapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.madcamp.tabapp.adapters.PhotosAdapter
import com.madcamp.tabapp.data.Review
import com.madcamp.tabapp.data.database.InitDb
import com.madcamp.tabapp.databinding.ActivityMyReviewsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyReviewsActivity : BaseActivity() {
    private lateinit var binding: ActivityMyReviewsBinding
    private val reviewDao = InitDb.appDatabase.reviewDao()
    private val adminReviewListDeferred = CoroutineScope(Dispatchers.IO).async {
        reviewDao.getAdminReviews()
    }
    private lateinit var adminReviewList: MutableList<Review>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            binding = ActivityMyReviewsBinding.inflate(layoutInflater)
            setContentView(binding.root)

            adminReviewList = adminReviewListDeferred.await().toMutableList()

            val myReviewAdapter = PhotosAdapter(this@MyReviewsActivity, adminReviewList, R.id.my_reviews_layout)
            binding.myReviewsRv.apply {
                adapter = myReviewAdapter
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }

            binding.toolbar.setNavigationOnClickListener {
                finish()
            }
        }
    }
}
