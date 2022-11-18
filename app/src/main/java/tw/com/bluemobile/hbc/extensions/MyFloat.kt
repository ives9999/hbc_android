package tw.com.bluemobile.hbc.extensions

import android.content.res.Resources

fun Float.dpToPx(): Float {

    return this * Resources.getSystem().displayMetrics.density
}

fun Float.pxToDp(): Float {
    return this / Resources.getSystem().displayMetrics.density
}