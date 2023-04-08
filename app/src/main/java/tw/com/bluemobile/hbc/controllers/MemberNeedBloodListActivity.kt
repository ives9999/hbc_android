package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.member
import tw.com.bluemobile.hbc.models.BaseModels
import tw.com.bluemobile.hbc.models.NeedBloodModel
import tw.com.bluemobile.hbc.services.NeedBloodService
import tw.com.bluemobile.hbc.utilities.genericType
import java.lang.reflect.Type

class MemberNeedBloodListActivity : ListActivity<NeedBloodListViewHolder, NeedBloodModel>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_need_blood_list)

        setTop(false, "我需要血")
        top?.showAdd(true)
        top?.showPrev(true)

        init()
    }

    override fun init() {
        adapter = NeedBloodListAdapter(R.layout.list_need_blood, ::NeedBloodListViewHolder, "member", onAcceptClick)
        super.init()

        refresh()
    }

    override fun getList(page: Int, perPage: Int) {

        loading.show()

        val params: MutableMap<String, String> = hashMapOf()
        params.putAll(hashMapOf("member_token" to member.token!!))
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

    override val onRowClick: ((Int) -> Unit) = { idx ->
        val row: NeedBloodModel = rows[idx]
        toNeedBloodShow(this, row.token)
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

    val onAcceptClick: ((Int) -> Unit) = { idx -> }
}