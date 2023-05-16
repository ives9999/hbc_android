package tw.com.bluemobile.hbc.controllers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.BaseAdapter
import tw.com.bluemobile.hbc.adapters.BaseViewHolder
import tw.com.bluemobile.hbc.databinding.ActivityMainBinding
import tw.com.bluemobile.hbc.extensions.parseErrmsg
import tw.com.bluemobile.hbc.member
import tw.com.bluemobile.hbc.models.*
import tw.com.bluemobile.hbc.services.DonateBloodService
import tw.com.bluemobile.hbc.services.OrderService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.*
import java.lang.Exception
import java.lang.reflect.Type

class NeedBloodActivity : ListActivity<DonateBloodListViewHolder, DonateBloodModel>() {

    private lateinit var binding: ActivityMainBinding

    var moreCity: More? = null
    var moreArea: More? = null
    var typeRadio: TwoRadio? = null
    var catBloodTypeRadio: ThreeRadio? = null
    var dogBloodTypeRadio: TwoRadio? = null

    var moreDialog: MoreDialog<SelectSingleViewHolder>? = null
    var token: String? = null
    var needBloodModel: NeedBloodModel? = null
    var source: String = "home"

    private val formItems: ArrayList<HashMap<NeedBloodEnum, MyLayout>> = arrayListOf()

