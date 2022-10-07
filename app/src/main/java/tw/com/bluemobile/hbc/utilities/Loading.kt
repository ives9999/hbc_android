package tw.com.bluemobile.hbc.utilities

import android.view.View
import android.widget.FrameLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity

class Loading(baseActivity: BaseActivity) {

    var frameLayout: FrameLayout? = null

    init {
        baseActivity.findViewById<FrameLayout>(R.id.loading_overlay) ?. let {
            frameLayout = it
        }
    }

    fun show() {
        frameLayout?.visibility = View.VISIBLE
    }

    fun hide() {
        frameLayout?.visibility = View.INVISIBLE
    }
}