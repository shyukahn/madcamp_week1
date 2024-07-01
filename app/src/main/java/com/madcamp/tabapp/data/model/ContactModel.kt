package com.madcamp.tabapp.data.model

import com.google.gson.annotations.SerializedName

data class ContactModel(
    @SerializedName("id") val storeId: Int,
    @SerializedName("name") val storeName: String,
    @SerializedName("phone") val storeNumber: String,
    @SerializedName("address") val storeAddress: String,
    @SerializedName("thumbnail") val storeThumbnail: String,
    @SerializedName("isBookmarked") val isBookmarked: Boolean
)