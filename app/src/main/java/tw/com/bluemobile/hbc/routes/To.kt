package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.controllers.SelectCity

interface To {

    fun toSelectCity(activity: BaseActivity) {
        val i = Intent(activity, SelectCity::class.java)
        activity.startActivity(i)
    }
}