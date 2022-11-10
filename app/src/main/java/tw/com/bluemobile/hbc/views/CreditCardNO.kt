package tw.com.bluemobile.hbc.views

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.afterTextChanged
import tw.com.bluemobile.hbc.extensions.toEditable

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
}