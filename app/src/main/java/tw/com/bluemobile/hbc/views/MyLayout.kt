package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

open class MyLayout(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0): LinearLayout(context, attrs, defStyleAttr) {

    @JvmField var key: String = ""
    @JvmField var value: String = ""

    var titleTV: TextView? = null

    open fun clear() {}

    open fun getKey(): String {
        return key
    }

    open fun getValue(): String {
        return value
    }

    open fun isEmpty(): Boolean { return false }

    open fun setKey(key: String) {
        this.key = key
    }

    open fun setOnCancelClickListener(lambda: () -> Unit) {
        lambda()
    }

    open fun setOnClickListener(lambda: () -> Unit) {
        lambda()
    }

    open fun setTitle(title: String) {
        titleTV?.text = title
    }

    open fun setValue(value: String) {
        this.value = value
    }

    open fun setZone(): Boolean { return true }
}