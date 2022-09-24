package tw.com.bluemobile.hbc.routes

import android.content.Intent
import androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.controllers.MemberActivity
import tw.com.bluemobile.hbc.controllers.RegisterActivity

interface ToMember {

    fun toMember(activity: BaseActivity) {
        val i = Intent(activity, MemberActivity::class.java)
        activity.startActivity(i)
    }

    fun toRegister(activity: BaseActivity) {
        val i = Intent(activity, RegisterActivity::class.java)
        activity.startActivity(i)
    }
}