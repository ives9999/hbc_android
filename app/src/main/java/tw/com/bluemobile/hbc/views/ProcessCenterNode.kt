package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.utilities.getColor

class ProcessCenterNode @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    ProcessNode(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.process_center_node, this)

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ProcessCenterNode, 0, 0)

            view.findViewById<TextView>(R.id.titleTV)?.let { textView ->
                titleTV = textView
                if (typedArray.hasValue(R.styleable.ProcessCenterNode_ProcessCenterNodeTitleTV)) {
                    textView.text =
                        typedArray.getString(R.styleable.ProcessCenterNode_ProcessCenterNodeTitleTV)
                            ?: ""
                }

                if (typedArray.hasValue(R.styleable.ProcessCenterNode_ProcessCenterNodeTextColor)) {
                    val color: Int = typedArray.getColor(R.styleable.ProcessCenterNode_ProcessCenterNodeTextColor, getColor(context, R.color.MY_WHITE))
                    textView.setTextColor(color)
                }
            }

            view.findViewById<LinearLayout>(R.id.nodeLL) ?. let { linearLayout ->
                nodeLL = linearLayout
                if (typedArray.hasValue(R.styleable.ProcessCenterNode_ProcessCenterNodeBackground)) {
                    val color: Int = typedArray.getColor(R.styleable.ProcessCenterNode_ProcessCenterNodeBackground, getColor(context, R.color.PROCESS_BK_BLUE))
                    linearLayout.setBackgroundColor(color)
                }
            }
        }



















    }
}