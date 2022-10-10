package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.models.MemberModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.PasswordEnum
import tw.com.bluemobile.hbc.utilities.ValidateEnum
import tw.com.bluemobile.hbc.utilities.jsonToModel
import tw.com.bluemobile.hbc.views.EditTextNormal
import java.lang.Exception

class ValidateActivity : BaseActivity() {

    var validateEnum: ValidateEnum? = null
    var editTextCode: EditTextNormal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validate)

        validateEnum = if (intent.hasExtra("type")) {
            val tmp: String = intent.getStringExtra("type") ?: "none"
            ValidateEnum.enumFromString(tmp)
        } else {
            ValidateEnum.none
        }

        if (validateEnum == ValidateEnum.none) {
            warning("沒有傳輸密碼類型，請洽管理員") {
                prev()
            }
        }

        setTop(true, validateEnum!!.chineseName)

        init()
    }

    override fun init() {
        super.init()
        loading = Loading(this)

        findViewById<EditTextNormal>(R.id.code) ?. let {
            editTextCode = it
        }

        findViewById<TextView>(R.id.submitTV) ?. let {
            it.setOnClickListener {
                submit()
            }
        }
    }

    fun checkEmpty(): Boolean {
        if (editTextCode!!.getValue().isEmpty()) {
            warning("請填寫認證碼")
            return false
        }

        return true
    }

    override fun submit() {
        if (!checkEmpty()) {
            return
        }

        loading.show()

        MemberService.validate(this, validateEnum!!, editTextCode!!.getValue()) { success ->

            if (success) {
                runOnUiThread {
                    try {
                        //println(MemberService.jsonString)
                        val successModel =
                            jsonToModel<SuccessModel<MemberModel>>(MemberService.jsonString)
                        if (successModel != null) {
                            if (successModel.success) {
                                success("認證成功") {
                                    prev()
                                }
                            } else {
                                warning(successModel.msg)
                            }
                        } else {
                            warning("app無法解析系統傳回值，請洽管理員")
                        }
                    } catch (e: Exception) {
                        warning(e.localizedMessage)
                    }
                }
            } else {
                runOnUiThread {
                    warning("無法取得伺服器的回應，請洽管理員")
                }
            }
            loading.hide()
        }
    }
}