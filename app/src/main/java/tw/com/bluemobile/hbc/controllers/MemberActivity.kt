package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.utilities.TabEnum

class MemberActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        able_type = TabEnum.member.englishName
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)
    }
}