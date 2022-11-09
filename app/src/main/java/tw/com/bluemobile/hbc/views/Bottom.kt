package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.extensions.setImage
import tw.com.bluemobile.hbc.member
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
                        TabEnum.donate_blood -> delegate.toDonateBloodList(delegate, "home")
                        TabEnum.member -> {
                            if (member.isLoggedIn) {
                                delegate.toMemberHome(delegate)
                            } else {
                                delegate.toLogin(delegate)
                            }
                        }
                        TabEnum.donate -> delegate.toDonate(delegate)

                        else -> delegate.toMemberHome(delegate)
                    }
                }
            }
        }
    }

    fun setFocus(packageName: String, able_enum: TabEnum) {

        val tabIconID: Int = able_enum.getIconID(resources, packageName)
        val icon: ImageView = view.findViewById<ImageView>(tabIconID)
        icon.setImage(able_enum.englishName + "_in")
    }
}