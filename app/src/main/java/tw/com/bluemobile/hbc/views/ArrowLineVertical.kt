package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.utilities.then

class ArrowLineVertical @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.arrow_line_vertical, this)
    private var line: View? = null
    private var arrow: LinearLayout? = null

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ArrowLineVertical, 0, 0)

            if (typedArray.hasValue(R.styleable.ArrowLineVertical_ArrowLineVerticalHeight)) {
                view.findViewById<View>(R.id.line)?.let { view1 ->
                    line = view1
                    val height: Int = typedArray.getInt(
                        R.styleable.ArrowLineVertical_ArrowLineVerticalHeight,
                        400
                    )
                    view1.layoutParams.height = height
                }
            }

            if (typedArray.hasValue(R.styleable.ArrowLineVertical_ArrowLineVerticalArrowShow)) {
                view.findViewById<LinearLayout>(R.id.arrow)?.let { linearLayout ->
                    arrow = linearLayout
                    val isShow: Boolean = typedArray.getBoolean(
                        R.styleable.ArrowLineVertical_ArrowLineVerticalArrowShow,
                        false
                    )
                    linearLayout.visibility = (isShow then { View.VISIBLE }) ?: View.GONE
                }
            }
        }
    }
}