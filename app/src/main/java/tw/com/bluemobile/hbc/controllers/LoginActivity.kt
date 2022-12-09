package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonParseException
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.dpToPx
import tw.com.bluemobile.hbc.extensions.parseErrmsg
import tw.com.bluemobile.hbc.member
import tw.com.bluemobile.hbc.models.MemberModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.EditTextNormal

class LoginActivity : BaseActivity() {

    private var editTextEmail: EditTextNormal? = null
    private var editTextPassword: EditTextNormal? = null

    private val initData: HashMap<String, String> = hashMapOf(
//        EMAIL_KEY to "ives@bluemobile.com.tw",
//        PASSWORD_KEY to "1234",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setTop(true, "登入")
        setBottomButtonPadding()

        init()
    }

    override fun init() {
        super.init()
        loading = Loading(this)

        findViewById<EditTextNormal>(R.id.email) ?. let {
            editTextEmail = it

            if (initData.containsKey(EMAIL_KEY)) {
                it.value = initData[EMAIL_KEY]!!
            }
        }

        findViewById<EditTextNormal>(R.id.password) ?. let {
            editTextPassword = it

            if (initData.containsKey(PASSWORD_KEY)) {
                it.value = initData[PASSWORD_KEY]!!
            }
        }

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
                toPassword(this, PasswordEnum.forget)
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

        if (editTextEmail!!.value.isEmpty()) {
            msg += "請填Email\n"
        }

        if (editTextPassword!!.value.isEmpty()) {
            msg += "請填密碼\n"
        }

        if (msg.isNotEmpty()) {
            warning(msg)
            return
        }

        loading.show()

        val params: MutableMap<String, String> = hashMapOf()

        params.putAll(hashMapOf(EMAIL_KEY to editTextEmail!!.value))
        params.putAll(hashMapOf(PASSWORD_KEY to editTextPassword!!.value))

        MemberService.login(this, params) { success ->
            loading.hide()
            if (success) {
                runOnUiThread {
                    try {
                        //println(MemberService.jsonString)
                        val successModel = jsonToModel<SuccessModel<MemberModel>>(MemberService.jsonString)
                        if (successModel != null) {
                            if (!successModel.success) {
                                warning(successModel.msgs.parseErrmsg())
                            } else {
                                val memberModel = successModel.model
                                memberModel?.filterRow()
                                //memberModel?.dump()
                                memberModel?.toSession(this, true)
                                //member.dump()
                                toMemberHome(this)
                            }
                        }
                    } catch (e: JsonParseException) {
                        warning(e.localizedMessage!!)
                    }
                }
            } else {
                warning(MemberService.msg)
            }
        }
    }
}