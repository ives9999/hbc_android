package tw.com.bluemobile.hbc.controllers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.*
import tw.com.bluemobile.hbc.extensions.setImage
import tw.com.bluemobile.hbc.extensions.setInfo
import tw.com.bluemobile.hbc.models.BaseModel
import tw.com.bluemobile.hbc.models.BaseModels
import tw.com.bluemobile.hbc.models.MemberPetModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.jsonToModel
import java.lang.reflect.Type

class MemberPetListActivity<U: MemberPetModel> : ListActivity<U>() {

    lateinit var baseList: BaseList<MemberPetListViewHolder, MemberPetModel>
    override var rows: ArrayList<U> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_pet_list)

        setTop(true, "我的寶貝")
        top?.showAdd(true)

        init()
    }

    override fun init() {
        super.init()

        baseList = BaseList(recyclerView!!, R.layout.list_member_pet, ::MemberPetListViewHolder)

        baseList.adapter.onRowClick = onRowClick
        baseList.adapter.onEditClick = onEditClick
        baseList.adapter.onDeleteClick = onDeleteClick
        baseList.adapter.onRefreshClick = onRefreshClick

        refresh()
    }

    override fun getList(page: Int, perPage: Int) {

        loading.show()
        MemberService.getPetList(this, page, perPage) { success ->
            runOnUiThread {

                //println(MemberService.jsonString)

                val baseModels = jsonToModel<BaseModels<U>>(MemberService.jsonString)
                rows = baseModels?.rows .let { baseModels!!.rows  }
                val rows1 = rows as ArrayList<MemberPetModel>

                if (rows.size > 0) {
                    baseList.setRows(rows1)
                } else {
                    showNoRows()
                }
                loading.hide()
            }
        }
    }

    override fun add() {
        super.add()
        toMemberPetEdit(this)
    }

    val onRowClick: ((Int) -> Unit) = { idx ->
        println(idx)
    }

    fun listSetSelected(row: MemberPetModel): Boolean {

        return false
    }

    val onEditClick: ((Int) -> Unit) = { idx ->
        val row: MemberPetModel = rows[idx]
        toMemberPetEdit(this, row.token)
    }

    val onDeleteClick: ((Int) -> Unit) = { idx ->
        val row: MemberPetModel = rows[idx]
    }

    val memberPetEditAR: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            val i: Intent? = res.data
            if (i != null) {
                refresh()
            }
        }
    }
}

class MemberPetListViewHolder(
    context: Context,
    view: View
): BaseViewHolder<MemberPetModel>(context, view) {

    override fun bind(row: MemberPetModel, idx: Int) {

        row.filterRow()
        super.bind(row, idx)

        setTV(R.id.blood_typeTV, row.blood_type)
        setTV(R.id.weightTV, row.weight.toString())
        setTV(R.id.ageTV, row.age.toString())
        setTV(R.id.created_at, row.created_at_show)
        setIV(R.id.typeIV, "ic_${row.type}")
    }
}

//class MyAdapter: RecyclerView.Adapter<MyViewHolder>() {
//
//    var list: ArrayList<MemberPetModel> = arrayListOf()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
//        val view: View = inflater.inflate(R.layout.list_member_pet, parent, false)
//        return MyViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.bind(list[position], position)
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//}
//
//class MyViewHolder(viewHolder: View): RecyclerView.ViewHolder(viewHolder) {
//
//    fun bind(row: MemberPetModel, idx: Int) {
//
//    }
//}
