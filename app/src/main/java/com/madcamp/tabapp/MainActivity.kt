package com.madcamp.tabapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.madcamp.tabapp.adapters.ViewPagerAdapter
import com.madcamp.tabapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup ViewPager2
        setupViewPager(binding.viewPager)

        // Setup BottomNavigationView
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.contacts -> binding.viewPager.currentItem = 0
                R.id.photos -> binding.viewPager.currentItem = 1
                R.id.settings -> binding.viewPager.currentItem = 2
            }
            true
        }
    }

    private fun setupViewPager(viewPager: ViewPager2) {
        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(ContactsFragment())
        adapter.addFragment(PhotosFragment())
        adapter.addFragment(MypageFragment())
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })
    }
}
