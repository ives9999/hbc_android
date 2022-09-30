package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.utilities.CITY_KEY
import tw.com.bluemobile.hbc.utilities.Global
import tw.com.bluemobile.hbc.utilities.KeyEnum

class More @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    LinearLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.more, this)
    private var keyEnum: KeyEnum = KeyEnum.city_id
    private var valueTV: TextView? = null
    private var cancelIV: ImageView? = null
    var value: String = ""

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.More, 0, 0)

            val key = typedArray.getString(R.styleable.More_key) ?: ""
            keyEnum = KeyEnum.enumFromString(key)

            view.findViewById<TextView>(R.id.titleTV) ?. let {
                it.text = typedArray.getString(R.styleable.More_moreTitleTV) ?: ""
            }
        }

        view.findViewById<TextView>(R.id.valueTV) ?. let {
            valueTV = it
        }

        view.findViewById<ImageView>(R.id.clear) ?. let {
            cancelIV = it
            it.setOnClickListener {
                clear()
            }
        }
    }

    fun clear() {
        value = ""
        valueTV?.text = ""
    }

    fun setOnClickListener(lambda: () -> Unit) {
        valueTV?.setOnClickListener {
            lambda()
        }
    }

    fun setOnCancelClickListener(lambda: () -> Unit) {
        cancelIV?.setOnClickListener {
            lambda()
        }
    }

    fun setText(text: String) {
        valueTV?.text = text
    }

    fun toMoreDialog(screenWidth: Int, selected: String, delegate: BaseActivity): MoreDialog {

        val moreDialog = MoreDialog(context, screenWidth, keyEnum, selected, delegate)
        moreDialog.setContentView(R.layout.select_single)
        moreDialog.init(false, keyEnum.chineseName)
        moreDialog.setAdapter()
        moreDialog.show(30)

        return moreDialog
    }

}