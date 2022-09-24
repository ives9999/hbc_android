package tw.com.bluemobile.hbc.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.routes.*
import tw.com.bluemobile.hbc.utilities.TabEnum

var able_enum: TabEnum = TabEnum.member //每一組頁面，都有一個專屬的代號的enum


open class BaseActivity : AppCompatActivity(), ToMember, ToNeedBlood {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setBottomTabFocus() {

        able_enum = TabEnum.enumFromString(able_enum.englishName)

        val tabIconID: Int = able_enum.getIconID(resources, packageName)

        findViewById<ImageView>(tabIconID) ?. let {
            able_enum.setIn(it)
        }

        val allEnum: Array<TabEnum> = able_enum.getAllEnum()
        for (enum in allEnum) {
            val containerIDString: String = enum.englishName + "TabContainer"
            val containerID: Int = resources.getIdentifier(containerIDString, "id", packageName)
            findViewById<LinearLayout>(containerID) ?. let {
                it.setOnClickListener {
                    when (enum) {
                        TabEnum.need_blood -> toNeedBlood(this)
                        TabEnum.member -> toMember(this)

                        else -> toMember(this)
                    }
                }
            }
        }
    }

    protected fun setTop() {

        val title: String = able_enum.chineseName
        findViewById<TextView>(R.id.title)?.let {
            it.setText(title)
        }
    }
}