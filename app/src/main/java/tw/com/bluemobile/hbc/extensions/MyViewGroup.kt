package tw.com.bluemobile.hbc.extensions

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import tw.com.bluemobile.hbc.R

fun ViewGroup.setInfo(context: Context, info: String): TextView {

    val label: TextView = TextView(context)
    val lp: ViewGroup.MarginLayoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)
    lp.topMargin = 400
    label.layoutParams = lp
    label.gravity = Gravity.CENTER_HORIZONTAL
    label.setTextColor(ContextCompat.getColor(context, R.color.MY_BLACK))
    label.text = info

    this.addView(label)

    return label
}
