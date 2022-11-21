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
    override var dateTextColor: Int
        get() = R.color.MY_BLACK
        set(value) {}

    init {
        myInit(view)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ProcessCenterNode, 0, 0)

            if (typedArray.hasValue(R.styleable.ProcessCenterNode_ProcessCenterNodeTitleTV)) {
                titleTV?.text =
                    typedArray.getString(R.styleable.ProcessCenterNode_ProcessCenterNodeTitleTV)
                        ?: ""
            }

            if (typedArray.hasValue(R.styleable.ProcessCenterNode_ProcessCenterNodeTextColor)) {
                val color: Int = typedArray.getColor(R.styleable.ProcessCenterNode_ProcessCenterNodeTextColor, getColor(context, R.color.MY_WHITE))
                titleTV?.setTextColor(color)
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

//    override fun setOpen() {
//        super.setOpen()
//        dateTV
//    }
}