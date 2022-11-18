package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.utilities.then

class LineVertical @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.line_vertical, this)
    private var line: View? = null

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.LineVertical, 0, 0)

            if (typedArray.hasValue(R.styleable.LineVertical_LineVerticalHeight)) {
                view.findViewById<View>(R.id.line)?.let { view1 ->
                    line = view1
                    val height: Int = typedArray.getInt(
                        R.styleable.LineVertical_LineVerticalHeight,
                        100
                    )
                    view1.layoutParams.height = height
                }
            }
        }
    }
}