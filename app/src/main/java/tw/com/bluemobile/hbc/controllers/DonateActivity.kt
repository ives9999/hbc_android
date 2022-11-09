package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.utilities.TabEnum

class DonateActivity : EditActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        able_enum = TabEnum.donate
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        setTop(false, "我要捐款")
        setBottom(able_enum)
    }
}