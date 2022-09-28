package tw.com.bluemobile.hbc.Views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity

class More @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    LinearLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.more, this)

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.More, 0, 0)

            view.findViewById<TextView>(R.id.titleTV) ?. let {
                it.text = typedArray.getString(R.styleable.More_moreTitleTV) ?: ""
            }
        }

        view.findViewById<TextView>(R.id.valueTV) ?. let {
            it.setOnClickListener {
                (context as? BaseActivity) ?. let { delegate->
                    delegate.toSelectCity(delegate)
                }
            }
        }

        view.findViewById<ImageView>(R.id.clear) ?. let {
            it.setOnClickListener {
                //editET?.text = "".toEditable()
            }
        }
    }

}