package tw.com.bluemobile.hbc.controllers

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.BaseAdapter
import tw.com.bluemobile.hbc.adapters.BaseViewHolder
import tw.com.bluemobile.hbc.adapters.viewHolder
import tw.com.bluemobile.hbc.member
import tw.com.bluemobile.hbc.models.BaseModels
import tw.com.bluemobile.hbc.models.MemberPetModel
import tw.com.bluemobile.hbc.models.NeedBloodModel
import tw.com.bluemobile.hbc.services.DonateBloodService
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.services.NeedBloodService
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.NeedBloodEnum
import tw.com.bluemobile.hbc.utilities.genericType
import tw.com.bluemobile.hbc.views.Bottom
import java.lang.reflect.Type

class DonateBloodListActivity : ListActivity<DonateBloodListViewHolder, MemberPetModel>() {

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
            setBottom(able_enum)
        } else {
            findViewById<Bottom>(R.id.bottom) ?. let {
                it.visibility = View.GONE
            }
            top?.showPrev(true)
        }

        loading = Loading(this)

        init()
    }

    override fun init() {
        adapter = DonateBloodListAdapter(R.layout.list_member_pet, ::DonateBloodListViewHolder, source)
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

                val modelType: Type = genericType<BaseModels<MemberPetModel>>()
                parseJSON(DonateBloodService.jsonString, modelType)
                loading.hide()
            }
        }
    }

    override fun add() {
        super.add()
        toDonateBloodEdit(this)
    }

    override val onRowClick: ((Int) -> Unit) = { idx ->
        val row: MemberPetModel = rows[idx]
        toDonateBloodShhow(this, row.token)
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
        toDonateBloodEdit(this, row.token)
    }

    override val onDeleteClick: ((Int) -> Unit) = { idx ->
        val row: MemberPetModel = rows[idx]
        warning("是否確定要刪除?", "刪除") {
            delete(row.token)
        }
    }
}

class DonateBloodListAdapter(override val resource: Int, override val viewHolderConstructor: viewHolder<DonateBloodListViewHolder>, val source: String): BaseAdapter<DonateBloodListViewHolder, MemberPetModel>(resource, viewHolderConstructor) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonateBloodListViewHolder {

        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(resource, parent, false)
        val viewHolder: DonateBloodListViewHolder = viewHolderConstructor(parent.context, view)
        viewHolder.source = source

        return viewHolder
    }
}

class DonateBloodListViewHolder(
    context: Context,
    view: View,
): BaseViewHolder<MemberPetModel>(context, view) {

    var source: String = ""

    override fun bind(row: MemberPetModel, idx: Int) {

        row.filterRow()
        super.bind(row, idx)

        if (source == "home") {
            view.findViewById<View>(R.id.line)?.let {
                it.visibility = View.GONE
            }
            view.findViewById<LinearLayout>(R.id.iconContainer)?.let {
                it.visibility = View.GONE
            }
        }

        setIV(R.id.typeIV, "ic_${row.type}")

        val typeEnum: NeedBloodEnum = NeedBloodEnum.enumFromString(row.type)
        setTV(R.id.typeTV, typeEnum.DBNameToRadioText(row.type))

        setTV(R.id.blood_typeTV, row.blood_type)
        setTV(R.id.created_at, row.created_at_show)
        setTV(R.id.traffic_feeTV, row.traffic_fee.toString())
        setTV(R.id.nutrient_feeTV, row.nutrient_fee.toString())
    }
}