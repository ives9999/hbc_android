package tw.com.bluemobile.hbc.controllers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.*
import tw.com.bluemobile.hbc.models.BaseModels
import tw.com.bluemobile.hbc.models.MemberPetModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.genericType
import java.lang.reflect.Type

class MemberPetListActivity : ListActivity<MemberPetListViewHolder, MemberPetModel>() {

    override var rows: ArrayList<MemberPetModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_pet_list)

        setTop(true, "我的寶貝")
        top?.showAdd(true)

        init()
    }

    override fun init() {

        adapter = BaseAdapter(R.layout.list_member_pet, ::MemberPetListViewHolder)
        super.init()

        refresh()
    }

    override fun getList(page: Int, perPage: Int) {

        loading.show()
        MemberService.getPetList(this, page, perPage) { success ->
            runOnUiThread {
                //println(MemberService.jsonString)

                val modelType: Type = genericType<BaseModels<MemberPetModel>>()
                parseJSON(MemberService.jsonString, modelType)
                loading.hide()
            }
        }
    }

    override fun add() {
        super.add()
        toMemberPetEdit(this)
    }

    override val onRowClick: ((Int) -> Unit) = { idx ->
        val row: MemberPetModel = rows[idx]
        toMemberPetShhow(this, row.token)
    }

    fun listSetSelected(row: MemberPetModel): Boolean {

        return false
    }

    private fun delete(token: String) {
        loading.show()
        MemberService.postDeletePet(this, token) {
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
        val row: MemberPetModel = rows[idx]
        toMemberPetEdit(this, row.token)
    }

    override val onDeleteClick: ((Int) -> Unit) = { idx ->
        val row: MemberPetModel = rows[idx]
        warning("是否確定要刪除?", "刪除") {
            delete(row.token)
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
