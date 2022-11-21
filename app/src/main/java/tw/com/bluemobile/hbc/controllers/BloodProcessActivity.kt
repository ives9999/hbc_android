package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.parseErrmsg
import tw.com.bluemobile.hbc.models.*
import tw.com.bluemobile.hbc.services.OrderService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.*
import java.lang.Exception
import java.lang.reflect.Type

class BloodProcessActivity : ShowActivity() {

    private var order_token: String? = null

    private val processNodes: ArrayList<HashMap<BloodProcessEnum, ProcessNode>> = arrayListOf()

//    private var processSendMessage: ProcessCenterNode? = null
//    private var processArriveHospitalA: ProcessLeftNode? = null
//    private var processArriveHospitalB: ProcessRightNode? = null
//    private var processMeet: ProcessCenterNode? = null
//    private var processTrafficFeeA: ProcessLeftNode? = null
//    private var processTrafficFeeP: ProcessCenterNode? = null
//    private var processTrafficFeeB: ProcessRightNode? = null
//    private var processNutrientFeeA: ProcessLeftNode? = null
//    private var processNutrientFeeP: ProcessCenterNode? = null
//    private var processNutrientFeeB: ProcessRightNode? = null
//    private var processPair: ProcessCenterNode? = null
//    private var processComplete: ProcessCenterNode? = null

//    private var allProcess: BloodProcessEnum = BloodProcessEnum.send_information
    var orderModel: OrderModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_process)

        if (intent.hasExtra("order_token")) {
            order_token = intent.getStringExtra("order_token")
        }

        setTop(true, "需血捐血配對流程")
        loading = Loading(this)

        setAllNodes()
        refresh()
    }

    override fun init() {
        super.init()

        if (orderModel != null && orderModel!!.abProcessModel != null) {
            val abProcessModel: ABProcessModel = orderModel!!.abProcessModel!!
            //abProcessModel.dump()

            val enums: ArrayList<BloodProcessEnum> = BloodProcessEnum.getAllEnum()
            for (enum in enums) {
                val show: String = getPropertyValue(abProcessModel, enum.englishName + "_at_show")
                if (show.isNotEmpty()) {
                    getNodeFromEnum(enum)?.setOpen(show)
                }
            }
        }
    }

    private fun getNodeFromEnum(enum: BloodProcessEnum): ProcessNode? {
        for (processNode in processNodes) {
            for ((nodeEnum, node) in processNode) {
                if (enum == nodeEnum) {
                    return node
                }
            }
        }

        return null
    }

    val changeProcess: (BloodProcessEnum) -> Unit = {
        //println(it)
        updateProcess(it)
    }

    override fun refresh() {

        super.refresh()
        val params: HashMap<String, String> = hashMapOf("token" to order_token!!)
        loading.show()
        OrderService.getOne(this, params) { success ->
            if (success) {
//                println(OrderService.jsonString)
                val modelType: Type = genericType<SuccessModel<OrderModel>>()
                orderModel = parseJSONAndInit<OrderModel>(OrderService.jsonString, modelType)
            }
        }
    }

    private fun setAllNodes() {

        val enums: ArrayList<BloodProcessEnum> = BloodProcessEnum.getAllEnum()
        for (enum in enums) {
            val key: String = enum.englishName
            val r: Int = resources.getIdentifier(key, "id", packageName)
            findViewById<ProcessNode>(r) ?. let {
                val h: HashMap<BloodProcessEnum, ProcessNode> = hashMapOf(enum to it)
                processNodes.add(h)
                it.process = enum
                it.setOnClickListener(changeProcess)
            }
        }
    }

    private fun updateProcess(process: BloodProcessEnum) {
        val params: HashMap<String, String> = hashMapOf(
            "token" to orderModel!!.token,
            "abProcess_process" to process.englishName
        )
        //println(params);

        loading.show()
        OrderService.update(this, params) { success ->
            runOnUiThread {
                loading.hide()
            }
            if (success) {
                runOnUiThread {
                    try {
                        //println(DonateBloodService.jsonString)
                        val successModel =
                            jsonToModel<SuccessModel<OrderModel>>(OrderService.jsonString)
                        if (successModel != null) {
                            if (successModel.success) {
                                orderModel = successModel.model
                                orderModel?.filterRow()
                                init()
                            } else {
                                warning(successModel.msgs.parseErrmsg())
                            }
                        } else {
                            warning("app無法解析系統傳回值，請洽管理員")
                        }
                    } catch (e: Exception) {
                        warning(e.localizedMessage)
                    }
                }
            }
        }
    }
}