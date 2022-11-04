package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.*

interface ToDonateBlood {

    fun toDonateBloodEdit(activity: BaseActivity, token: String? = null) {
        val i = Intent(activity, DonateBloodEditActivity::class.java)
        i.putExtra("donateBloodToken", token)
        activity.memberPetEditAR.launch(i)
    }

    fun toDonateBloodList(activity: BaseActivity, source: String="home") {
        val i = Intent(activity, DonateBloodListActivity::class.java)
        i.putExtra("source", source)
        activity.startActivity(i)
    }

    fun toDonateBloodShow(activity: BaseActivity, token: String) {
        val i = Intent(activity, DonateBloodShowActivity::class.java)
        i.putExtra("donateBloodToken", token)
        activity.startActivity(i)
    }
}