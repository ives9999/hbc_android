package tw.com.bluemobile.hbc.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import tw.com.bluemobile.hbc.utilities.TabEnum

var able_type: String = "member"//每一組頁面，都有一個專屬的代號，就稱為able_type
var able_enum: TabEnum = TabEnum.member //每一組頁面，都有一個專屬的代號的enum

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setBottomTabFocus() {

        able_enum = TabEnum.enumFromString(able_type)

        val tabIconID: Int = able_enum.getIconID(resources, packageName)

        findViewById<ImageView>(tabIconID) ?. let {
            able_enum.setIn(it)
        }
    }
}