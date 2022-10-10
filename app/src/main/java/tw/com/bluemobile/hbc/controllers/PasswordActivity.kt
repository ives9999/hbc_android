package tw.com.bluemobile.hbc.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.TextView
import com.example.awesomedialog.*
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.models.MemberModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.EMAIL_KEY
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.PasswordEnum
import tw.com.bluemobile.hbc.utilities.jsonToModel
import tw.com.bluemobile.hbc.views.EditTextNormal
import java.lang.Exception

class PasswordActivity : BaseActivity() {

    var passwordEnum: PasswordEnum? = null

    var editTextPassword: EditTextNormal? = null
    var editTextRePassword: EditTextNormal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        passwordEnum = if (intent.hasExtra("type")) {
            val tmp: String = intent.getStringExtra("type") ?: "none"
            PasswordEnum.enumFromString(tmp)
        } else {
            PasswordEnum.none
        }

        if (passwordEnum == PasswordEnum.none) {
            warning("沒有傳輸密碼類型，請洽管理員") {
                prev()
            }
        }

        setTop(true, passwordEnum!!.chineseName)

        init()
    }

    override fun init() {
        super.init()
        loading = Loading(this)

        findViewById<EditTextNormal>(R.id.password) ?. let {
            editTextPassword = it
        }

        findViewById<EditTextNormal>(R.id.repassword) ?. let {
            editTextRePassword = it
        }

        if (passwordEnum == PasswordEnum.forget) {
            editTextRePassword?.visibility = View.GONE
            editTextPassword?.setTitle("Email")
        } else {
            editTextPassword?.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            editTextRePassword?.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        }

        findViewById<TextView>(R.id.submitTV) ?. let {
            it.setOnClickListener {
                submit()
            }
        }
    }

    fun checkEmpty(): Boolean {
        if (passwordEnum == PasswordEnum.forget) {
            if (editTextPassword!!.getValue().isEmpty()) {
                warning("請填寫Email")
                return false
            }
        } else {
            msg = ""
            if (editTextPassword!!.getValue().isEmpty()) {
                msg += "密碼不能為空白\n"
            }

            if (editTextRePassword!!.getValue().isEmpty()) {
                msg += "確認密碼不能為空白\n"
            }

            if (editTextPassword!!.getValue() != editTextRePassword!!.getValue()) {
                msg += "密碼不符合\n"
            }

            if (msg.isNotEmpty()) {
                warning(msg)
                return false
            }
        }

        return true
    }

    override fun submit() {
        if (!checkEmpty()) {
            return
        }

        loading.show()

        if (passwordEnum == PasswordEnum.forget) {
            MemberService.forgetPassword(this, editTextPassword!!.getValue()) { success ->

                if (success) {
                    runOnUiThread {
                        try {
                            //println(MemberService.jsonString)
                            val successModel =
                                jsonToModel<SuccessModel<MemberModel>>(MemberService.jsonString)
                            if (successModel != null) {
                                if (successModel.success) {
                                    info("系統將會寄送一封更新密碼的郵件至您註冊的信箱，請用該網址來重新設定密碼")
                                } else {
                                    warning("糸統傳回值錯誤，請洽管理員")
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
        } else {
            MemberService.resetPassword(this, editTextPassword!!.getValue()) { success ->

                if (success) {
                    runOnUiThread {
                        try {
                            //println(MemberService.jsonString)
                            val successModel =
                                jsonToModel<SuccessModel<MemberModel>>(MemberService.jsonString)
                            if (successModel != null) {
                                if (successModel.success) {
                                    success("更新密碼成功，請用新密碼登入") {
                                        toLogin(this)
                                    }
                                } else {
                                    warning("糸統傳回值錯誤，請洽管理員")
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
}