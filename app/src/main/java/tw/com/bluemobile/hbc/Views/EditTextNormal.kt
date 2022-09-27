package tw.com.bluemobile.hbc.Views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.toEditable

class EditTextNormal @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    LinearLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.edit_text_normal, this)
    private var editET: EditText? = null

    init {
        if (attrs != null) {
            val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.EditTextNormal, 0, 0)

            view.findViewById<TextView>(R.id.titleTV) ?. let {
                it.text = attributes.getString(R.styleable.EditTextNormal_titleTV) ?: ""
            }

            view.findViewById<EditText>(R.id.valueET) ?. let {
                editET = it
                editET!!.text = (attributes.getString(R.styleable.EditTextNormal_valueET) ?: "").toEditable()
            }
        }

        view.findViewById<ImageView>(R.id.clear) ?. let {
            it.setOnClickListener {
                editET?.text = "".toEditable()
            }
        }
    }

    fun isEmpty(): Boolean {

        var res = false

        if (editET != null) {
            val text: String = editET!!.text.toString()
            if (text.trim().isEmpty()) {
                res = true
            }
        } else {
            res = true
        }

        return res
    }
}



















