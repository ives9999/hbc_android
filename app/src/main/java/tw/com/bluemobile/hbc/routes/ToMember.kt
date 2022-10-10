package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.*
import tw.com.bluemobile.hbc.utilities.PasswordEnum

interface ToMember {

    fun toLogin(activity: BaseActivity) {
        val i = Intent(activity, LoginActivity::class.java)
        activity.startActivity(i)
    }

    fun toMemberHome(activity: BaseActivity) {
        val i = Intent(activity, MemberActivity::class.java)
        activity.startActivity(i)
    }

    fun toPassword(activity: BaseActivity, type: PasswordEnum) {
        val i = Intent(activity, PasswordActivity::class.java)
        i.putExtra("type", type.englishName)
        activity.startActivity(i)
    }

    fun toRegister(activity: BaseActivity) {
        val i = Intent(activity, RegisterActivity::class.java)
        activity.startActivity(i)
    }
}