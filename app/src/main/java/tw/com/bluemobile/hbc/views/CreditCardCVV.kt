package tw.com.bluemobile.hbc.views

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.afterTextChanged
import tw.com.bluemobile.hbc.extensions.toEditable
import tw.com.bluemobile.hbc.utilities.getColor

class CreditCardCVV @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.credit_card_cvv, this)
    private var valueET: EditText? = null

    override var value: String = ""
        get() {
            valueET?.text ?. let {
                field = it.toString()
            }

            return field
        }
        set(value) { valueET?.text = value.toEditable() }

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CreditCardCvv, 0, 0)

            if (typedArray.hasValue(R.styleable.CreditCardCvv_creditCardCvvTextColor)) {
                val color: Int = typedArray.getColor(R.styleable.CreditCardCvv_creditCardCvvTextColor, getColor(context, R.color.MY_BLACK))
                setStyle(color)
            }

            view.findViewById<ImageView>(R.id.starIV)?.let { it1 ->
                starIV = it1
                val b: Boolean =
                    typedArray.getBoolean(R.styleable.CreditCardCvv_creditCardCvvStarIV, true)
                if (!b) {
                    starIV!!.visibility = View.GONE
                }
            }

            view.findViewById<EditText>(R.id.valueET) ?. let { it1 ->
                valueET = it1
                it1.inputType = InputType.TYPE_CLASS_PHONE
            }
        }
    }

    fun initFocus() {
        valueET?.requestFocus()
    }

    private fun setStyle(color: Int) {
        val idsTV: ArrayList<Int> = arrayListOf(
            R.id.titleTV,
            R.id.colonTV
        )
        for (idTV in idsTV) {
            view.findViewById<TextView>(idTV)?.setTextColor(color)
        }

        val partsLL: ArrayList<Int> = arrayListOf(
            R.id.part1LL
        )
        for (partLL in partsLL) {
            view.findViewById<LinearLayout>(partLL) ?. let { linearLayout ->
                linearLayout.background = ContextCompat.getDrawable(context, R.drawable.edit_text_border_white)
            }
        }

        val partsET: java.util.ArrayList<Int> = arrayListOf(
            R.id.valueET
        )
        for (partET in partsET) {
            view.findViewById<EditText>(partET)?.setTextColor(color)
        }
    }
}