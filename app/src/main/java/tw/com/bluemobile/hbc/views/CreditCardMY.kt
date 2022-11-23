package tw.com.bluemobile.hbc.views

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.*
import tw.com.bluemobile.hbc.utilities.getColor
import java.util.*

class CreditCardMY @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.credit_card_my, this)
    private var monthET: EditText? = null
    private var yearET: EditText? = null

    override var value: String = ""
        get() {
            var no: String = ""
            monthET?.text ?. let {
                no += it.toString() + "/"
            }
            yearET?.text ?. let {
                no += it.toString()
            }

            field = no

            return field
        }
        set(value) {  }

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CreditCardMY, 0, 0)

            if (typedArray.hasValue(R.styleable.CreditCardMY_creditCardMYTextColor)) {
                val color: Int = typedArray.getColor(R.styleable.CreditCardMY_creditCardMYTextColor, getColor(context, R.color.MY_BLACK))
                setStyle(color)
            }

            view.findViewById<ImageView>(R.id.starIV) ?. let { it1 ->
                starIV = it1
                val b: Boolean = typedArray.getBoolean(R.styleable.CreditCardNO_creditCardNOStarIV, true)
                if (!b) {
                    starIV!!.visibility = View.GONE
                }
            }

            view.findViewById<EditText>(R.id.monthET) ?. let { it1 ->
                monthET = it1
                it1.inputType = InputType.TYPE_CLASS_PHONE
                it1.afterTextChanged { it2 ->
                    if (it2.length == 2) {
                        yearET?.requestFocus()
                    }
                }
            }
            view.findViewById<EditText>(R.id.yearET) ?. let { it1 ->
                yearET = it1
                it1.inputType = InputType.TYPE_CLASS_PHONE
            }
        }
    }

    fun setOnChangeListener(warning: (String)->Unit) {

//        valueET?.addTextChangedListener(object : TextWatcher {
//
//            var b: Boolean = false
//
//            //s：Text 變化前的字串
//            //start：Text 開始變化的位置
//            //before：即將被取代的原字符長度
//            //count：剛剛取代原字符的字符個數
//            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
////                println("s: ${s}")
////                println("start: ${start}")
////                println("before: ${before}")
////                println("count: ${count}")
//                b = true
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (b) {
////                    println("s: ${s}")
////                    println("start: ${start}")
////                    println("before: ${before}")
////                    println("count: ${count}")
//                }
//            }
//
//            override fun afterTextChanged(it: Editable?) {
//                valueET?.removeTextChangedListener(this)
//                val m: String = it.toString() + "/"
//                valueET?.text = m.toEditable()
//                valueET?.requestFocus()
//            }
//        })


        monthET?.afterTextChanged {
            if (it.length == 2) {
                yearET?.requestFocus()
            }
            if (it.length == 2) {
                if (it.isInt() && it.toInt() > 12) {
                    warning("到期月為1到12")
                    monthET?.text = "".toEditable()
                }
            }
        }

        val yearInt: Int = Date().getY() - 2000
        yearET?.afterTextChanged {
            if (it.length == 2) {
                if (it.isInt() && (it.toInt() - yearInt < 0)) {
                    warning("到期年不能小於今年")
                    yearET?.text = "".toEditable()
                }

                if (monthET?.text.toString().length == 2) {
                    val yearString: String = (2000 + it.toInt()).toString()
                    val monthString: String =  (monthET?.text.toString().toInt()).toString()
                    val dayString: String = "01"
                    val dateString: String = "${yearString}-${monthString}-${dayString}"
                    val timeString: String = "00:00:00"

                    val expireDate: Date = ("${dateString} ${timeString}").toDate()!!
                    val now: Date = Date()
                    if (now > expireDate) {
                        warning("到期月年不能小於現在")
                    }
                }
            }
        }
    }

    private fun setStyle(color: Int) {
        val idsTV: ArrayList<Int> = arrayListOf(
            R.id.titleTV,
            R.id.colonTV,
            R.id.monthTV,
            R.id.slashTV,
            R.id.yearTV
        )
        for (idTV in idsTV) {
            view.findViewById<TextView>(idTV)?.setTextColor(color)
        }

        val partsLL: ArrayList<Int> = arrayListOf(
            R.id.part1LL, R.id.part2LL
        )
        for (partLL in partsLL) {
            view.findViewById<LinearLayout>(partLL) ?. let { linearLayout ->
                linearLayout.background = ContextCompat.getDrawable(context, R.drawable.edit_text_border_white)
            }
        }

        val partsET: ArrayList<Int> = arrayListOf(
            R.id.monthET, R.id.yearET
        )
        for (partET in partsET) {
            view.findViewById<EditText>(partET)?.setTextColor(color)
        }
    }
}