package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import tw.com.bluemobile.hbc.R

class BloodProcessActivity : BaseActivity() {

    var need_blood_token: String? = null
    var donate_member_token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_process)

        if (intent.hasExtra("need_blood_token")) {
            need_blood_token = intent.getStringExtra("need_blood_token")
        }

        if (intent.hasExtra("donate_member_token")) {
            donate_member_token = intent.getStringExtra("donate_member_token")
        }

        setTop(true, "捐需血配對流程")
    }
}