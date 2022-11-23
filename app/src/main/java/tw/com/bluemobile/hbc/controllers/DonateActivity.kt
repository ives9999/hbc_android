package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.LinearLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.parseErrmsg
import tw.com.bluemobile.hbc.models.DonateModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.DonateService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.*
import java.lang.Exception

class DonateActivity : EditActivity() {

    private val formItems: ArrayList<HashMap<DonateEnum, MyLayout>> = arrayListOf()

    private var moreCity: SelectCity? = null
    private var moreArea: SelectArea? = null
    var moreDialog: MoreDialog? = null

    var creditCardNO: CreditCardNO? = null
    var creditCardMy: CreditCardMY? = null
    var creditCardCVV: CreditCardCVV? = null

    private val initData: MutableMap<String, String> = mutableMapOf(
        DonateEnum.amount.englishName to "500",
        DonateEnum.realname.englishName to "王大銘",
        DonateEnum.tax_no.englishName to "12335",
        DonateEnum.email.englishName to "john@gmail.tw",
        DonateEnum.tel.englishName to "13457",
        DonateEnum.receipt.englishName to "是",
        DonateEnum.city_id.englishName to "218",
        DonateEnum.area_id.englishName to "219",
        DonateEnum.road.englishName to "南華街101號8樓"
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        able_enum = TabEnum.donate
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        setTop(false, "我要捐款")
        setBottom(able_enum)

        loading = Loading(this)
        init()
    }

    override fun init() {
        super.init()

        val allEnums: ArrayList<DonateEnum> = DonateEnum.getAllEnum()
        for (enum in allEnums) {

            val key: String = enum.englishName
            val r: Int = resources.getIdentifier(enum.englishName, "id", packageName)
            findViewById<MyLayout>(r) ?. let {

                if (enum == DonateEnum.receipt) {

                    val receptRadio = it as TwoRadio

                    if (initData.containsKey(key)) {
                        receptRadio.setCheck(enum.DBNameToRadioText(initData[key]!!))
                    }
                } else if (enum == DonateEnum.credit_card_no) {
                    creditCardNO = it as CreditCardNO
                    creditCardNO?.myRequestFocus(MYFocus)
                } else if (enum == DonateEnum.credit_card_my) {
                    creditCardMy = it as CreditCardMY
                    creditCardMy?.setOnChangeListener(showWarning)
                    creditCardMy?.myRequestFocus(CVVFocus)
                } else if (enum == DonateEnum.credit_card_cvv) {
                    creditCardCVV = it as CreditCardCVV
                } else if (enum == DonateEnum.city_id || enum == DonateEnum.area_id) {
                    if (enum == DonateEnum.city_id) {
                        moreCity = it as SelectCity
                        it.setOnClickListener() {
                            val screenWidth = Global.getScreenWidth(resources)
                            moreDialog =
                                it.toMoreDialog(screenWidth, it.value, this)
                            //println(moreCity?.value)
                        }

                        it.setOnCancelClickListener {
                            it.clear()
                            moreArea?.clear()
                        }
                    } else {
                        moreArea = it as SelectArea
                        it.setOnClickListener {
                            if (moreCity == null || moreCity!!.value.isEmpty()) {
                                warning("請先選擇縣市")
                            } else {
                                val screenWidth = Global.getScreenWidth(resources)
                                val city_id: Int = moreCity?.value?.toInt() ?: 0
                                moreDialog = it.toMoreDialog(
                                    screenWidth,
                                    city_id,
                                    moreArea!!.value,
                                    this
                                )
                            }
                        }
                    }
                }
                val h: HashMap<DonateEnum, MyLayout> = hashMapOf(enum to it)
                formItems.add(h)

                if (initData.containsKey(key)) {
                    it.value = initData[key]!!
                    if (enum == DonateEnum.city_id || enum == DonateEnum.area_id) {
                        val b = it.setZone()
                        if (!b) {
                            warning("縣市或區域不是整數值")
                        }
                    }
                }
            }
        }

        findViewById<LinearLayout>(R.id.submitLL) ?. let {
            it.setOnClickListener {
                submit()
            }
        }
    }

    val CVVFocus: ()->Unit = {
        creditCardCVV?.initFocus()
    }

    val MYFocus: ()->Unit = {
        creditCardMy?.initFocus()
    }

    // set setting after city and area click.
    override fun cellClick(keyEnum: KeyEnum, id: Int) {
//        println(key)
//        println(id)
        if (keyEnum == KeyEnum.city_id) {
            moreCity?.setText(Zones.zoneIDToName(id))
            moreCity?.value = id.toString()
            moreDialog?.hide()
        } else if (keyEnum == KeyEnum.area_id) {
            moreArea?.setText(Zones.zoneIDToName(id))
            moreArea?.value = id.toString()
            moreDialog?.hide()
        }
    }

    override fun submit() {
        val params: MutableMap<String, String> = hashMapOf()
        msg = ""

        for (formItem in formItems) {
            for ((enum, layout) in formItem) {
                if (layout.isEmpty() && DonateEnum.getMustEnum().contains(enum)) {
                    msg += enum.errMsg()
                } else {
                    val k = enum.englishName
                    var v = layout.value
                    if (enum == DonateEnum.receipt) {
                        v = enum.radioTextToDBName(v)
                    }
                    val temp: HashMap<String, String> =
                        hashMapOf(k to v)
                    params.putAll(temp)
                }
            }
        }

        if (msg.isNotEmpty()) {
            warning(msg)
            return
        }

        //println(params)
        val n = 6

        loading.show()
        DonateService.update(this, params) { success ->
            if (success) {
                runOnUiThread {
                    try {
                        //println(DonateBloodService.jsonString)
                        val successModel =
                            jsonToModel<SuccessModel<DonateModel>>(DonateService.jsonString)
                        if (successModel != null) {
                            if (successModel.success) {
                                val donateBloodModel = successModel.model
                                success("新增 捐款成功") {
                                    //prev()
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
            } else {
                runOnUiThread {
                    warning("伺服器錯誤，請稍後再試，或洽管理人員")
                }
            }

            runOnUiThread {
                loading.hide()
            }
        }
    }

    val showWarning: (String) -> Unit = {
        warning(it)
    }
}