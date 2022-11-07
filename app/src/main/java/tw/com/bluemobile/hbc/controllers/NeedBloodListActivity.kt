package tw.com.bluemobile.hbc.controllers

import android.content.Context
import android.os.Bundle
import android.view.View
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.BaseAdapter
import tw.com.bluemobile.hbc.adapters.BaseViewHolder
import tw.com.bluemobile.hbc.models.BaseModels
import tw.com.bluemobile.hbc.models.NeedBloodModel
import tw.com.bluemobile.hbc.services.NeedBloodService
import tw.com.bluemobile.hbc.utilities.NeedBloodEnum
import tw.com.bluemobile.hbc.utilities.genericType
import java.lang.reflect.Type

class NeedBloodListActivity : ListActivity<NeedBloodListViewHolder, NeedBloodModel>() {

    var isMember: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_need_blood_list)

        setTop(true, "我需要血")
        top?.showAdd(true)

        init()
    }

    override fun init() {
        adapter = BaseAdapter(R.layout.list_donate_blood, ::NeedBloodListViewHolder)
        super.init()

        refresh()
    }

    override fun getList(page: Int, perPage: Int) {

        loading.show()

        val params: MutableMap<String, String> = hashMapOf()
        NeedBloodService.getList(this, params, page, perPage) { success ->
            runOnUiThread {
                //println(NeedBloodService.jsonString)

                val modelType: Type = genericType<BaseModels<NeedBloodModel>>()
                parseJSON(NeedBloodService.jsonString, modelType)
                loading.hide()
            }
        }
    }

    override fun add() {
        super.add()
        toNeedBloodEdit(this)
    }

    override val onEditClick: ((Int) -> Unit) = { idx ->
        val row: NeedBloodModel = rows[idx]
        toNeedBloodEdit(this, row.token)
    }

    override val onDeleteClick: ((Int) -> Unit) = { idx ->
        val row: NeedBloodModel = rows[idx]
        warning("是否確定要刪除?", "刪除") {
            delete(row.token)
        }
    }
    private fun delete(token: String) {
        loading.show()
        NeedBloodService.postDelete(this, token) {
            runOnUiThread {
                //println(MemberService.jsonString)

                loading.hide()
                success("刪除完成") {
                    refresh()
                }
            }
        }

    }
}

class NeedBloodListViewHolder(
    context: Context,
    view: View
): BaseViewHolder<NeedBloodModel>(context, view) {

    override fun bind(row: NeedBloodModel, idx: Int) {

        row.filterRow()
        super.bind(row, idx)

        setIV(R.id.typeIV, "ic_${row.type}")

        val typeEnum: NeedBloodEnum = NeedBloodEnum.enumFromString(row.type)
        setTV(R.id.typeTV, typeEnum.DBNameToRadioText(row.type))

        setTV(R.id.blood_typeTV, row.blood_type)
        setTV(R.id.created_at, row.created_at_show)
        setTV(R.id.traffic_feeTV, row.traffic_fee.toString())
        setTV(R.id.nutrient_feeTV, row.nutrient_fee.toString())
    }
}