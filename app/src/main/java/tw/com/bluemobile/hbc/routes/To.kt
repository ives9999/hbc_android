package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.controllers.BloodProcessActivity
import tw.com.bluemobile.hbc.controllers.DonateActivity

interface To {

    fun toBloodProcess(activity: BaseActivity) {
        val i = Intent(activity, BloodProcessActivity::class.java)
        activity.startActivity(i)
    }

}