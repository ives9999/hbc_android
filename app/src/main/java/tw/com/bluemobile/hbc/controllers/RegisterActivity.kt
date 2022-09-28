package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.LinearLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.views.EditTextNormal
import tw.com.bluemobile.hbc.utilities.TabEnum

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        able_enum = TabEnum.member

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setTop(true, "註冊")

        findViewById<LinearLayout>(R.id.submit) ?. let {
            it.setOnClickListener {
                submit()
            }
        }
    }

    fun submit() {

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