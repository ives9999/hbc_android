package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.controllers.BloodProcessActivity
import tw.com.bluemobile.hbc.controllers.DonateActivity

interface To {

    fun toBloodProcess(activity: BaseActivity, order_token: String) {
        val i = Intent(activity, BloodProcessActivity::class.java)
        i.putExtra("order_token", order_token)
        activity.startActivity(i)
    }

}