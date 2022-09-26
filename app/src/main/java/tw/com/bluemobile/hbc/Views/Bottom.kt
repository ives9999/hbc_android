package tw.com.bluemobile.hbc.Views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.controllers.able_enum
import tw.com.bluemobile.hbc.extensions.setImage
import tw.com.bluemobile.hbc.utilities.TabEnum

class Bottom @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    LinearLayout(context, attrs, defStyleAttr) {

    val view = View.inflate(context, R.layout.bottom, this)
    init {
        (context as? BaseActivity) ?. let { delegate->
            onClickListener(delegate)
        }
    }


    private fun onClickListener(delegate: BaseActivity) {
        val allEnum: Array<TabEnum> = TabEnum.getAllEnum()
        for (enum in allEnum) {
            val containerIDString: String = enum.englishName + "TabContainer"
            val containerID: Int = resources.getIdentifier(containerIDString, "id", delegate.packageName)
            findViewById<LinearLayout>(containerID) ?. let {
                it.setOnClickListener {
                    when (enum) {
                        TabEnum.need_blood -> delegate.toNeedBlood(delegate)
                        TabEnum.member -> delegate.toMember(delegate)

                        else -> delegate.toMember(delegate)
                    }
                }
            }
        }

    }

    fun setFocus(packageName: String) {

        able_enum = TabEnum.enumFromString(able_enum.englishName)
        val tabIconID: Int = able_enum.getIconID(resources, packageName)
        val icon: ImageView = view.findViewById<ImageView>(tabIconID)
        icon.setImage(able_enum.englishName + "_in")
    }
}