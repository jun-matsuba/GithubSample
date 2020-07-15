package com.example.matsubajun.githubsample.ui.common

import android.net.Uri
import android.view.View
import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder

@BindingAdapter("imageUrl")
fun bindImageFromImageUrl(simpleDraweeView: SimpleDraweeView, imageUrl: String?) {
    if (imageUrl == null || imageUrl.isEmpty()) {
        return
    }
    val imageRequest: ImageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl)).build()
    simpleDraweeView.setImageRequest(imageRequest)
}

@BindingAdapter("visibleGone")
fun bindViewVisibility(view: View, visible: Boolean) {
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}