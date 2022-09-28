package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.utilities.CITY_KEY
import tw.com.bluemobile.hbc.utilities.Global

class More @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    LinearLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.more, this)

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.More, 0, 0)

            view.findViewById<TextView>(R.id.titleTV) ?. let {
                it.text = typedArray.getString(R.styleable.More_moreTitleTV) ?: ""
            }
        }

        view.findViewById<TextView>(R.id.valueTV) ?. let {
            it.setOnClickListener {
                (context as? BaseActivity) ?. let { delegate->
                    val screenWidth = Global.getScreenWidth(delegate.resources)
                    toMoreDialog(CITY_KEY, "0", screenWidth, delegate)
                    //delegate.toSelectCity(delegate)
                }
            }
        }

        view.findViewById<ImageView>(R.id.clear) ?. let {
            it.setOnClickListener {
                //editET?.text = "".toEditable()
            }
        }
    }

    private fun toMoreDialog(key: String, selected: String, screenWidth: Int, delegate: BaseActivity) {

        val moreDialog = MoreDialog(context, screenWidth, key, delegate)
        moreDialog.setContentView(R.layout.select_single)

        moreDialog.findViewById<Top>(R.id.top) ?. let { itTop->
            itTop.showPrev(false)
            itTop.setTitle("縣市")
        }

        //moreDialog.setBottomButtonPadding(1, button_width)

//        if (key == AREA_KEY) {
//            val row1: OneRow = getOneRowFromKey(CITY_KEY)
//            val city_id_str: String = row1.value
//            val city_id: Int = ((city_id_str.isInt()) then { city_id_str.toInt() }) ?: 0
//            moreDialog.city_id = city_id
//        }
        moreDialog.setSelectSingle(selected, delegate)

        moreDialog.show(30)
    }

}