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
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.services.NeedBloodService
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
        adapter = BaseAdapter(R.layout.list_member_pet, ::NeedBloodListViewHolder)
        super.init()

        refresh()
    }

    override fun getList(page: Int, perPage: Int) {

        loading.show()

        val params: MutableMap<String, String> = hashMapOf()
        NeedBloodService.getList(this, params, page, perPage) { success ->
            runOnUiThread {
                println(NeedBloodService.jsonString)

                val modelType: Type = genericType<BaseModels<NeedBloodModel>>()
                parseJSON(NeedBloodService.jsonString, modelType)
                loading.hide()
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

//        setTV(R.id.blood_typeTV, row.blood_type)
//        setTV(R.id.weightTV, row.weight.toString())
//        setTV(R.id.ageTV, row.age.toString())
//        setTV(R.id.created_at, row.created_at_show)
//        setIV(R.id.typeIV, "ic_${row.type}")
    }
}