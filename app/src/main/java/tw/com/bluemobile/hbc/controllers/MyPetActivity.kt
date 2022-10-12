package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.TextView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.views.EditTextNormal

class MyPetActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_pet)

        setTop(true, "我的寶貝")

        init()
    }

    override fun init() {
        super.init()
        loading = Loading(this)

        findViewById<EditTextNormal>(R.id.code) ?. let {
            //editTextCode = it
        }

        findViewById<TextView>(R.id.submitTV) ?. let {
            it.setOnClickListener {
                submit()
            }
        }
    }
}