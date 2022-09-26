package tw.com.bluemobile.hbc.controllers

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.compones.Top
import tw.com.bluemobile.hbc.routes.*
import tw.com.bluemobile.hbc.utilities.TabEnum
import tw.com.bluemobile.hbc.utilities.then

var able_enum: TabEnum = TabEnum.member //每一組頁面，都有一個專屬的代號的enum
var isPrevIconShow: Boolean = false
lateinit var top: Top

open class BaseActivity : AppCompatActivity(), ToMember, ToNeedBlood {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val top = findViewById<Top>(R.id.top)
    }

    open fun init() {
        //top.showPrev(isPrevIconShow)
    }

    open fun prev() {
        //hideKeyboard()
        val intent = Intent()
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    protected fun setBottomTabFocus() {

        able_enum = TabEnum.enumFromString(able_enum.englishName)

        val tabIconID: Int = able_enum.getIconID(resources, packageName)

        findViewById<ImageView>(tabIconID) ?. let {
            able_enum.setIn(it)
        }

        val allEnum: Array<TabEnum> = TabEnum.getAllEnum()
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

    protected fun setTitle(title: String) {
//        findViewById<TextView>(R.id.title)?.let {
//            it.setText(title)
//        }
    }

    protected fun setTop() {

        val title: String = able_enum.chineseName
        setTitle(title)
    }
}