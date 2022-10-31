package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.controllers.DonateBloodListActivity
import tw.com.bluemobile.hbc.controllers.NeedBloodActivity

interface ToDonateBlood {

    fun toDonateBloodList(activity: BaseActivity) {
        val i = Intent(activity, DonateBloodListActivity::class.java)
        activity.startActivity(i)
    }
}