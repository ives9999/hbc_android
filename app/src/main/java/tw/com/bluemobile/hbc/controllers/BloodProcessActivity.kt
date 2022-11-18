package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.LinearLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.dpToPx
import tw.com.bluemobile.hbc.extensions.pxToDp
import tw.com.bluemobile.hbc.utilities.BloodProcessEnum
import tw.com.bluemobile.hbc.utilities.Global
import tw.com.bluemobile.hbc.views.ProcessCenterNode
import tw.com.bluemobile.hbc.views.ProcessLeftNode

class BloodProcessActivity : BaseActivity() {

    private var need_blood_token: String? = null
    private var donate_member_token: String? = null

    private var processSendMessage: ProcessCenterNode? = null
    private var processArriveHospitalA: ProcessLeftNode? = null

    var process: BloodProcessEnum = BloodProcessEnum.send_message

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_process)

        if (intent.hasExtra("need_blood_token")) {
            need_blood_token = intent.getStringExtra("need_blood_token")
        }

        if (intent.hasExtra("donate_member_token")) {
            donate_member_token = intent.getStringExtra("donate_member_token")
        }

        setTop(true, "需血捐血配對流程")

        findViewById<ProcessCenterNode>(R.id.send_message) ?. let {
            processSendMessage = it
            it.process = process
        }

        findViewById<ProcessLeftNode>(R.id.need_blood_arrive_hospital) ?. let {
            processArriveHospitalA = it
            it.setOnClickListener(n)
        }

//        val screenWidth: Float = Global.getScreenWidth(resources).toFloat()
//        var n: Int = 1
//        n = n.dpToPx()

//        findViewById<LinearLayout>(R.id.n1) ?. let {
//            it.layoutParams.width = ((screenWidth-n)/2).toInt()
//        }

//        findViewById<LinearLayout>(R.id.n2) ?. let {
//            it.layoutParams.width = ((screenWidth-n)/2).toInt()
//        }
    }

    val n: () -> Unit = {
        println("aaa")
    }
}