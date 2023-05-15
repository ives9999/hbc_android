package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.extensions.isInt
import tw.com.bluemobile.hbc.utilities.CITY_ID_KEY
import tw.com.bluemobile.hbc.utilities.KeyEnum
import tw.com.bluemobile.hbc.utilities.Zones

open class More @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    MyLayout(context, attrs, defStyleAttr) {

    protected val view: View = View.inflate(context, R.layout.more, this)
    var keyEnum: KeyEnum = KeyEnum.city_id
    var valueTV: TextView? = null
    private var cancelIV: ImageView? = null

    init {
        attrs?.let { it ->
            val typedArray = context.obtainStyledAttributes(it, R.styleable.More, 0, 0)

            key = typedArray.getString(R.styleable.More_moreKey) ?: ""
            keyEnum = KeyEnum.enumFromString(key)

            view.findViewById<TextView>(R.id.titleTV) ?. let { textView: TextView->
                textView.text = typedArray.getString(R.styleable.More_moreTitleTV) ?: ""
            }

            view.findViewById<ImageView>(R.id.starIV) ?. let {
                starIV = it
                val b: Boolean = typedArray.getBoolean(R.styleable.More_moreStarTV, true)
                if (!b) {
                    starIV!!.visibility = View.GONE
                }
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

    override fun clear() {
        value = ""
        valueTV?.text = ""
    }

    override fun isEmpty(): Boolean {

        return value.isEmpty()
    }

    override fun setOnClickListener(lambda: () -> Unit) {
        valueTV?.setOnClickListener {
            lambda()
        }
    }

    override fun setOnCancelClickListener(lambda: () -> Unit) {
        cancelIV?.setOnClickListener {
            lambda()
        }
    }

    fun setText(text: String) {
        valueTV?.text = text
    }

    override fun setZone(): Boolean {

        if (value.isInt()) {
            val text: String = Zones.zoneIDToName(value.toInt())
            this.setText(text)
            return true
        } else {
            return false
        }
    }

    open fun toMoreDialog(screenWidth: Int, selected: String, delegate: MoreDialogDelegate? = null): MoreDialog {

        val moreDialog = MoreDialog(context, screenWidth, KeyEnum.city_id, selected)
        moreDialog.delegate = delegate
        moreDialog.setContentView(R.layout.select_single)
        moreDialog.init(false, keyEnum.chineseName)
        moreDialog.setAdapter()
        moreDialog.show(30)

        return moreDialog
    }

}