package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import tw.com.bluemobile.hbc.R

class ProcessLeftNode @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.process_left_node, this)

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ProcessLeftNode, 0, 0)

            view.findViewById<TextView>(R.id.titleTV)?.let {
                titleTV = it
                it.text = typedArray.getString(R.styleable.ProcessLeftNode_ProcessLeftNodeTitleTV) ?: ""
            }
        }
    }
}