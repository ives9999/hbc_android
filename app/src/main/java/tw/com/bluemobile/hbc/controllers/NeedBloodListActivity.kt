package tw.com.bluemobile.hbc.controllers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.BaseAdapter
import tw.com.bluemobile.hbc.adapters.BaseViewHolder
import tw.com.bluemobile.hbc.extensions.parseErrmsg
import tw.com.bluemobile.hbc.member
import tw.com.bluemobile.hbc.models.*
import tw.com.bluemobile.hbc.services.NeedBloodService
import tw.com.bluemobile.hbc.services.OrderService
import tw.com.bluemobile.hbc.utilities.NeedBloodEnum
import tw.com.bluemobile.hbc.utilities.genericType
import tw.com.bluemobile.hbc.utilities.jsonToModel
import java.lang.Exception
import java.lang.reflect.Type

class NeedBloodListActivity : ListActivity<NeedBloodListViewHolder, NeedBloodModel>() {

    var source: String = "member"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_need_blood_list)

        setTop(true, "我需要血")
        top?.showAdd(true)

        init()
    }

    override fun init() {
        adapter = NeedBloodListAdapter(R.layout.list_donate_blood, ::NeedBloodListViewHolder, source, onAcceptClick)
        super.init()

        refresh()
    }

    override fun getList(page: Int, perPage: Int) {

        loading.show()

        val params: MutableMap<String, String> = hashMapOf()
        if (source == "member") {
            params.putAll(hashMapOf("member_token" to member.token!!))
        }
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

    val onAcceptClick: ((Int) -> Unit) = { idx ->
        if (!member.isLoggedIn) {
            warning("請先登入！！", "登入") {
                toLogin(this)
            }
        } else {
            val row: NeedBloodModel = rows[idx]
            if (row.status == "online") {
                info("是否確定寵物要做捐血", "確定捐血") {
                    insertBloodProcess(idx)
                }
            } else if (row.status == "process") {
                if (member.token != row.memberA_token && member.token != row.memberB_token) {
                    warning("您不是該捐需血的主人，無法檢視進行中的流程!!")
                } else {
                    toBloodProcess(this, row.order_token)
                }
            }
        }
    }

    private fun insertBloodProcess(idx: Int) {
        val row: NeedBloodModel = rows[idx]
        val params: HashMap<String, String> = hashMapOf(
            "need_blood_token" to row.token,
            "memberA_token" to member.token!!,
            "memberB_token" to row.member_token,
            "product_type" to "blood"
        )
        //println(params);

        loading.show()
        OrderService.update(this, params) { success ->
            runOnUiThread {
                loading.hide()
            }
            if (success) {
                runOnUiThread {
                    try {
                        //println(DonateBloodService.jsonString)
                        val successModel =
                            jsonToModel<SuccessModel<OrderModel>>(OrderService.jsonString)
                        if (successModel != null) {
                            if (successModel.success) {
                                val orderModel: OrderModel = successModel.model!!
                                success("已經建立此筆訂單，是否前往後續流程服務頁面？", "是") {
                                    toBloodProcess(this, orderModel.token)
                                }
                            } else {
                                warning(successModel.msgs.parseErrmsg())
                            }
                        } else {
                            warning("app無法解析系統傳回值，請洽管理員")
                        }
                    } catch (e: Exception) {
                        warning(e.localizedMessage)
                    }
                }
            }
        }
    }
}