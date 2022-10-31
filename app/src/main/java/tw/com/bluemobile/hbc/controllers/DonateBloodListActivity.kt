package tw.com.bluemobile.hbc.controllers

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.BaseAdapter
import tw.com.bluemobile.hbc.adapters.BaseViewHolder
import tw.com.bluemobile.hbc.models.BaseModels
import tw.com.bluemobile.hbc.models.MemberPetModel
import tw.com.bluemobile.hbc.models.NeedBloodModel
import tw.com.bluemobile.hbc.services.DonateBloodService
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.services.NeedBloodService
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.NeedBloodEnum
import tw.com.bluemobile.hbc.utilities.genericType
import java.lang.reflect.Type

class DonateBloodListActivity : ListActivity<DonateBloodListViewHolder, MemberPetModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_blood_list)

        setTop(false, "我要捐血")
        top?.showAdd(true)

        loading = Loading(this)

        init()
    }

    override fun init() {
        adapter = BaseAdapter(R.layout.list_member_pet, ::DonateBloodListViewHolder)
        super.init()

        refresh()
    }

    override fun getList(page: Int, perPage: Int) {

        loading.show()
        val params: MutableMap<String, String> = mutableMapOf()
        DonateBloodService.getList(this, params, page, perPage) { success ->
            runOnUiThread {
                //println(DonateBloodService.jsonString)

                val modelType: Type = genericType<BaseModels<MemberPetModel>>()
                parseJSON(DonateBloodService.jsonString, modelType)
                loading.hide()
            }
        }
    }

    override fun add() {
        super.add()
        toNeedBloodEdit(this)
    }
}

class DonateBloodListViewHolder(
    context: Context,
    view: View
): BaseViewHolder<MemberPetModel>(context, view) {

    override fun bind(row: MemberPetModel, idx: Int) {

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