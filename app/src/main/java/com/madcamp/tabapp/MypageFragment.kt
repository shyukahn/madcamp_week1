package com.madcamp.tabapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.madcamp.tabapp.data.database.InitDb
import com.madcamp.tabapp.databinding.FragmentMypageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MypageFragment : Fragment(R.layout.fragment_mypage) {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMypageBinding.bind(view)
        val userDao = InitDb.appDatabase.userDao()

        CoroutineScope(Dispatchers.IO).launch {
            val loginId = "admin" // TODO: Refactor to use variable instead of hardcoding
            val user = userDao.getUserByLoginId(loginId)
            if (user != null) {
                withContext(Dispatchers.Main) {
                    binding.nickname.text = user.nickname
                }
            }
        }

        binding.bookmarkedBakeries.setOnClickListener {
            val intent = Intent(context, BookmarkedBakeriesActivity::class.java)
            val options = context?.let { it1 -> ActivityOptionsCompat.makeCustomAnimation(it1, R.anim.slide_in_right, R.anim.slide_out_left) }
            if (options != null) {
                context?.startActivity(intent, options.toBundle())
            }
        }
        binding.myReviews.setOnClickListener {
            val intent = Intent(context, MyReviewsActivity::class.java)
            val options = context?.let { it1 -> ActivityOptionsCompat.makeCustomAnimation(it1, R.anim.slide_in_right, R.anim.slide_out_left) }
            if (options != null) {
                context?.startActivity(intent, options.toBundle())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
