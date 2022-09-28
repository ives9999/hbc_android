package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity

class Action @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    LinearLayout(context, attrs, defStyleAttr) {

    val view = View.inflate(context, R.layout.action, this)

    init {
//        view.findViewById<Button>(R.id.submit) ?. let {
//            it.setOnClickListener {
//                (context as? BaseActivity) ?. let { delegate ->
//                    delegate.submit()
//                }
//            }
//        }
//
    }

    fun setOnCancelListener(lambda: () -> Unit) {
        view.findViewById<Button>(R.id.cancel) ?. let {
            it.setOnClickListener {
                lambda()
            }
        }
    }
}