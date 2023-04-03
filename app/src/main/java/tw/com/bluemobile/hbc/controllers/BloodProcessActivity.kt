package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.*
import tw.com.bluemobile.hbc.models.*
import tw.com.bluemobile.hbc.services.OrderService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.*
import java.lang.Exception
import java.lang.reflect.Type

class BloodProcessActivity : ShowActivity() {

    private var order_token: String? = null

    //所有node跟enum的array
    private val processNodes: ArrayList<HashMap<BloodProcessEnum, ProcessNode>> = arrayListOf()
    //紀錄node enum是on or off，如果on表示已經執行完畢
    private val nodesOnOff: ArrayList<HashMap<BloodProcessEnum, Boolean>> = arrayListOf()
    //最後一個亮的node enum
    private var theLastOnNodeEnum: BloodProcessEnum = BloodProcessEnum.send_information

    var orderModel: OrderModel? = null

    var creditCardNO: CreditCardNO? = null
    var creditCardMY: CreditCardMY? = null
    var creditCardCVV: CreditCardCVV? = null
    var amountET: EditTextNormal? = null

    var clickEnum: BloodProcessEnum = BloodProcessEnum.send_information

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
                    setNodeOn(enum)
                    setLastOnNode()
                }
            }
            //println(nodesOnOff)
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

    //把所有on的node設定到陣列去
    private fun setNodeOn(enum: BloodProcessEnum) {
        for ((idx, nodeOnOff) in nodesOnOff.withIndex()) {
            for ((nodeEnum, _) in nodeOnOff) {
                if (enum == nodeEnum) {
                    nodesOnOff[idx] = hashMapOf(nodeEnum to true)
                }
            }
        }
    }

    //設定最後一個亮的旗標node的enum
    private fun setLastOnNode() {
        theLastOnNodeEnum = BloodProcessEnum.send_information
        var isFind: Boolean = false
        for (nodeOnOff in nodesOnOff) {
            for ((nodeEnum, onoff) in nodeOnOff) {
                if (onoff) {
                    theLastOnNodeEnum = nodeEnum
                } else {
                    isFind = true
                    break
                }
            }
            if (isFind) {
                break
            }
        }
        //println(theLastOnNode)
    }

    //判斷按下的process是否為下一個，如果不是則跳出警告，但「到達醫院」的兩顆按鈕沒有限制誰先
    private fun isNextCanOnNode(currentNodEnum: BloodProcessEnum): Boolean {
        if (currentNodEnum == BloodProcessEnum.arrive_hospitalA || currentNodEnum == BloodProcessEnum.arrive_hospitalB) {
            if (theLastOnNodeEnum == BloodProcessEnum.send_information || theLastOnNodeEnum == BloodProcessEnum.arrive_hospitalA || theLastOnNodeEnum == BloodProcessEnum.arrive_hospitalB) {
                return true
            }
        } else {
            var lastOnIdx: Int = 0
            var currentNodeIdx: Int = 0
            for ((idx, nodeOnOff) in nodesOnOff.withIndex()) {
                for ((nodeEnum, _) in nodeOnOff) {
                    if (theLastOnNodeEnum == nodeEnum) {
                        lastOnIdx = idx
                    }
                    if (currentNodEnum == nodeEnum) {
                        currentNodeIdx = idx
                    }
                }
            }

            var b: Boolean = false
            if (currentNodeIdx == lastOnIdx + 1) {
                b = true
            }
            return b
        }

        return false
    }

    //按下每一個步驟時，需有以下判斷
    //1.在步驟執行中只有需血方與捐血方可以進入此頁面，此資格判斷在進入時就必須判斷
    //2.已執行過的步驟不能再執行
    //3.步驟需一步一步執行，不能跳躍，但雙方抵達醫院的步驟可以
    //4.需血方與捐血方，只能按下自己邊的步驟
    //5.在需血方付款時，需跳出信用卡輸入對話盒
    private val changeProcess: (BloodProcessEnum) -> Unit = { enum ->
        //println(it)

        clickEnum = enum
        val clickNode = getNodeFromEnum(enum) //取得按下去的節點
        if (clickNode != null && clickNode.isOn) {
            warning("此步驟已經執行過了!!")
        } else {
            if (isNextCanOnNode(enum)) { //如果下一個節點可以按
                if (enum == BloodProcessEnum.traffic_feeA || enum == BloodProcessEnum.traffic_feeB) {
                    showCreditCardLayer()
                } else {
                    updateProcess(enum)
                }
            } else {
                warning("無法跳過步驟執行!!")
            }
        }
    }

    val CVVFocus: ()->Unit = {
        creditCardCVV?.initFocus()
    }

    val MYFocus: ()->Unit = {
        creditCardMY?.initFocus()
    }

    val showWarning: (String)-> Unit = {
        warning(it)
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

                val h1: HashMap<BloodProcessEnum, Boolean> = hashMapOf(enum to false)
                nodesOnOff.add(h1)
            }
        }
    }

    private fun showCreditCardLayer() {
        val view = window.decorView.rootView as ViewGroup
        val maskView: LinearLayout = view.mask(this)

        val blackViewHeight: Int = 1500
        val blackViewPaddingLeft: Int = 20
        val blackView: RelativeLayout = maskView.blackView(this, blackViewPaddingLeft, blackViewHeight)

        val creditCardView = View.inflate(this, R.layout.credit_card, null)
        blackView.addView(creditCardView)
        val params = creditCardView.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = 100
        creditCardView.layoutParams = params

        creditCardView.findViewById<EditTextNormal>(R.id.amount) ?. let {
            amountET = it
        }

        //設定輸完4位數後，往下一個輸入框
        creditCardView.findViewById<CreditCardNO>(R.id.credit_card_no) ?. let {
            creditCardNO = it
            it.myRequestFocus(MYFocus)
        }
        creditCardView.findViewById<CreditCardMY>(R.id.credit_card_my) ?. let {
            creditCardMY = it
            it.setOnChangeListener(showWarning)
            it.myRequestFocus(CVVFocus)
        }
        creditCardView.findViewById<CreditCardCVV>(R.id.credit_card_cvv) ?. let {
            creditCardCVV = it
        }

        val bottomView: LinearLayout = blackView.bottom2ButtonView(this, creditCardSubmit, creditCardCancel)
    }

    private fun updateProcess(process: BloodProcessEnum, amount: String = "", credit_card_no: String = "", credit_card_my: String = "", credit_card_cvv: String = "") {
        val params: MutableMap<String, String> = hashMapOf(
            //order token，只要傳此token代表要更新process
            "token" to orderModel!!.token,
            "abProcess_process" to process.englishName
        )

        if (credit_card_no.isNotEmpty()) {
            params.put("abProcess_credit_card_no", credit_card_no)
            params.put("abProcess_credit_card_my", credit_card_my)
            params.put("abProcess_credit_card_cvv", credit_card_cvv)
        }
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

    val creditCardSubmit: ()->Unit = {
        //println(clickEnum)

        var amount: String = ""
        amountET ?. let {
            amount = it.value
        }

        var credit_card_no: String = ""
        creditCardNO ?. let {
            credit_card_no = it.value
        }

        var credit_card_my: String = ""
        creditCardMY ?. let {
            credit_card_my = it.value
        }

        var credit_card_cvv: String = ""
        creditCardCVV ?. let {
            credit_card_cvv = it.value
        }

        updateProcess(clickEnum, amount, credit_card_no, credit_card_my, credit_card_cvv)

        val view = window.decorView.rootView as ViewGroup
        view.unmask()
    }

    val creditCardCancel: ()->Unit = {
        val view = window.decorView.rootView as ViewGroup
        view.unmask()
    }
}