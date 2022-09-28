package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.view.isEmpty
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.EditTextNormal
import tw.com.bluemobile.hbc.views.More
import tw.com.bluemobile.hbc.views.Action
import tw.com.bluemobile.hbc.views.MoreDialog

class RegisterActivity : BaseActivity() {

    var moreCity: More? = null
    var moreArea: More? = null
    var moreDialog: MoreDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        able_enum = TabEnum.member

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setTop(true, "註冊")

        findViewById<More>(R.id.city_id) ?. let {
            moreCity = it
            it.setOnClickListener() {
                val screenWidth = Global.getScreenWidth(resources)
                moreDialog = it.toMoreDialog(screenWidth, it.value, this)
                //println(moreCity?.value)
            }
        }

        findViewById<More>(R.id.area_id) ?. let {
            moreArea = it
            it .setOnClickListener() {
                if (moreCity == null || moreCity!!.value.isEmpty()) {
                    println("aaa")
                }
            }
        }

        findViewById<More>(R.id.area_id) ?. let {
            moreArea = it
        }

        findViewById<LinearLayout>(R.id.submit) ?. let {
            it.setOnClickListener {
                submit()
            }
        }
    }

    override fun cellClick(keyEnum: KeyEnum, id: Int) {
//        println(key)
//        println(id)
        if (keyEnum == KeyEnum.city_id) {
            moreCity?.setText(Zones.zoneIDToName(id))
            moreCity?.value = id.toString()
            moreDialog?.hide()
        }
    }

    override fun submit() {

        if (isEmpty(R.id.email)) {
            msg += "請填郵件\n"
        }


        if (msg.isNotEmpty()) {
            println(msg)
        }
    }

    private fun isEmpty(resource: Int): Boolean {

        var b: Boolean = true
        findViewById<EditTextNormal>(resource) ?. let { editTextNormal->
            if (!editTextNormal.isEmpty()) {
                b = false
            }
        }

        return b
    }
}