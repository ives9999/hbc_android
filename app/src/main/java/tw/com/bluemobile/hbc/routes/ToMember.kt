package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.controllers.MemberActivity

interface ToMember {

    fun toMember(activity: BaseActivity) {
        val i = Intent(activity, MemberActivity::class.java)
        activity.startActivity(i)
    }

}