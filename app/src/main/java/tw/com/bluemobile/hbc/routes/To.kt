package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.controllers.BloodProcessActivity
import tw.com.bluemobile.hbc.controllers.DonateActivity

interface To {

    fun toBloodProcess(activity: BaseActivity, need_blood_token: String, donate_member_token: String) {
        val i = Intent(activity, BloodProcessActivity::class.java)
        i.putExtra("need_blood_token", need_blood_token)
        i.putExtra("donate_member_token", donate_member_token)
        activity.startActivity(i)
    }

}