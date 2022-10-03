package tw.com.bluemobile.hbc.utilities

import android.content.res.Resources

class Global {

    companion object {
        fun getScreenHeight(resources: Resources): Int {
            val displayMetrics = resources.displayMetrics
            val density = displayMetrics.density
            val screenHeight = displayMetrics.heightPixels

            return screenHeight
        }

        fun getScreenWidth(resources: Resources): Int {
            val displayMetrics = resources.displayMetrics
            val density = displayMetrics.density
            val screenWidth = displayMetrics.widthPixels

            return screenWidth
        }

        fun isEmulator(): Boolean {
            return isEmulator
        }
    }
}