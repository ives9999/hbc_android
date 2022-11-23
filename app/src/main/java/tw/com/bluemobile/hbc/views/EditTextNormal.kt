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
import tw.com.bluemobile.hbc.extensions.setImage
import tw.com.bluemobile.hbc.extensions.toEditable
import tw.com.bluemobile.hbc.utilities.getColor

class EditTextNormal @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.edit_text_normal, this)
    private var editET: EditText? = null
    private var theme: String = "light"

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

            if (typedArray.hasValue(R.styleable.EditTextNormal_myTheme)) {
                theme = typedArray.getString(R.styleable.EditTextNormal_myTheme) ?: "light"
            }

            view.findViewById<TextView>(R.id.titleTV) ?. let { iit ->
                titleTV = iit
                iit.text = typedArray.getString(R.styleable.EditTextNormal_titleTV) ?: ""

                if (theme == "dark") {
                    iit.setTextColor(getColor(context, R.color.MY_WHITE))
                }
            }

            view.findViewById<EditText>(R.id.valueET) ?. let { iit ->
                editET = iit

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

                if (theme == "dark") {
                    iit.setTextColor(getColor(context, R.color.MY_WHITE))
                }
            }

            view.findViewById<ImageView>(R.id.starIV) ?. let {
                starIV = it
                val b: Boolean = typedArray.getBoolean(R.styleable.EditTextNormal_starIV, true)
                if (!b) {
                    starIV!!.visibility = View.GONE
                }
            }

            key = typedArray.getString(R.styleable.EditTextNormal_key) ?: ""

            if (theme == "dark") {
                view.findViewById<TextView>(R.id.colonTV) ?. let { iit ->
                    iit.setTextColor(getColor(context, R.color.MY_WHITE))
                }
                view.findViewById<LinearLayout>(R.id.part1LL) ?. let { linearLayout ->
                    linearLayout.background = ContextCompat.getDrawable(context, R.drawable.edit_text_border_white)
                }
                view.findViewById<ImageView>(R.id.clear) ?. let { iit ->
                    iit.setImage("ic_clear_white")
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



















