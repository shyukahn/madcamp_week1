package com.madcamp.tabapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.madcamp.tabapp.data.model.BakeryDetailModel
import com.madcamp.tabapp.databinding.ActivityContactDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class ContactDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDetailBinding
    private val bakeryDetailList: ArrayList<BakeryDetailModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storeName = intent.getStringExtra("storeName")
        val storeNumber = intent.getStringExtra("storeNumber")
        val storeAddress = intent.getStringExtra("storeAddress")
        val storeThumbnail = intent.getStringExtra("storeThumbnail")
        val bakeryId = intent.getIntExtra("bakeryId", -1)

        binding.storeName.text = storeName
        binding.storeNumber.text = storeNumber
        binding.storeAddress.text = storeAddress
        Glide.with(this).load(storeThumbnail).into(binding.storeThumbnail)

        // 비동기로 JSON 데이터를 로드
        CoroutineScope(Dispatchers.IO).launch {
            loadBakeryDetailsFromJson()
            withContext(Dispatchers.Main) {
                if (bakeryId != -1 && bakeryId < bakeryDetailList.size) {
                    binding.storeHours.text = bakeryDetailList[bakeryId].openingHours
                    binding.reviewSummary.text = bakeryDetailList[bakeryId].reviewSummary
                } else {
                    binding.storeHours.text = "Unknown"
                    binding.reviewSummary.text = "No review available"
                }
            }
        }
    }

    private fun loadBakeryDetailsFromJson() {
        val inputStream = resources.openRawResource(R.raw.bakery_details)
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<BakeryDetailModel>>() {}.type
        val bakeryDetails: List<BakeryDetailModel> = Gson().fromJson(reader, type)
        bakeryDetailList.addAll(bakeryDetails)
        reader.close()
    }
}
