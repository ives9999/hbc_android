package tw.com.bluemobile.hbc.controllers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.BaseAdapter
import tw.com.bluemobile.hbc.adapters.BaseViewHolder
import tw.com.bluemobile.hbc.adapters.viewHolder
import tw.com.bluemobile.hbc.extensions.parseErrmsg
import tw.com.bluemobile.hbc.member
import tw.com.bluemobile.hbc.models.*
import tw.com.bluemobile.hbc.services.DonateBloodService
import tw.com.bluemobile.hbc.services.OrderService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.Bottom
import java.lang.Exception
import java.lang.reflect.Type

class DonateBloodListActivity : ListActivity<DonateBloodListViewHolder, DonateBloodModel>() {

    var source: String = "home"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_blood_list)

        if (intent.hasExtra("source")) {
            source = intent.getStringExtra("source")!!
        }

        setTop(false, "我要捐血")
        top?.showAdd(true)

        if (source == "home") {
            able_enum = TabEnum.donate_blood
            setBottom(able_enum)
        } else {
            findViewById<Bottom>(R.id.bottom) ?. let {
                it.visibility = View.GONE
            }
            top?.showPrev(true)
            top?.setTitle("我的寶貝")
        }

        loading = Loading(this)

        init()
    }

    override fun init() {
        adapter = DonateBloodListAdapter(R.layout.list_donate_blood, ::DonateBloodListViewHolder, source, onAcceptClick)
        //adapter
        super.init()

        refresh()
    }

    override fun getList(page: Int, perPage: Int) {

        loading.show()

        val params: MutableMap<String, String> = mutableMapOf()
        if (source == "member") {
            params.putAll(hashMapOf("member_token" to member.token!!))
        } else if (source == "home") {
            params.putAll(hashMapOf("IDo" to "1"))
        }

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
        if (!member.isLoggedIn) {
            warning("請先登入") {
                toLogin(this)
            }
            return
        }

        super.add()
        toDonateBloodEdit(this)
    }

    override val onRowClick: ((Int) -> Unit) = { idx ->
        val row: DonateBloodModel = rows[idx]
        toDonateBloodShow(this, row.token)
    }

    fun listSetSelected(row: DonateBloodModel): Boolean {

        return false
    }

    private fun delete(token: String) {
        loading.show()
        DonateBloodService.getDelete(this, token) {
            runOnUiThread {
                //println(MemberService.jsonString)

                loading.hide()
                success("刪除完成") {
                    refresh()
                }
            }
        }
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

    private val onAcceptClick: ((Int) -> Unit) = { idx ->
        if (!member.isLoggedIn) {
            warning("請先登入！！", "登入") {
                toLogin(this)
            }
        } else {
            val row: DonateBloodModel = rows[idx]
            if (row.status == "online") {
                info("是否確定寵物需要捐血", "確定需要") {
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
            "abProcess_donate_blood_token" to row.token,
            "abProcess_memberA_token" to member.token!!,
            "abProcess_memberB_token" to row.member_token,
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
                                success("已經建立此筆訂單，是否前往後續流程服務頁面？", "是") {
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
    val source: String,
    val onAcceptClick: ((Int) -> Unit)?):
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
        setTV(R.id.created_at, row.created_at_show)
        setTV(R.id.traffic_feeTV, row.traffic_fee.toString())
        setTV(R.id.nutrient_feeTV, row.nutrient_fee.toString())
    }

    fun setOnAcceptClickListener(idx: Int, onAcceptClick: ((Int) -> Unit)?) {
        view.findViewById<LinearLayout>(R.id.acceptContainer) ?. let {
            it.setOnClickListener {
                onAcceptClick?.invoke(idx)
            }
        }
    }
}