package tw.com.bluemobile.hbc.views

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.dpToPx
import tw.com.bluemobile.hbc.extensions.toEditable

class EditTextUnit @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.edit_text_unit, this)
    private var editET: EditText? = null

    override var value: String = ""
        get() {
            editET?.text ?. let {
                field = it.toString()
            }

            return field
        }
        set(value) { editET?.text = value.toEditable() }

    init {
        attrs?.let { attributeSet ->
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.EditTextUnit, 0, 0)

            view.findViewById<TextView>(R.id.titleTV) ?. let {
                titleTV = it
                //val a = typedArray.getString(R.styleable.EditTextUnit_unitTitleTV)
                it.text = typedArray.getString(R.styleable.EditTextUnit_unitTitleTV) ?: ""
            }

            view.findViewById<EditText>(R.id.valueET) ?. let {
                editET = it

                val keyboard: String = typedArray.getString(R.styleable.EditTextUnit_unitKeyboard) ?: ""
                if (keyboard == "password") {
                    //editET!!.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                    editET!!.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                } else if (keyboard == "number") {
                    editET!!.inputType = InputType.TYPE_CLASS_PHONE
                } else if (keyboard == "email") {
                    editET!!.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                }

                editET!!.text = (typedArray.getString(R.styleable.EditTextUnit_unitValueET) ?: "").toEditable()
            }

            view.findViewById<TextView>(R.id.unitTV) ?. let {
                it.text = typedArray.getString(R.styleable.EditTextUnit_unit)
            }

            key = typedArray.getString(R.styleable.EditTextUnit_unitKey) ?: ""

            view.findViewById<LinearLayout>(R.id.titleLL) ?. let {
                val widthStr: String = typedArray.getString(R.styleable.EditTextUnit_unitTitleLLWidth) ?: "130"
                val width: Int = widthStr.toInt().dpToPx()
                val params: ViewGroup.LayoutParams = it.layoutParams
                params.width = width
                it.layoutParams = params
            }

            view.findViewById<LinearLayout>(R.id.valueLL) ?. let {
                val widthStr: String = typedArray.getString(R.styleable.EditTextUnit_unitValueLLWidth) ?: "238"
                val width: Int = widthStr.toInt().dpToPx()
                val params: ViewGroup.LayoutParams = it.layoutParams
                params.width = width
                it.layoutParams = params
            }

            view.findViewById<EditText>(R.id.valueET) ?. let {
                val widthStr: String = typedArray.getString(R.styleable.EditTextUnit_unitValueETWidth) ?: "160"
                val width: Int = widthStr.toInt().dpToPx()
                val params: ViewGroup.LayoutParams = it.layoutParams
                params.width = width
                it.layoutParams = params
            }

            view.findViewById<ImageView>(R.id.starIV) ?. let {
                starIV = it
                val b: Boolean = typedArray.getBoolean(R.styleable.EditTextUnit_unitStarIV, true)
                if (!b) {
                    starIV!!.visibility = View.GONE
                }
            }
        }

        view.findViewById<ImageView>(R.id.clear) ?. let {
            it.setOnClickListener {
                clear()
            }
        }
    }

    override fun clear() {
        editET?.text = "".toEditable()
    }

}