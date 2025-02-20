package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.*
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.dpToPx

class TwoRadio @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.two_radio, this)
    private var yesNoGroup: RadioGroup? = null
    private var yesRB: RadioButton? = null
    private var noRB: RadioButton? = null

    init {
        attrs?.let { it ->
            val typedArray = context.obtainStyledAttributes(it, R.styleable.TwoRadio, 0, 0)

            key = typedArray.getString(R.styleable.TwoRadio_twoKey) ?: ""

            view.findViewById<TextView>(R.id.titleTV) ?. let {
                titleTV = it
                it.text = typedArray.getString(R.styleable.TwoRadio_twoRadioTitleTV) ?: ""
            }

            view.findViewById<ImageView>(R.id.starIV) ?. let {
                starIV = it
                val b: Boolean = typedArray.getBoolean(R.styleable.TwoRadio_twoRadioStarIV, true)
                if (!b) {
                    starIV!!.visibility = View.GONE
                }
            }

            view.findViewById<RadioGroup>(R.id.yes_no_group) ?. let {
                yesNoGroup = it
                setOnGroupCheckedChangeListener()
            }

            view.findViewById<RadioButton>(R.id.yes) ?. let {
                yesRB = it
                it.text = typedArray.getString(R.styleable.TwoRadio_yesRadioTitle) ?: ""

                val width: Int = typedArray.getInteger(R.styleable.TwoRadio_yesRadioWidth, 80).dpToPx()
                setWidth(it, width)
            }

            view.findViewById<RadioButton>(R.id.no) ?. let {
                noRB = it
                it.text = typedArray.getString(R.styleable.TwoRadio_noRadioTitle) ?: ""

                val width: Int = typedArray.getInteger(R.styleable.TwoRadio_noRadioWidth, 80).dpToPx()
                it.width = width
                setWidth(it, width)
            }

            view.findViewById<LinearLayout>(R.id.titleLL) ?. let {
                val width: Int = typedArray.getInteger(R.styleable.TwoRadio_twoRadioTitleWidth, 100).dpToPx()
                setWidth(it, width)
            }
        }
    }

    private fun setOnGroupCheckedChangeListener() {
        yesNoGroup?.setOnCheckedChangeListener { radioGroup, i ->
            val radioButton: RadioButton = view.findViewById(i)
            value = radioButton.text.toString()
        }
    }

    fun setOnGroupCheckedChangeListener(lambda: (String) -> Unit) {
        yesNoGroup?.setOnCheckedChangeListener { radioGroup, i ->
            val radioButton: RadioButton = view.findViewById(i)
            value = radioButton.text.toString()
            lambda(radioButton.text.toString())
        }
    }

    fun setCheck(text: String) {
        val a = yesRB?.text
        if (yesRB?.text == text) {
            yesRB?.isChecked = true
            value = text
        } else {
            noRB?.isChecked = true
        }
    }

    fun setWidth(it: View, width: Int) {
        val params: ViewGroup.LayoutParams = it.layoutParams
        params.width = width
        it.layoutParams = params
    }
}



















