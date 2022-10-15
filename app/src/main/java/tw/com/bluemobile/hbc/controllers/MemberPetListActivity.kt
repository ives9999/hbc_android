package tw.com.bluemobile.hbc.controllers

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.reflect.TypeToken
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.BaseList
import tw.com.bluemobile.hbc.adapters.BaseViewHolder
import tw.com.bluemobile.hbc.adapters.didSelectClosure
import tw.com.bluemobile.hbc.adapters.selectedClosure
import tw.com.bluemobile.hbc.models.BaseModel
import tw.com.bluemobile.hbc.models.BaseModels
import tw.com.bluemobile.hbc.models.MemberPetModel
import java.lang.reflect.Type

class MemberPetListActivity : ListActivity() {

    private val modelType: Type = object : TypeToken<BaseModels<MemberPetModel>>() {}.type
    lateinit var baseList: BaseList<MemberPetListViewHolder, MemberPetModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_pet_list)

        setTop(true, "我的寶貝")

        init()
    }

    override fun init() {
        super.init()

        findViewById<RecyclerView>(R.id.list) ?. let {
            recyclerView = it
            baseList = BaseList(it, R.layout.list_member_pet, ::MemberPetListViewHolder, modelType, this::didSelect, this::listSetSelected)

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
    viewHolder: View,
    didSelect: didSelectClosure<MemberPetModel>,
    selected: selectedClosure<MemberPetModel>
): BaseViewHolder<MemberPetModel>(context, viewHolder, didSelect, selected) {

//    val titleLbl: TextView = viewHolder.titleLbl
//    val priceLbl: TextView = viewHolder.priceLbl

    override fun bind(row: MemberPetModel, idx: Int) {
        super.bind(row, idx)

//        titleLbl.text = row.name
//        priceLbl.text = "NT$: " + row.price.toString() + " 元/月"
    }
}
