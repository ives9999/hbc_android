package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.controllers.NeedBloodActivity

interface ToNeedBlood {

    fun toNeedBlood(activity: BaseActivity) {
        val i = Intent(activity, NeedBloodActivity::class.java)
        activity.startActivity(i)
    }
}