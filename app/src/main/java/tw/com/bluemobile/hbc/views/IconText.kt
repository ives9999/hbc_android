package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.setImage

class IconText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.icon_text, this)
    private var iconIV: ImageView? = null
    private var valueTV: TextView? = null
    private var unitTV: TextView? = null

    init {
        attrs?.let { attributeSet ->
            val typedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.IconText, 0, 0)

            view.findViewById<ImageView>(R.id.iconIV) ?. let { IV ->
                iconIV = IV
                if (typedArray.hasValue(R.styleable.IconText_IconTextIconIV)) {
                    typedArray.getString(R.styleable.IconText_IconTextIconIV)
                        ?.let { icon ->
                            IV.setImage(icon) }
                        ?: ""
                }
            }

            view.findViewById<TextView>(R.id.titleTV) ?. let {
                titleTV = it
                if (typedArray.hasValue(R.styleable.IconText_IconTextTitleTV)) {
                    it.text = typedArray.getString(R.styleable.IconText_IconTextTitleTV)
                }
            }

            view.findViewById<TextView>(R.id.valueTV) ?. let {
                valueTV = it
                if (typedArray.hasValue(R.styleable.IconText_IconTextValueTV)) {
                    it.text = typedArray.getString(R.styleable.IconText_IconTextValueTV)
                }
            }

            view.findViewById<TextView>(R.id.unitTV) ?. let {
                unitTV = it
                if (typedArray.hasValue(R.styleable.IconText_IconTextUnitTV)) {
                    it.text = typedArray.getString(R.styleable.IconText_IconTextUnitTV)
                }
            }
        }
    }

    fun setIconIV(icon: String) {
        iconIV?.setImage(icon)
    }

    fun setTitleTV(value: String? = null) {
        titleTV?.text = value
    }

    fun setValueTV(value: String? = null) {
        valueTV?.text = value
    }

    fun setUnitTV(value: String? = null) {
        unitTV?.text = value
    }
}