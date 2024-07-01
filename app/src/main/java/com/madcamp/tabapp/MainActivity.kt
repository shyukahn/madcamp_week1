package com.madcamp.tabapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.madcamp.tabapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize fragments
        val contactsFragment = ContactsFragment()

        // Set the initial fragment
        setCurrentFragment(contactsFragment, "CONTACTS")

        // Set up the bottom navigation view item selected listener
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.contacts -> setCurrentFragment(ContactsFragment(), "CONTACTS")
                R.id.photos -> setCurrentFragment(PhotosFragment(), "PHOTOS")
                R.id.settings -> setCurrentFragment(MypageFragment(), "MY PAGE")
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment, tag: String) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFrameLayout, fragment, tag)
            commit()
        }
}