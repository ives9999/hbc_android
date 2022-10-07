package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonParseException
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.dpToPx
import tw.com.bluemobile.hbc.member
import tw.com.bluemobile.hbc.models.MemberModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.EditTextNormal

class LoginActivity : BaseActivity() {

    private var editTextEmail: EditTextNormal? = null
    private var editTextPassword: EditTextNormal? = null

    private val initData: HashMap<String, String> = hashMapOf(
        EMAIL_KEY to "ives@bluemobile.com.tw",
        PASSWORD_KEY to "1234",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setTop(true, "登入")
        setBottomButtonPadding()

        init()

        findViewById<TextView>(R.id.submitTV) ?. let {
            it.setOnClickListener {
                submit()
            }
        }

        findViewById<TextView>(R.id.registerTV) ?. let {
            it.setOnClickListener {
                toRegister(this)
            }
        }

        findViewById<TextView>(R.id.forget_passwordTV) ?. let {
            it.setOnClickListener {

            }
        }
    }

    override fun init() {

        findViewById<EditTextNormal>(R.id.email) ?. let {
            editTextEmail = it

            if (initData.containsKey(EMAIL_KEY)) {
                it.setValue(initData[EMAIL_KEY]!!)
            }
        }

        findViewById<EditTextNormal>(R.id.password) ?. let {
            editTextPassword = it

            if (initData.containsKey(PASSWORD_KEY)) {
                it.setValue(initData[PASSWORD_KEY]!!)
            }
        }
    }

    fun setBottomButtonPadding() {

        val screenWidth: Int = Global.getScreenWidth(resources)
        val bottom_button_count: Int = 2
        val button_width: Int = 120.dpToPx()

        val padding: Int = (screenWidth - bottom_button_count * button_width) / (bottom_button_count + 1)
        //val leading: Int = bottom_button_count * padding + (bottom_button_count - 1) * button_width

        findViewById<TextView>(R.id.registerTV) ?. let {
            val params: ViewGroup.MarginLayoutParams = it.layoutParams as ViewGroup.MarginLayoutParams
            params.width = button_width
            params.marginStart = padding
            it.layoutParams = params
        }

        findViewById<TextView>(R.id.forget_passwordTV) ?. let {
            val params: ViewGroup.MarginLayoutParams = it.layoutParams as ViewGroup.MarginLayoutParams
            params.width = button_width
            params.marginStart = padding
            it.layoutParams = params
        }
    }

    override fun submit() {

        if (editTextEmail == null || editTextPassword == null) {
            warning("無法取得email與password元件")
            return
        }

        if (editTextEmail!!.getValue().isEmpty()) {
            msg += "請填Email\n"
        }

        if (editTextPassword!!.getValue().isEmpty()) {
            msg += "請填密碼\n"
        }

        if (msg.isNotEmpty()) {
            warning(msg)
            return
        }

        val params: MutableMap<String, String> = hashMapOf()

        params.putAll(hashMapOf(EMAIL_KEY to editTextEmail!!.getValue()))
        params.putAll(hashMapOf(PASSWORD_KEY to editTextPassword!!.getValue()))

        MemberService.login(this, params) { success ->
            if (success) {
                if (MemberService.success) {
                    runOnUiThread {
                        try {
                            //println(MemberService.jsonString)
                            val registerResModel = Gson().fromJson<RegisterResModel>(
                                MemberService.jsonString,
                                RegisterResModel::class.java
                            )
                            if (registerResModel != null) {
                                if (!registerResModel.success) {
                                    msg = ""
                                    for (error in registerResModel.errors) {
                                        msg += error + "\n"
                                    }
                                    warning(msg)
                                } else {
                                    if (registerResModel.model != null) {
                                        val memberModel: MemberModel = registerResModel.model!!
                                        memberModel.filterRow()
                                        memberModel.toSession(this, true)
                                        member.dump()
                                        toMemberHome(this)
                                    }
                                }
                            } else {
                                warning("伺服器回傳錯誤，請稍後再試，或洽管理人員")
                            }
                        } catch (e: JsonParseException) {
                            warning(e.localizedMessage!!)
                        }
                    }
                } else {
                    runOnUiThread {
                        warning(MemberService.msg)
                    }
                }
            } else {
                warning(MemberService.msg)
            }
        }
    }
}