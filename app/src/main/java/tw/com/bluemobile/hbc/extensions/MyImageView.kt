package tw.com.bluemobile.hbc.extensions

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setImage(name: String) {
    val id = context.resources.getIdentifier(name, "drawable", context.packageName)
    this.setImageResource(id)
}

fun ImageView.setDrawableImage(@DrawableRes resource: Int, applyCircle: Boolean = false) {
    val glide = Glide.with(this).load(resource)
    if (applyCircle) {
        glide.apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        glide.into(this)
    }
}

fun ImageView.setLocalImage(uri: Uri, applyCircle: Boolean = false) {
    val glide = Glide.with(this).load(uri)
    if (applyCircle) {
        glide.apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        glide.into(this)
    }
}
