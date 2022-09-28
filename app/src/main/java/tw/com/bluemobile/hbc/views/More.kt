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
    private var moreDialog: MoreDialog? = null
    private var valueTV: TextView? = null
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
//            it.setOnClickListener {
//                if (keyEnum == KeyEnum.area_id) {
//
//                }
//
//                (context as? BaseActivity) ?. let { delegate->
//                    val screenWidth = Global.getScreenWidth(delegate.resources)
//                    moreDialog = toMoreDialog(screenWidth,value, delegate)
//                }
//            }
        }

        view.findViewById<ImageView>(R.id.clear) ?. let {
            it.setOnClickListener {
                value = ""
                valueTV?.text = ""
            }
        }
    }

    fun setOnClickListener(lambda: () -> Unit) {
        println("aaa")
        valueTV?.setOnClickListener {
            lambda()
        }
    }

    fun setText(text: String) {
        valueTV?.text = text
    }

    fun closeMoreDialog() {
        moreDialog?.hide()
    }

    fun toMoreDialog(screenWidth: Int, selected: String, delegate: BaseActivity): MoreDialog {

        val moreDialog = MoreDialog(context, screenWidth, keyEnum, selected, delegate)
        moreDialog.setContentView(R.layout.select_single)
        moreDialog.init(false, keyEnum.chineseName)

//        if (key == AREA_KEY) {
//            moreDialog.city_id = city_id
//        }
        moreDialog.setAdapter()
        moreDialog.show(30)

        return moreDialog
    }

}