package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import tw.com.bluemobile.hbc.extensions.toEditable
import tw.com.bluemobile.hbc.utilities.BloodProcessEnum

open class ProcessNode @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    protected var nodeLL: LinearLayout? = null
    open var process: BloodProcessEnum? = null

    override fun setOnClickListener(lambda: () -> Unit) {
        nodeLL?.setOnClickListener {
            lambda()
        }
    }
}