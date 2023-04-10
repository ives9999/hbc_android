package tw.com.bluemobile.hbc.controllers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.BaseAdapter
import tw.com.bluemobile.hbc.adapters.BaseViewHolder
import tw.com.bluemobile.hbc.adapters.viewHolder
import tw.com.bluemobile.hbc.member
import tw.com.bluemobile.hbc.models.ABProcessModel
import tw.com.bluemobile.hbc.models.BaseModels
import tw.com.bluemobile.hbc.models.NeedBloodModel
import tw.com.bluemobile.hbc.services.ABProcessService
import tw.com.bluemobile.hbc.utilities.*
import java.lang.reflect.Type

class MemberABProcessListActivity : ListActivity<MemberABProcessListViewHolder, ABProcessModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_abprocess_list)

        setTop(false, "我的配對")
        top?.showAdd(true)
        top?.showPrev(true)

        loading = Loading(this)

        init()
    }

    override fun init() {
        adapter = MemberABProcessListAdapter(R.layout.list_member_abprocess, ::MemberABProcessListViewHolder, onAcceptClick)
        //adapter
        super.init()

        refresh()
    }

    override fun getList(page: Int, perPage: Int) {

        loading.show()

        val params: MutableMap<String, String> = hashMapOf()
        params.putAll(hashMapOf("member_token" to member.token!!))
        ABProcessService.getList(this, params, page, perPage) { success ->
            runOnUiThread {
                //println(NeedBloodService.jsonString)

                val modelType: Type = genericType<BaseModels<ABProcessModel>>()
                parseJSON(ABProcessService.jsonString, modelType)
                loading.hide()
            }
        }
    }

    private val onAcceptClick: ((Int) -> Unit) = { idx ->
        val row: ABProcessModel = rows[idx]
        toBloodProcess(this, row.order_token)
    }
}

class MemberABProcessListAdapter(
    override val resource: Int,
    override val viewHolderConstructor: viewHolder<MemberABProcessListViewHolder>,
    private val onAcceptClick: ((Int) -> Unit)?):

    BaseAdapter<MemberABProcessListViewHolder, ABProcessModel>(resource, viewHolderConstructor) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberABProcessListViewHolder {

        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(resource, parent, false)
        val viewHolder: MemberABProcessListViewHolder = viewHolderConstructor(parent.context, view)

        return viewHolder
    }

    override fun onBindViewHolder(holder: MemberABProcessListViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.setOnAcceptClickListener(position, onAcceptClick)
    }
}

class MemberABProcessListViewHolder(
    context: Context,
    view: View
): BaseViewHolder<ABProcessModel>(context, view) {

    var source: String = ""

    override fun bind(row: ABProcessModel, idx: Int) {

        row.filterRow()
        super.bind(row, idx)

        var type: String = ""
        if (row.need_blood_id > 0) {
            type = "donate"
        } else if (row.donate_blood_id > 0) {
            type = "need"
        }

        if (type == "donate") {
            view.findViewById<LinearLayout>(R.id.acceptLL)?.let {
                it.background = ContextCompat.getDrawable(context, R.drawable.circle_blue)
            }
            view.findViewById<TextView>(R.id.acceptTV)?.let {
                it.text = "捐血"
                it.setTextColor(getColor(context, R.color.MY_WHITE))
            }

            if (row.needBloodModel != null) {
                setIV(R.id.typeIV, "ic_${row.needBloodModel!!.type}")
                setTV(R.id.typeTV, row.needBloodModel!!.type_show)
                setTV(R.id.nameTV, row.needBloodModel!!.name)
            }

            setTV(R.id.nicknameTV, row.memberA_nickname)
            setTV(R.id.mobileTV, row.memberA_mobile_show)
        } else {
            view.findViewById<LinearLayout>(R.id.acceptLL) ?. let {
                it.background = ContextCompat.getDrawable(context, R.drawable.circle)
            }
            view.findViewById<TextView>(R.id.acceptTV)?.let {
                it.text = "需血"
                it.setTextColor(getColor(context, R.color.MY_BLACK))
            }

            if (row.donateBloodModel != null) {
                setIV(R.id.typeIV, "ic_${row.donateBloodModel!!.type}")
                setTV(R.id.typeTV, row.donateBloodModel!!.type_show)
                setTV(R.id.nameTV, row.donateBloodModel!!.name)
            }

            setTV(R.id.nicknameTV, row.memberB_nickname)
            setTV(R.id.mobileTV, row.memberB_mobile_show)
        }

        setTV(R.id.created_at, row.created_at_show)
    }

    fun setOnAcceptClickListener(idx: Int, onAcceptClick: ((Int) -> Unit)?) {
        view.findViewById<LinearLayout>(R.id.acceptContainer) ?. let {
            it.setOnClickListener {
                onAcceptClick?.invoke(idx)
            }
        }
    }
}