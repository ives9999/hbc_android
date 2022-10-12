package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.member
import tw.com.bluemobile.hbc.models.MemberModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.jsonToModel
import tw.com.bluemobile.hbc.utilities.then
import tw.com.bluemobile.hbc.views.EditTextNormal
import java.lang.Exception

class BankAccountActivity : BaseActivity() {

    var editTextBank: EditTextNormal? = null
    var editTextBranch: EditTextNormal? = null
    var editTextBankCode: EditTextNormal? = null
    var editTextBankAccount: EditTextNormal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_account)

        setTop(true, "銀行帳號")

        init()
    }

    override fun init() {
        super.init()
        loading = Loading(this)

        findViewById<EditTextNormal>(R.id.bank) ?. let {
            editTextBank = it
            it.value = member.bank!!
        }

        findViewById<EditTextNormal>(R.id.branch) ?. let {
            editTextBranch = it
            it.value = member.branch!!
        }

        findViewById<EditTextNormal>(R.id.bank_code) ?. let {
            editTextBankCode = it
            var code: String = member.bank_code!!.toString()
            code = ((code == "0") then { "" }) ?: code
            it.value = code
        }

        findViewById<EditTextNormal>(R.id.bank_account) ?. let {
            editTextBankAccount = it
            it.value = member.bank_account!!
        }

        findViewById<TextView>(R.id.submitTV) ?. let {
            it.setOnClickListener {
                submit()
            }
        }
    }

    fun checkEmpty(): Boolean {

        msg = ""
        if (editTextBank!!.value.isEmpty()) {
            msg += "請填寫銀行名稱\n"
        }

        if (editTextBranch!!.value.isEmpty()) {
            msg += "請填寫分行名稱\n"
        }

        if (editTextBankCode!!.value.isEmpty()) {
            msg += "請填寫銀行代碼\n"
        }

        if (editTextBankAccount!!.value.isEmpty()) {
            msg += "請填寫帳號\n"
        }

        return ((msg.isNotEmpty()) then { true }) ?: false
    }

    override fun submit() {
        if (checkEmpty()) {
            warning(msg)
            return
        }

        val params: MutableMap<String, String> = mutableMapOf(
            "bank" to editTextBank!!.value,
            "branch" to editTextBranch!!.value,
            "bank_code" to editTextBankCode!!.value,
            "bank_account" to editTextBankAccount!!.value
        )

        loading.show()

        MemberService.bank(this, params) { success ->

            if (success) {
                runOnUiThread {
                    try {
                        //println(MemberService.jsonString)
                        val successModel =
                            jsonToModel<SuccessModel<MemberModel>>(MemberService.jsonString)
                        if (successModel != null) {
                            if (successModel.success) {
                                val memberModel = successModel.model
                                memberModel?.toSession(this, true)
                                success("新增/修改 銀行帳號成功") {
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