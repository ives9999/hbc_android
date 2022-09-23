package tw.com.bluemobile.hbc.extensions

import android.widget.ImageView

fun ImageView.setImage(name: String) {
    val id = context.resources.getIdentifier(name, "drawable", context.packageName)
    this.setImageResource(id)
}