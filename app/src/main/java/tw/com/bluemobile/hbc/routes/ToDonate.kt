package tw.com.bluemobile.hbc.routes

import android.content.Intent
import android.net.Uri
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.controllers.DonateActivity

interface ToDonate {

    fun toDonate(activity: BaseActivity) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://donate.newebpay.com/catpool/20190414"))
        //val i = Intent(activity, DonateActivity::class.java)
        activity.startActivity(i)
    }
}