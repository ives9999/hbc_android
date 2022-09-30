package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.view.isEmpty
import id.ionbit.ionalert.IonAlert
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.EditTextNormal
import tw.com.bluemobile.hbc.views.More
import tw.com.bluemobile.hbc.views.Action
import tw.com.bluemobile.hbc.views.MoreDialog

class RegisterActivity : BaseActivity() {

    var editTextEmail: EditTextNormal? = null
    var editTextPassword: EditTextNormal? = null
    var editTextRePassword: EditTextNormal? = null
    var editTextName: EditTextNormal? = null
    var editTextNickname: EditTextNormal? = null
    var editTextMobile: EditTextNormal? = null
    var moreCity: More? = null
    var moreArea: More? = null
    var editTextRoad: EditTextNormal? = null

    var moreDialog: MoreDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        able_enum = TabEnum.member

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setTop(true, "註冊")

        findViewById<EditTextNormal>(R.id.email) ?. let {
            editTextEmail = it
        }

        findViewById<EditTextNormal>(R.id.password) ?. let {
            editTextPassword = it
        }

        findViewById<EditTextNormal>(R.id.repassword) ?. let {
            editTextRePassword = it
        }

        findViewById<EditTextNormal>(R.id.name) ?. let {
            editTextName = it
        }

        findViewById<EditTextNormal>(R.id.nickname) ?. let {
            editTextNickname = it
        }

        findViewById<EditTextNormal>(R.id.mobile) ?. let {
            editTextMobile = it
        }

        findViewById<More>(R.id.city_id) ?. let {
            moreCity = it
            it.setOnClickListener() {
                val screenWidth = Global.getScreenWidth(resources)
                moreDialog = it.toMoreDialog(screenWidth, it.value, this)
                //println(moreCity?.value)
            }

            it.setOnCancelClickListener {
                moreCity?.clear()
                moreArea?.clear()
            }
        }

        findViewById<More>(R.id.area_id) ?. let {
            moreArea = it
            it .setOnClickListener() {
                if (moreCity == null || moreCity!!.value.isEmpty()) {
                    warning("請先選擇縣市")
                } else {
                    val screenWidth = Global.getScreenWidth(resources)
                    moreDialog = it.toMoreDialog(screenWidth, moreCity!!.value, this)
                }
            }
        }

        findViewById<EditTextNormal>(R.id.road) ?. let {
            editTextRoad = it
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
        } else if (keyEnum == KeyEnum.area_id) {
            moreArea?.setText(Zones.zoneIDToName(id))
            moreArea?.value = id.toString()
            moreDialog?.hide()
        }
    }

    override fun submit() {

        if (editTextEmail?.getValue()?.isEmpty() == true) {
            msg += "請填郵件\n"
        }

        if (editTextPassword?.getValue()?.isEmpty() == true) {
            msg += "請填密碼\n"
        }

        if (editTextRePassword?.getValue()?.isEmpty() == true) {
            msg += "請填確認密碼\n"
        }

        if (editTextPassword != null && editTextRePassword != null) {
            if (editTextPassword!!.getValue() != editTextRePassword!!.getValue()) {
                msg += "密碼不符合\n"
            }
        }

        if (editTextName?.getValue()?.isEmpty() == true) {
            msg += "請填真實姓名\n"
        }

        if (editTextNickname?.getValue()?.isEmpty() == true) {
            msg += "請填暱稱\n"
        }

        if (moreCity?.value?.isEmpty() == true) {
            msg += "請選擇縣市\n"
        }

        if (moreArea?.value?.isEmpty() == true) {
            msg += "請選擇區域\n"
        }

        if (editTextRoad?.getValue()?.isEmpty() == true) {
            msg += "請填路名\n"
        }

        if (msg.isNotEmpty()) {
            warning(msg)
            return
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