package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.controllers.DonateActivity

interface ToDonate {

    fun toDonate(activity: BaseActivity) {
        val i = Intent(activity, DonateActivity::class.java)
        activity.startActivity(i)
    }
}