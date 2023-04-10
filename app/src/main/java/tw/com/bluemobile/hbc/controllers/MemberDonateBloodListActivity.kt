package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.member
import tw.com.bluemobile.hbc.models.BaseModels
import tw.com.bluemobile.hbc.models.DonateBloodModel
import tw.com.bluemobile.hbc.services.DonateBloodService
import tw.com.bluemobile.hbc.utilities.genericType
import java.lang.reflect.Type

class MemberDonateBloodListActivity : ListActivity<DonateBloodListViewHolder, DonateBloodModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_blood_list)

        setTop(false, "我的寶貝")
        top?.showAdd(true)
        top?.showPrev(true)

        init()
    }

    override fun init() {
        adapter = DonateBloodListAdapter(R.layout.list_donate_blood, ::DonateBloodListViewHolder, "member", onAcceptClick)
        super.init()

        refresh()
    }

    override fun getList(page: Int, perPage: Int) {

        loading.show()

        val params: MutableMap<String, String> = hashMapOf()
        params.putAll(hashMapOf("member_token" to member.token!!))
        DonateBloodService.getList(this, params, page, perPage) { success ->
            runOnUiThread {
                //println(NeedBloodService.jsonString)

                val modelType: Type = genericType<BaseModels<DonateBloodModel>>()
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
        val row: DonateBloodModel = rows[idx]
        toDonateBloodShow(this, row.token)
    }

    override val onEditClick: ((Int) -> Unit) = { idx ->
        val row: DonateBloodModel = rows[idx]
        toDonateBloodEdit(this, row.token)
    }

    override val onDeleteClick: ((Int) -> Unit) = { idx ->
        val row: DonateBloodModel = rows[idx]
        warning("是否確定要刪除?", "刪除") {
            delete(row.token)
        }
    }

    private fun delete(token: String) {
        loading.show()
        DonateBloodService.postDelete(this, token) {
            runOnUiThread {
                //println(MemberService.jsonString)

                loading.hide()
                success("刪除完成") {
                    refresh()
                }
            }
        }
    }

    val onAcceptClick: ((Int) -> Unit) = { idx -> }
}