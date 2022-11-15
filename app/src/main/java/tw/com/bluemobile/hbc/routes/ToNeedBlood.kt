package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.controllers.NeedBloodActivity
import tw.com.bluemobile.hbc.controllers.NeedBloodEditActivity
import tw.com.bluemobile.hbc.controllers.NeedBloodListActivity

interface ToNeedBlood {

    fun toNeedBlood(activity: BaseActivity, token: String? = null) {
        val i = Intent(activity, NeedBloodActivity::class.java)
        if (token != null) {
            i.putExtra("token", token)
        }
        activity.startActivity(i)
    }

    fun toNeedBloodEdit(activity: BaseActivity, token: String? = null) {
        val i = Intent(activity, NeedBloodEditActivity::class.java)
        if (token != null) {
            i.putExtra("token", token)
        }
        activity.startActivity(i)
    }

    fun toNeedBloodList(activity: BaseActivity, source: String="home") {
        val i = Intent(activity, NeedBloodListActivity::class.java)
        i.putExtra("source", source)
        activity.startActivity(i)
    }
}