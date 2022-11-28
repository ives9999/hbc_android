package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.*

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

    fun toNeedBloodShow(activity: BaseActivity, token: String) {
        val i = Intent(activity, NeedBloodShowActivity::class.java)
        i.putExtra("needBloodToken", token)
        activity.startActivity(i)
    }
}