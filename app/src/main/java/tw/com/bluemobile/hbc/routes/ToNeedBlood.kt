package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.controllers.NeedBloodActivity
import tw.com.bluemobile.hbc.controllers.NeedBloodListActivity

interface ToNeedBlood {

    fun toNeedBlood(activity: BaseActivity) {
        val i = Intent(activity, NeedBloodActivity::class.java)
        activity.startActivity(i)
    }

    fun toNeedBloodList(activity: BaseActivity, isMember: Boolean) {
        val i = Intent(activity, NeedBloodListActivity::class.java)
        i.putExtra("isMember", isMember)
        activity.startActivity(i)
    }
}