    private val initData: MutableMap<String, String> = mutableMapOf(
//        NeedBloodEnum.hospital_name.englishName to "忍者貓侍",
//        NeedBloodEnum.hospital_city_id.englishName to "218",
//        NeedBloodEnum.hospital_area_id.englishName to "219",
//        NeedBloodEnum.hospital_road.englishName to "南華街101號8樓",
//        NeedBloodEnum.traffic_fee.englishName to "100",
//        NeedBloodEnum.nutrient_fee.englishName to "200",
//
//        NeedBloodEnum.type.englishName to "貓",
//        NeedBloodEnum.blood_type_cat.englishName to "B"
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        able_enum = TabEnum.need_blood
        super.onCreate(savedInstanceState)

        if (intent.hasExtra("token")) {
            token = intent.getStringExtra("token")!!
        }

        if (token == null) {
//        Thread.sleep(1000)
            val splashScreen = installSplashScreen()
        }

        if (intent.hasExtra("source")) {
            source = intent.getStringExtra("source")!!
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        //setContentView(binding.root)

        setTop(false, "我需要血")
        top?.showAdd(true)

        if (source == "home") {
            setBottom(able_enum)
        } else {
            findViewById<Bottom>(R.id.bottom) ?. let {
                it.visibility = View.GONE
            }
            top?.showPrev(true)
            top?.setTitle("我需要血")
        }
        loading = Loading(this)

        init()
    }

    override fun init() {
        adapter = DonateBloodListAdapter(R.layout.list_donate_blood, ::DonateBloodListViewHolder, source, onAcceptClick)
        super.init()

        refresh()
    }

    override fun getList(page: Int, perPage: Int) {

        loading.show()

        val params: MutableMap<String, String> = hashMapOf()
        if (source == "member") {
            params.putAll(hashMapOf("member_token" to member.token!!))
        }

        params.putAll(hashMapOf("status" to "online,process"))

        DonateBloodService.getList(this, params, page, perPage) { success ->
            runOnUiThread {
                //println(DonateBloodService.jsonString)

                val modelType: Type = genericType<BaseModels<DonateBloodModel>>()
                parseJSON(DonateBloodService.jsonString, modelType)
                loading.hide()
            }
        }
    }

    override fun add() {
        super.add()
        toNeedBloodEdit(this)
    }

    override val onRowClick: ((Int) -> Unit) = { idx ->
        val row: DonateBloodModel = rows[idx]
        toDonateBloodShow(this, row.token)
    }

    override val onEditClick: ((Int) -> Unit) = { idx ->
        val row: DonateBloodModel = rows[idx]
        toDonateBloodEdit(this, row.token)
    }

    override val onDeleteClick: ((Int) -> Unit) = { idx ->
        val row: DonateBloodModel = rows[idx]
        warning("是否確定要刪除?", "刪除") {
            delete(row.token)
        }
    }

    private fun delete(token: String) {
        loading.show()
        DonateBloodService.postDelete(this, token) {
            runOnUiThread {
                //println(MemberService.jsonString)

                loading.hide()
                success("刪除完成") {
                    refresh()
                }
            }
        }
    }

    private val onAcceptClick: ((Int) -> Unit) = { idx ->
        if (!member.isLoggedIn) {
            warning("請先登入！！", "登入") {
                toLogin(this)
            }
        } else {
            val row: DonateBloodModel = rows[idx]
            if (row.status == "online") {
                info("是否確定要接受此寵物的捐血", "確定") {
                    insertBloodProcess(idx)
                }
            } else if (row.status == "process") {
                if (member.token != row.memberA_token && member.token != row.memberB_token) {
                    warning("您不是該捐需血的主人，無法檢視進行中的流程!!")
                } else {
                    toBloodProcess(this, row.order_token)
                }
            }
        }
    }

    private fun insertBloodProcess(idx: Int) {
        val row: DonateBloodModel = rows[idx]
        val params: HashMap<String, String> = hashMapOf(
            //捐血記錄的token
            "abProcess_donate_blood_token" to row.token,
            //需血方的會員token
            "abProcess_memberA_token" to member.token!!,
            //捐血方的會員token
            "abProcess_memberB_token" to row.member_token,
            //產品類型，目前一律為"blood"
            "product_type" to "blood"
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
                                val orderModel: OrderModel = successModel.model!!
                                success("已經建立此筆流程，是否前往後續流程服務頁面？", "是") {
                                    toBloodProcess(this, orderModel.token)
                                }
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

class DonateBloodListAdapter(
    override val resource: Int,
    override val viewHolderConstructor: viewHolder<DonateBloodListViewHolder>,
    private val source: String,
    private val onAcceptClick: ((Int) -> Unit)?):
    BaseAdapter<DonateBloodListViewHolder, DonateBloodModel>(resource, viewHolderConstructor) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonateBloodListViewHolder {

        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(resource, parent, false)
        val viewHolder: DonateBloodListViewHolder = viewHolderConstructor(parent.context, view)
        viewHolder.source = source

        return viewHolder
    }

    override fun onBindViewHolder(holder: DonateBloodListViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.setOnAcceptClickListener(position, onAcceptClick)
    }
}

class DonateBloodListViewHolder(
    context: Context,
    view: View,
): BaseViewHolder<DonateBloodModel>(context, view) {

    var source: String = ""

    override fun bind(row: DonateBloodModel, idx: Int) {

        row.filterRow()
        super.bind(row, idx)

        if (source == "home") {
            view.findViewById<View>(R.id.line)?.let {
                it.visibility = View.GONE
            }
            view.findViewById<LinearLayout>(R.id.iconContainer)?.let {
                it.visibility = View.GONE
            }
        } else {
            view.findViewById<LinearLayout>(R.id.acceptContainer) ?. let {
                it.visibility = View.GONE
            }
        }

        setIV(R.id.typeIV, "ic_${row.type}")

        val typeEnum: NeedBloodEnum = NeedBloodEnum.enumFromString(row.type)
        setTV(R.id.typeTV, typeEnum.DBNameToRadioText(row.type))

        setTV(R.id.nameTV, row.name)
        setTV(R.id.blood_typeTV, row.blood_type)
        setTV(R.id.ageTV, row.age.toString())
        setTV(R.id.weightTV, row.weight.toString())
        setTV(R.id.updated_at, row.updated_at_show)
        setTV(R.id.traffic_feeTV, row.traffic_fee.toString())
        setTV(R.id.nutrient_feeTV, row.nutrient_fee.toString())

        if (row.status == "process") {
            view.findViewById<LinearLayout>(R.id.acceptLL) ?. let {
                it.background = ContextCompat.getDrawable(context, R.drawable.circle_blue)
            }

            view.findViewById<TextView>(R.id.acceptTV)?.let {
                it.text = "進行中..."
                val size: Int = 12
                it.textSize = size.toFloat()
                it.setTextColor(getColor(context, R.color.MY_WHITE))
            }
        } else if (row.status == "online") {
            view.findViewById<LinearLayout>(R.id.acceptLL) ?. let {
                it.background = ContextCompat.getDrawable(context, R.drawable.circle)
            }

            view.findViewById<TextView>(R.id.acceptTV)?.let {
                it.text = "接受"
                val size: Int = 12
                it.textSize = size.toFloat()
                it.setTextColor(getColor(context, R.color.MY_BLACK))
            }
        }
    }

    fun setOnAcceptClickListener(idx: Int, onAcceptClick: ((Int) -> Unit)?) {
        view.findViewById<LinearLayout>(R.id.acceptContainer) ?. let {
            it.setOnClickListener {
                onAcceptClick?.invoke(idx)
            }
        }
    }
}