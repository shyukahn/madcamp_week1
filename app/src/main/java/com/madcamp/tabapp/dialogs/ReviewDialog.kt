package com.madcamp.tabapp.dialogs

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.madcamp.tabapp.adapters.PhotosAdapter
import com.madcamp.tabapp.data.PhotoModel
import com.madcamp.tabapp.databinding.DialogAddReviewBinding

class ReviewDialog(context: Context, private val photosAdapter: PhotosAdapter, private val uri: Uri) : Dialog(context) {
    private lateinit var binding: DialogAddReviewBinding
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddReviewBinding.inflate(layoutInflater)
        binding.dialogImage.setImageURI(uri)
        setContentView(binding.root)
        setCancelable(false)

        binding.dialogOkButton.setOnClickListener {
            if (binding.dialogTitle.text.isNullOrBlank()) {
                if (toast == null) {
                    toast = Toast.makeText(context, "제목을 입력해주세요", Toast.LENGTH_SHORT)
                }
                toast!!.show()
            } else {
                photosAdapter.addPhoto(PhotoModel(binding.dialogTitle.text.toString(), uri = uri))
                dismiss()
            }
        }

        binding.dialogCancelButton.setOnClickListener {
            dismiss()
        }
    }
}