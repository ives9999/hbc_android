package tw.com.bluemobile.hbc.views

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.toEditable

class EditTextNormal @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.edit_text_normal, this)
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
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.EditTextNormal, 0, 0)

            view.findViewById<TextView>(R.id.titleTV) ?. let {
                titleTV = it
                it.text = typedArray.getString(R.styleable.EditTextNormal_titleTV) ?: ""
            }

            view.findViewById<EditText>(R.id.valueET) ?. let {
                editET = it

                val keyboard: String = typedArray.getString(R.styleable.EditTextNormal_keyboard) ?: ""
                if (keyboard == "password") {
                    //editET!!.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                    editET!!.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                } else if (keyboard == "number") {
                    editET!!.inputType = InputType.TYPE_CLASS_PHONE
                } else if (keyboard == "email") {
                    editET!!.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                }

                editET!!.text = (typedArray.getString(R.styleable.EditTextNormal_valueET) ?: "").toEditable()
            }

            key = typedArray.getString(R.styleable.EditTextNormal_key) ?: ""
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

//    override fun getValue(): String {
//        editET?.text ?. let {
//            value = it.toString()
//        }
//
//        return value
//    }

    override fun isEmpty(): Boolean {

        var res = false

        if (editET != null) {
            val text: String = editET!!.text.toString()
            if (text.trim().isEmpty()) {
                res = true
            }
        } else {
            res = true
        }

        return res
    }

    fun setInputType(inputType: Int) {
        editET?.inputType = inputType
    }

//    override fun setValue(value: String) {
//        editET?.text = value.toEditable()
//    }
}



















