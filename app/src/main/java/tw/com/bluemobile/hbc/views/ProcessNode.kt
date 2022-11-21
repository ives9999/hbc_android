package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.dpToPx
import tw.com.bluemobile.hbc.utilities.BloodProcessEnum
import tw.com.bluemobile.hbc.utilities.getColor

open class ProcessNode @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    protected var nodeLL: LinearLayout? = null
    protected var dateTV: TextView? = null
    open var process: BloodProcessEnum? = null
    open var background: Int = R.color.PROCESS_BK_YELLOW
    open var textColor: Int = R.color.MY_BLACK
    open var dateTextColor: Int = R.color.MY_WHITE

    open fun myInit(view: View) {
        view.findViewById<TextView>(R.id.titleTV)?.let { textView ->
            titleTV = textView
            setTitleBottomMargin(16)
        }

        view.findViewById<TextView>(R.id.dateTV) ?. let { textView ->
            dateTV = textView
            dateTV?.visibility = View.GONE
        }
    }

    fun setOnClickListener(lambda: (BloodProcessEnum) -> Unit) {
        nodeLL?.setOnClickListener {
            if (process != null) {
                lambda(process!!)
            }
        }
    }

    open fun setOpen(at: String) {
        nodeLL?.setBackgroundColor(getColor(context, background))
        titleTV?.setTextColor(getColor(context, textColor))
        dateTV?.visibility = View.VISIBLE
        dateTV?.setTextColor(getColor(context, dateTextColor))
        dateTV?.text = at
        setTitleBottomMargin(4)
    }

    private fun setTitleBottomMargin(value: Int) {
        val param = titleTV?.layoutParams as ViewGroup.MarginLayoutParams
        param.bottomMargin = value.dpToPx()
        titleTV?.layoutParams = param
    }
}