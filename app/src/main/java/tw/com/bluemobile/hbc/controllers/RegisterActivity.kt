package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.utilities.TabEnum

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        able_enum = TabEnum.member

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setTop()
        setTitle("註冊")

        init()
    }

    override fun init() {
        isPrevIconShow = true
        super.init()
    }
}