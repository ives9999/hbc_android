package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.dpToPx
import tw.com.bluemobile.hbc.utilities.BloodProcessEnum
import tw.com.bluemobile.hbc.utilities.Global
import tw.com.bluemobile.hbc.utilities.getColor

class ProcessLeftNode @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    ProcessNode(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.process_left_node, this)

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ProcessLeftNode, 0, 0)

            view.findViewById<LinearLayout>(R.id.container) ?. let { container ->
                val screenWidth: Float = Global.getScreenWidth(resources).toFloat()
                var n: Int = 1
                n = n.dpToPx()
                container.layoutParams.width = ((screenWidth-n)/2).toInt()
            }

            view.findViewById<TextView>(R.id.titleTV)?.let { textView ->
                titleTV = textView
                if (typedArray.hasValue(R.styleable.ProcessLeftNode_ProcessLeftNodeTitleTV)) {
                    textView.text =
                        typedArray.getString(R.styleable.ProcessLeftNode_ProcessLeftNodeTitleTV)
                            ?: ""
                }

                if (typedArray.hasValue(R.styleable.ProcessLeftNode_ProcessLeftNodeTextColor)) {
                    val color: Int = typedArray.getColor(R.styleable.ProcessLeftNode_ProcessLeftNodeTextColor, getColor(context, R.color.MY_WHITE))
                    textView.setTextColor(color)
                }
            }

            view.findViewById<LinearLayout>(R.id.nodeLL) ?. let { linearLayout ->
                nodeLL = linearLayout
                if (typedArray.hasValue(R.styleable.ProcessLeftNode_ProcessLeftNodeBackground)) {
                    val color: Int = typedArray.getColor(R.styleable.ProcessLeftNode_ProcessLeftNodeBackground, getColor(context, R.color.PROCESS_BK_BLUE))
                    linearLayout.setBackgroundColor(color)
                }
            }
        }
    }
}