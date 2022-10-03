package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.CheckBox
import tw.com.bluemobile.hbc.R

class Privacy @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.privacy, this)
    private var isCheck: Boolean = false
    private var checkBox: CheckBox? = null

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.Privacy, 0, 0)
            key = typedArray.getString(R.styleable.Privacy_privacyKey) ?: ""
        }

        view.findViewById<CheckBox>(R.id.checkBox) ?. let {
            checkBox = it

//            it.setOnCheckedChangeListener { compoundButton, b ->
//                println(b)
//            }
        }
    }

    fun getCheck(): Boolean {
        return isCheck
    }

    override fun isEmpty(): Boolean {
        return !isCheck
    }

    fun setCheck(b: Boolean) {
        isCheck = b
        checkBox?.isChecked = b
    }

    fun setOnCheckChangeListener(lambda: (Boolean) -> Unit) {
        checkBox?.setOnCheckedChangeListener { compoundButton, isCheck ->
            setCheck(isCheck)
            lambda(isCheck)
        }
    }
}