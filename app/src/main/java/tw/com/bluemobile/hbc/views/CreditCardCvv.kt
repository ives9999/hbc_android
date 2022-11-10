package tw.com.bluemobile.hbc.views

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.afterTextChanged
import tw.com.bluemobile.hbc.extensions.toEditable

class CreditCardCvv @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
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

            view.findViewById<ImageView>(R.id.starIV)?.let { it1 ->
                starIV = it1
                val b: Boolean =
                    typedArray.getBoolean(R.styleable.CreditCardNO_creditCardNOStarIV, true)
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
}