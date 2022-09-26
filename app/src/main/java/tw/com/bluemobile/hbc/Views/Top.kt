package tw.com.bluemobile.hbc.Views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.utilities.then

class Top @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    RelativeLayout(context, attrs, defStyleAttr) {

    val view = View.inflate(context, R.layout.top, this)
    private var prevIV: ImageButton = view.findViewById(R.id.prev)
    private var titleTV: TextView = view.findViewById(R.id.title)

    init {
        (context as? BaseActivity) ?. let { delegate->
            prevIV.setOnClickListener {
                delegate.prev()
            }
        }
    }


    fun showPrev(isShow: Boolean = true) {
        prevIV.visibility = (isShow then { VISIBLE }) ?: INVISIBLE
    }

    fun setTitle(title: String) {
        titleTV.text = title
    }
}