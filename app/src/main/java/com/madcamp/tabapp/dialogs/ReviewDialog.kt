package com.madcamp.tabapp.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.madcamp.tabapp.adapters.PhotosAdapter
import com.madcamp.tabapp.data.Review
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
                photosAdapter.addReview(Review(
                    name = binding.dialogTitle.text.toString(),
                    reviewText = binding.dialogReview.text.toString(),
                    imageUri = uri.toString(),
                    writer = "user"
                ))
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(uri, flag)
                dismiss()
            }
        }

        binding.dialogCancelButton.setOnClickListener {
            dismiss()
        }
    }
}