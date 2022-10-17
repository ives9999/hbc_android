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

class MemberPetListActivity : ListActivity() {

    //private val modelType: Type = object : TypeToken<BaseModels<MemberPetModel>>() {}.type
    lateinit var baseList: BaseList<MemberPetListViewHolder, MemberPetModel>
//    lateinit var adapter: BaseAdapter<MemberPetListViewHolder, MemberPetModel>
//    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_pet_list)

        setTop(true, "我的寶貝")
        top?.showAdd(true)

        init()
    }

    override fun init() {
        super.init()
        loading = Loading(this)

        findViewById<RecyclerView>(R.id.list) ?. let {
            recyclerView = it
            it.layoutManager = LinearLayoutManager(this)
//            adapter = MyAdapter()

            baseList = BaseList(recyclerView!!, R.layout.list_member_pet, ::MemberPetListViewHolder, this::didSelect, this::listSetSelected)
            baseList.adapter.onEditClick = onEditClick
        }

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
                if (rows.size > 0) {
                    baseList.setRows(rows)
                } else {
                    showNoRows()
                }
                loading.hide()
            }
        }
    }

    fun didSelect(row: MemberPetModel, idx: Int) {
        println(row)
    }

    fun listSetSelected(row: MemberPetModel): Boolean {

        return false
    }

    override fun add() {
        super.add()
        toMemberPetEdit(this)
    }

    val onEditClick: ((Int) -> Unit) = { idx ->
        println(idx)
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
    view: View,
    didSelect: didSelectClosure<MemberPetModel>,
    selected: selectedClosure<MemberPetModel>
): BaseViewHolder<MemberPetModel>(context, view, didSelect, selected) {

    //val nameTV: TextView? = null

    override fun bind(row: MemberPetModel, idx: Int) {
        super.bind(row, idx)

        row.filterRow()
        setTV(R.id.nameTV, row.name)
        setTV(R.id.blood_typeTV, row.blood_type)
        setTV(R.id.weightTV, row.weight.toString())
        setTV(R.id.ageTV, row.age.toString())
        setTV(R.id.created_at, row.created_at_show)
        setIV(R.id.typeIV, "ic_${row.type}")

        view.findViewById<ImageView>(R.id.editIV) ?. let {
            it.setOnClickListener {
                onEditClick?.invoke(idx)
            }
        }
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
