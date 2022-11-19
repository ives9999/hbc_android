package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.LinearLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.dpToPx
import tw.com.bluemobile.hbc.extensions.pxToDp
import tw.com.bluemobile.hbc.models.DonateBloodModel
import tw.com.bluemobile.hbc.models.OrderModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.DonateBloodService
import tw.com.bluemobile.hbc.services.OrderService
import tw.com.bluemobile.hbc.utilities.BloodProcessEnum
import tw.com.bluemobile.hbc.utilities.Global
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.genericType
import tw.com.bluemobile.hbc.views.ProcessCenterNode
import tw.com.bluemobile.hbc.views.ProcessLeftNode
import java.lang.reflect.Type

class BloodProcessActivity : ShowActivity() {

    private var order_token: String? = null

    private var processSendMessage: ProcessCenterNode? = null
    private var processArriveHospitalA: ProcessLeftNode? = null

    private var process: BloodProcessEnum = BloodProcessEnum.send_message
    var orderModel: OrderModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_process)

        if (intent.hasExtra("order_token")) {
            order_token = intent.getStringExtra("order_token")
        }

        setTop(true, "需血捐血配對流程")
        loading = Loading(this)

        findViewById<ProcessCenterNode>(R.id.send_message) ?. let {
            processSendMessage = it
            it.process = process
        }

        findViewById<ProcessLeftNode>(R.id.need_blood_arrive_hospital) ?. let {
            processArriveHospitalA = it
            it.setOnClickListener(n)
        }
    }

    val n: () -> Unit = {
        println("aaa")
    }

    override fun refresh() {

        super.refresh()
        val params: HashMap<String, String> = hashMapOf("token" to order_token!!)
        loading.show()
        OrderService.getOne(this, params) { success ->
            runOnUiThread {
                loading.hide()
            }
            if (success) {
                //println(DonateBloodService.jsonString)
                val modelType: Type = genericType<SuccessModel<OrderModel>>()
                orderModel = parseJSONAndInit<OrderModel>(DonateBloodService.jsonString, modelType)
                runOnUiThread {
                    init()
                }
            }
        }
    }
}