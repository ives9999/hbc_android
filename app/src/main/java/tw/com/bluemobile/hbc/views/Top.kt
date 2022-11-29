package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.utilities.then

class Top @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    RelativeLayout(context, attrs, defStyleAttr) {

    val view = View.inflate(context, R.layout.top, this)
    private var prevIB: ImageButton = view.findViewById(R.id.prevIB)
    private var addIB: ImageButton = view.findViewById(R.id.addIB)
    private var editIB: ImageButton = view.findViewById(R.id.editIB)
    private var titleTV: TextView = view.findViewById(R.id.titleTV)

    init {
        (context as? BaseActivity) ?. let { delegate->
            prevIB.setOnClickListener {
                delegate.prev()
            }

            addIB.setOnClickListener {
                delegate.add()
            }

            editIB.setOnClickListener {
                delegate.edit()
            }
        }
    }

    fun showAdd(isShow: Boolean = false) {
        addIB.visibility = (isShow then { VISIBLE }) ?: GONE
    }

    fun showEdit(isShow: Boolean = false) {
        editIB.visibility = (isShow then { VISIBLE }) ?: GONE
    }

    fun showPrev(isShow: Boolean = true) {
        prevIB.visibility = (isShow then { VISIBLE }) ?: GONE
    }

    fun setTitle(title: String) {
        titleTV.text = title
    }
}