package tw.com.bluemobile.hbc.controllers

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.*
import tw.com.bluemobile.hbc.models.BaseModel
import tw.com.bluemobile.hbc.models.BaseModels
import tw.com.bluemobile.hbc.models.MemberPetModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.jsonToModel
import java.lang.reflect.Type

class MemberPetListActivity : ListActivity() {

    //private val modelType: Type = object : TypeToken<BaseModels<MemberPetModel>>() {}.type
    //lateinit var baseList: BaseList<MemberPetListViewHolder, MemberPetModel>
    lateinit var adapter: BaseAdapter<MemberPetListViewHolder, MemberPetModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_pet_list)

        setTop(true, "我的寶貝")

        init()
    }

    override fun init() {
        super.init()
        loading = Loading(this)

        findViewById<RecyclerView>(R.id.list) ?. let {
            recyclerView = it
        }

        //baseList = BaseList(recyclerView!!, R.layout.list_member_pet, ::MemberPetListViewHolder, this::didSelect, this::listSetSelected)
        adapter = BaseAdapter(R.layout.list_member_pet, ::MemberPetListViewHolder)
        recyclerView!!.adapter = adapter

        refresh()
    }

    private fun refresh() {
        page = 1
        getList()
    }

    private fun getList() {

        loading.show()
        MemberService.getPetList(this, page, perPage) { success ->
            runOnUiThread {

                //println(MemberService.jsonString)

                val baseModels = jsonToModel<BaseModels<MemberPetModel>>(MemberService.jsonString)
                val rows: ArrayList<MemberPetModel> = baseModels?.rows .let { baseModels!!.rows } ?: arrayListOf<MemberPetModel>()
                //baseList.adapter.items = rows
                //baseList.adapter.notifyDataSetChanged()
                //MyTable2IF
//                val b: Boolean = showTableView(tableView, MemberService.jsonString)
//                if (b) {
//                    tableView.notifyDataSetChanged()
//                } else {
//                    val rootView: ViewGroup = getRootView()
//                    rootView.setInfo(this, "目前暫無資料")
//                }
                loading.hide()
            }
        }
    }

    fun didSelect(row: MemberPetModel, idx: Int) {
        //toMemberSubscriptionPay(row.name, row.price, row.eng_name)
    }

    fun listSetSelected(row: MemberPetModel): Boolean {

        return false
    }

}

class MemberPetListViewHolder(
    context: Context,
    viewHolder: View
//    didSelect: didSelectClosure<MemberPetModel>,
//    selected: selectedClosure<MemberPetModel>
): BaseViewHolder<MemberPetModel>(context, viewHolder) {

//    val titleLbl: TextView = viewHolder.titleLbl
//    val priceLbl: TextView = viewHolder.priceLbl

    override fun bind(row: MemberPetModel, idx: Int) {
        super.bind(row, idx)

//        titleLbl.text = row.name
//        priceLbl.text = "NT$: " + row.price.toString() + " 元/月"
    }
}
