package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.dpToPx
import tw.com.bluemobile.hbc.utilities.Global
import tw.com.bluemobile.hbc.utilities.getColor

class ProcessRightNode @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    ProcessNode(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.process_right_node, this)
    override var background: Int
        get() = R.color.PROCESS_BK_GREEN
        set(value) {}
    override var textColor: Int
        get() = R.color.MY_WHITE
        set(value) {}

    init {
        myInit(view)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ProcessRightNode, 0, 0)

            view.findViewById<LinearLayout>(R.id.container) ?. let { container ->
                val screenWidth: Float = Global.getScreenWidth(resources).toFloat()
                var n: Int = 1
                n = n.dpToPx()
                container.layoutParams.width = ((screenWidth-n)/2).toInt()
            }

            if (typedArray.hasValue(R.styleable.ProcessRightNode_ProcessRightNodeTitleTV)) {
                titleTV?.text =
                    typedArray.getString(R.styleable.ProcessRightNode_ProcessRightNodeTitleTV)
                        ?: ""
            }

            if (typedArray.hasValue(R.styleable.ProcessRightNode_ProcessRightNodeTextColor)) {
                val color: Int = typedArray.getColor(R.styleable.ProcessRightNode_ProcessRightNodeTextColor, getColor(context, R.color.MY_WHITE))
                titleTV?.setTextColor(color)
            }

            view.findViewById<LinearLayout>(R.id.nodeLL) ?. let { linearLayout ->
                nodeLL = linearLayout
                if (typedArray.hasValue(R.styleable.ProcessRightNode_ProcessRightNodeBackground)) {
                    val color: Int = typedArray.getColor(R.styleable.ProcessRightNode_ProcessRightNodeBackground, getColor(context, R.color.PROCESS_BK_BLUE))
                    linearLayout.setBackgroundColor(color)
                }
            }
        }
    }
}