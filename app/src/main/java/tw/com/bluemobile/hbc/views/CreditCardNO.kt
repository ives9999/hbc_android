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
import androidx.core.widget.addTextChangedListener
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.afterTextChanged
import tw.com.bluemobile.hbc.extensions.toEditable
import tw.com.bluemobile.hbc.utilities.getColor

class CreditCardNO @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.credit_card_no, this)
    private var part1ET: EditText? = null
    private var part2ET: EditText? = null
    private var part3ET: EditText? = null
    private var part4ET: EditText? = null

    override var value: String = ""
        get() {
            var no: String = ""
            part1ET?.text ?. let {
                no += it.toString()
            }
            part2ET?.text ?. let {
                no += it.toString()
            }
            part3ET?.text ?. let {
                no += it.toString()
            }
            part4ET?.text ?. let {
                no += it.toString()
            }

            field = no

            return field
        }
        set(value) {  }

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CreditCardNO, 0, 0)

            view.findViewById<ImageView>(R.id.starIV) ?. let {
                starIV = it
                val b: Boolean = typedArray.getBoolean(R.styleable.CreditCardNO_creditCardNOStarIV, true)
                if (!b) {
                    starIV!!.visibility = View.GONE
                }
            }

            if (typedArray.hasValue(R.styleable.CreditCardNO_creditCardNOTextColor)) {
                val color: Int = typedArray.getColor(R.styleable.CreditCardNO_creditCardNOTextColor, getColor(context, R.color.MY_BLACK))
                setStyle(color)
            }

            view.findViewById<EditText>(R.id.part1ET) ?. let { it1 ->
                part1ET = it1
                it1.inputType = InputType.TYPE_CLASS_PHONE
                it1.afterTextChanged { it2 ->
                    if (it2.length == 4) {
                        part2ET?.requestFocus()
                    }
                }
            }

            view.findViewById<EditText>(R.id.part2ET) ?. let { it1 ->
                part2ET = it1
                it1.inputType = InputType.TYPE_CLASS_PHONE
                it1.afterTextChanged { it2 ->
                    if (it2.length == 4) {
                        part3ET?.requestFocus()
                    }
                }
            }

            view.findViewById<EditText>(R.id.part3ET) ?. let { it1 ->
                part3ET = it1
                it1.inputType = InputType.TYPE_CLASS_PHONE
                it1.afterTextChanged { it2 ->
                    if (it2.length == 4) {
                        part4ET?.requestFocus()
                    }
                }
            }

            view.findViewById<EditText>(R.id.part4ET) ?. let { it1 ->
                part4ET = it1
                it1.inputType = InputType.TYPE_CLASS_PHONE
            }
        }
    }

    private fun setStyle(color: Int) {
        val idsTV: ArrayList<Int> = arrayListOf(
            R.id.titleTV,
            R.id.colonTV,
            R.id.dash1TV,
            R.id.dash2TV,
            R.id.dash3TV
        )
        for (idTV in idsTV) {
            view.findViewById<TextView>(idTV) ?. let { textView ->
                textView.setTextColor(color)
            }
        }

        val partsLL: ArrayList<Int> = arrayListOf(
            R.id.part1LL, R.id.part2LL, R.id.part3LL, R.id.part4LL
        )
        for (partLL in partsLL) {
            view.findViewById<LinearLayout>(partLL) ?. let { linearLayout ->
                linearLayout.background = ContextCompat.getDrawable(context, R.drawable.edit_text_border_white)
            }
        }

        val partsET: ArrayList<Int> = arrayListOf(
            R.id.part1ET, R.id.part2ET, R.id.part3ET, R.id.part4ET
        )
        for (partET in partsET) {
            view.findViewById<EditText>(partET) ?. let { editText ->
                editText.setTextColor(color)
            }
        }
    }
}