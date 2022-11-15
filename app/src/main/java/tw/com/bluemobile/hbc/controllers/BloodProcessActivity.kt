package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import tw.com.bluemobile.hbc.R

class BloodProcessActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_process)

        setTop(true, "捐需血配對流程")
    }
}