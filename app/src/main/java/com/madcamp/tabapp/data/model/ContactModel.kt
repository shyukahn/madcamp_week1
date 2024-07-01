package com.madcamp.tabapp.data.model

import com.google.gson.annotations.SerializedName

data class ContactModel(
    @SerializedName("id") val storeImage: Int,
    @SerializedName("name") val storeName: String,
    @SerializedName("phone") val storeNumber: String,
    @SerializedName("address") val storeAddress: String,
    @SerializedName("isBookmarked") val isBookmarked: Boolean
)