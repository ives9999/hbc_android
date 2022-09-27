package tw.com.bluemobile.hbc.Views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R

class EditTextNormal @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    LinearLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.edit_text_normal, this)

    init {
        if (attrs != null) {
            val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.EditTextNormal, 0, 0)

            view.findViewById<TextView>(R.id.titleTV) ?. let {
                it.text = attributes.getString(R.styleable.EditTextNormal_titleTV) ?: ""
            }
        }
    }
}



















