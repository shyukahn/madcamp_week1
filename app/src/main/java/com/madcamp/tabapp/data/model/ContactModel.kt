package com.madcamp.tabapp.data

import com.google.gson.annotations.SerializedName

data class ContactModel(
    @SerializedName("id") val storeImage: Int,
    @SerializedName("name") val storeName: String,
    @SerializedName("phone") val storeNumber: String,
    @SerializedName("location") val storeLocation: String
)