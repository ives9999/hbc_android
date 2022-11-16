package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.utilities.then

class ArrowLineVertical @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.arrow_line_vertical, this)
    private val line: View? = null
    private val arrow: View? = null

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ArrowLineVertical, 0, 0)

            view.findViewById<View>(R.id.line) ?. let {
                val height: Int = typedArray.getInt(R.styleable.ArrowLineVertical_ArrowLineVerticalHeight, 100)
                it.layoutParams.height = height
            }

            view.findViewById<View>(R.id.arrow) ?. let {
                val isShow: Boolean = typedArray.getBoolean(R.styleable.ArrowLineVertical_ArrowLineVerticalArrowShow, false)
                it.visibility = (isShow then { View.VISIBLE }) ?: View.GONE
            }
        }
    }
}