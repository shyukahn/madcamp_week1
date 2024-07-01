package com.madcamp.tabapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.madcamp.tabapp.data.database.InitDb
import com.madcamp.tabapp.databinding.FragmentMypageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MypageFragment : Fragment(R.layout.fragment_mypage) {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMypageBinding.bind(view)

        CoroutineScope(Dispatchers.IO).launch {
            val userDao = InitDb.appDatabase.userDao()
            // TODO: test user "admin" -> refactor using variable
            val user = userDao.getUserByLoginId("admin")
            if (user != null) {
                binding.nickname.text = user.fullName
            }
        }
        binding.bookmarkedBakeries.setOnClickListener {
            val intent = Intent(context, BookmarkedBakeriesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
