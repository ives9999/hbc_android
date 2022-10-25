package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.*
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.dpToPx

class ThreeRadio @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.three_radio, this)
    private var threeGroup: RadioGroup? = null
    private var oneRB: RadioButton? = null
    private var twoRB: RadioButton? = null
    private var threeRB: RadioButton? = null

    init {
        attrs?.let { it ->
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ThreeRadio, 0, 0)

            key = typedArray.getString(R.styleable.ThreeRadio_threeKey) ?: ""

            view.findViewById<TextView>(R.id.titleTV) ?. let {
                titleTV = it
                it.text = typedArray.getString(R.styleable.ThreeRadio_threeRadioTitleTV) ?: ""
            }

            view.findViewById<ImageView>(R.id.starIV) ?. let {
                starIV = it
                val b: Boolean = typedArray.getBoolean(R.styleable.ThreeRadio_threeRadioStarIV, true)
                if (!b) {
                    starIV!!.visibility = View.GONE
                }
            }

            view.findViewById<RadioGroup>(R.id.three_group) ?. let {
                threeGroup = it
                setOnGroupCheckedChangeListener()
            }

            view.findViewById<RadioButton>(R.id.one) ?. let {
                oneRB = it
                it.text = typedArray.getString(R.styleable.ThreeRadio_oneRadioTitle) ?: ""
            }

            view.findViewById<RadioButton>(R.id.two) ?. let {
                twoRB = it
                it.text = typedArray.getString(R.styleable.ThreeRadio_twoRadioTitle) ?: ""
            }

            view.findViewById<RadioButton>(R.id.three) ?. let {
                threeRB = it
                it.text = typedArray.getString(R.styleable.ThreeRadio_threeRadioTitle) ?: ""
            }

            view.findViewById<LinearLayout>(R.id.titleLL) ?. let {
                val widthStr: String = typedArray.getString(R.styleable.ThreeRadio_threeRadioTitleWidth) ?: "130"
                val width: Int = widthStr.toInt().dpToPx()
                val params: ViewGroup.LayoutParams = it.layoutParams
                params.width = width
                it.layoutParams = params
            }
        }
    }

    private fun setOnGroupCheckedChangeListener() {
        threeGroup?.setOnCheckedChangeListener { radioGroup, i ->
            val radioButton: RadioButton = view.findViewById(i)
            value = radioButton.text.toString()
        }
    }

    fun setOnGroupCheckedChangeListener(lambda: (String) -> Unit) {
        threeGroup?.setOnCheckedChangeListener { radioGroup, i ->
            val radioButton: RadioButton = view.findViewById(i)
            lambda(radioButton.text.toString())
        }
    }

    fun setCheck(text: String) {
        val a = oneRB?.text
        if (oneRB?.text == text) {
            oneRB?.isChecked = true
            value = text
        } else if (twoRB?.text == text) {
            twoRB?.isChecked = true
        } else {
            threeRB?.isChecked = true
        }
    }

}



















