package com.madcamp.tabapp.data.model

import android.net.Uri

data class PhotoModel(
    val text: String,
    val id: Int? = null,
    val uri: Uri? = null
) {
    val isId: Boolean
        get() = id != null
}
