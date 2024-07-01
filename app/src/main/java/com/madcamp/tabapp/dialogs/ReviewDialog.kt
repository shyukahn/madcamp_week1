package com.madcamp.tabapp.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.madcamp.tabapp.adapters.PhotosAdapter
import com.madcamp.tabapp.data.model.PhotoModel
import com.madcamp.tabapp.data.Review
import com.madcamp.tabapp.databinding.DialogAddReviewBinding

class ReviewDialog(
    context: Context,
    private val photosAdapter: PhotosAdapter,
    private val review: Review,
    private val position: Int // -1 for add, position(>=0) for update
) : Dialog(context) {
    private lateinit var binding: DialogAddReviewBinding
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddReviewBinding.inflate(layoutInflater)
        val uri = Uri.parse(review.imageUri)
        binding.dialogImage.setImageURI(uri)
        binding.dialogTitle.setText(review.name)
        binding.dialogReview.setText(review.reviewText)
        setContentView(binding.root)
        setCancelable(false)

        binding.dialogOkButton.setOnClickListener {
            if (binding.dialogTitle.text.isNullOrBlank()) {
                if (toast == null) {
                    toast = Toast.makeText(context, "제목을 입력해주세요", Toast.LENGTH_SHORT)
                }
                toast!!.show()
            } else {
                val newReview = Review(
                    id = review.id,
                    name = binding.dialogTitle.text.toString(),
                    reviewText = binding.dialogReview.text.toString(),
                    imageUri = uri.toString(),
                    writer = "admin"
                )
                if (position >= 0){ // update
                    photosAdapter.updateReview(newReview, position)
                } else { // add
                    photosAdapter.addReview(newReview)
                    val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    context.contentResolver.takePersistableUriPermission(uri, flag)
                }
                dismiss()
            }
        }

        binding.dialogCancelButton.setOnClickListener {
            dismiss()
        }
    }
}