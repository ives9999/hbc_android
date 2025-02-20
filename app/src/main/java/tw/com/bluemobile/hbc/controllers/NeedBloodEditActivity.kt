package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.parseErrmsg
import tw.com.bluemobile.hbc.models.*
import tw.com.bluemobile.hbc.services.HospitalService
import tw.com.bluemobile.hbc.services.NeedBloodService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.*
import java.lang.Exception
import java.lang.reflect.Type

class NeedBloodEditActivity : EditActivity(), MoreDialogDelegate {

    var moreHospital: More? = null
    var typeRadio: TwoRadio? = null
    var catBloodTypeRadio: ThreeRadio? = null
    var dogBloodTypeRadio: TwoRadio? = null
    private var hospitalDialog: SelectHospitalDialog? = null

    var type: String = ""
    var blood_type: String = ""

    var token: String? = null
    var needBloodModel: NeedBloodModel? = null

    var rows: ArrayList<HospitalModel> = arrayListOf()
    //private var page: Int = 1
    protected var perPage: Int = PERPAGE
    //protected var totalCount: Int = 0
    //protected var totalPage: Int = 0

    private val formItems: ArrayList<HashMap<NeedBloodEnum, MyLayout>> = arrayListOf()

    private val initData: MutableMap<String, String> = mutableMapOf(
//        NeedBloodEnum.hospital_name.englishName to "忍者貓侍",
//        NeedBloodEnum.hospital_city_id.englishName to "218",
//        NeedBloodEnum.hospital_area_id.englishName to "219",
//        NeedBloodEnum.hospital_road.englishName to "南華街101號8樓",
//        NeedBloodEnum.traffic_fee.englishName to "100",
//        NeedBloodEnum.nutrient_fee.englishName to "200",
//        NeedBloodEnum.petName.englishName to "來福",
//        NeedBloodEnum.type.englishName to "貓",
//        NeedBloodEnum.blood_type_cat.englishName to "A"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_need_blood_edit)

        if (intent.hasExtra("token")) {
            token = intent.getStringExtra("token")!!
        }
        setTop(true, "我需要血")
        loading = Loading(this)

        if (token != null) {
            refresh()
        } else {
            init()
            setBottom(able_enum)
        }
    }

    override fun refresh() {

        super.refresh()

        loading.show()
        val params: HashMap<String, String> = hashMapOf("token" to token!!)
        NeedBloodService.getOne(this, params) { success ->
            if (success) {
                runOnUiThread {
                    try {
                        //println(NeedBloodService.jsonString)
                        val successModel =
                            jsonToModel<SuccessModel<NeedBloodModel>>(NeedBloodService.jsonString)
                        if (successModel != null) {
                            if (successModel.success) {
                                needBloodModel = successModel.model
                                if (needBloodModel != null) {
                                    for (enum in NeedBloodEnum.getAllEnum()) {
                                        val name = enum.englishName
                                        val value = getPropertyValue(needBloodModel!!, name)
                                        initData.put(name, value)
                                    }
                                    init()
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
                    loading.hide()
                }
            }
        }
    }

    override fun init() {
        super.init()

        val allEnums: ArrayList<NeedBloodEnum> = NeedBloodEnum.getAllEnum()
        for (enum in allEnums) {

            val key: String = enum.englishName
            val r: Int = resources.getIdentifier(enum.englishName, "id", packageName)
            findViewById<MyLayout>(r)?.let {

                if (enum == NeedBloodEnum.hospital_token) {
                    moreHospital = it as More

                    if (initData.containsKey(key) && needBloodModel != null) {
                        it.setText(needBloodModel!!.hospital_name)
                        it.value = initData[key]!!
                    }

                    it.setOnClickListener {
                        val screenWidth = Global.getScreenWidth(resources)
                        hospitalDialog = SelectHospitalDialog(this, screenWidth, ::HospitalSelectSingleViewHolder, it.value, this)
                    }
                } else if (enum == NeedBloodEnum.type) {
                    typeRadio = it as TwoRadio

                    it.setOnGroupCheckedChangeListener { newType ->
                        typeChange(newType)
                    }

                    if (initData.containsKey(key)) {
                        it.setCheck(enum.DBNameToRadioText(initData[key]!!))
                        type = it.value
                    }

                } else if (enum == NeedBloodEnum.blood_type_cat) {
                    catBloodTypeRadio = it as ThreeRadio

//                    if (initData.containsKey(key)) {
//                        it.setCheck(enum.DBNameToRadioText(initData[key]!!))
//                    }

                } else if (enum == NeedBloodEnum.blood_type_dog) {
                    dogBloodTypeRadio = it as TwoRadio

//                    if (initData.containsKey(key)) {
//                        it.setCheck(enum.DBNameToRadioText(initData[key]!!))
//                    }

                } else {
                    if (initData.containsKey(key)) {
                        it.value = initData[key]!!
                    }
                }
                val h: HashMap<NeedBloodEnum, MyLayout> = hashMapOf(enum to it)
                formItems.add(h)
            }
        }

        initData[DonateBloodEnum.type.englishName]?.let {
            typeChange(it)
        }

        dogBloodTypeRadio?.setOnGroupCheckedChangeListener(dogChanged)
        catBloodTypeRadio?.setOnGroupCheckedChangeListener(catChanged)

        if (needBloodModel != null) {
            top!!.setTitle(needBloodModel!!.name)
        }

        findViewById<LinearLayout>(R.id.submitLL) ?. let {
            it.setOnClickListener {
                submit()
            }
        }
    }

    override fun modelToInitData(model: BaseModel) {

        val donateBloodModel = model as DonateBloodModel
        for (enum in DonateBloodEnum.getAllEnum()) {
            val name = enum.englishName
            val value = getPropertyValue(donateBloodModel, name)
            initData.put(name, value)
        }
    }

    override fun delegateHospitalClick(token: String, name: String) {
        moreHospital?.setText(name)
        moreHospital?.value = token
        hospitalDialog?.hide()
    }

    private fun typeChange(type: String) {
        if (type == "狗") {
            dogBloodTypeRadio?.visibility = View.VISIBLE
            catBloodTypeRadio?.visibility = View.GONE

            if (initData[DonateBloodEnum.type.englishName] == type) {
                dogBloodTypeRadio?.setCheck(DonateBloodEnum.blood_type_dog.englishName)
                blood_type = dogBloodTypeRadio?.value.toString()
            } else {
                dogBloodTypeRadio?.setCheck("DEA1陽性")
                blood_type = "DEA1陽性"
            }

            this.type = "dog"
        } else {
            dogBloodTypeRadio?.visibility = View.GONE
            catBloodTypeRadio?.visibility = View.VISIBLE

            if (initData[DonateBloodEnum.type.englishName] == type) {
                catBloodTypeRadio?.setCheck(DonateBloodEnum.blood_type_cat.englishName)
                blood_type = catBloodTypeRadio?.value.toString()
            } else {
                catBloodTypeRadio?.setCheck("A")
                blood_type = "A"
            }

            this.type = "cat"
        }
    }

    private val dogChanged: (String)-> Unit = {
        this.blood_type = it
    }

    private val catChanged: (String)-> Unit = {
        this.blood_type = it
    }

    override fun submit() {

        val params: MutableMap<String, String> = hashMapOf()

//        var type: String = "" //cat or dog
        for (formItem in formItems) {
            for ((enum, layout) in formItem) {
                println(layout.key)
                if (layout.isEmpty()) {
                    msg += enum.errMsg()
                } else {
                    val k = enum.englishName
                    val v = layout.value
//                    if (enum == NeedBloodEnum.type) {
//                        v = enum.radioTextToDBName(v)
//                        type = v
//                    }
                    val temp: HashMap<String, String> =
                        hashMapOf(k to v)
                    params.putAll(temp)
                }
            }
        }

//        var blood_type: String = ""
//
//        for (formItem in formItems) {
//            for ((enum, layout) in formItem) {
//                if (enum.englishName == "blood_type_${type}") {
//                    if (layout.value.isEmpty()) {
//                        msg += "請選擇血液血型\n"
//                    } else {
//                        blood_type = layout.value
//                    }
//                }
//            }
//        }
//
//        params.put("blood_type", blood_type)
//        if (token != null) {
//            params.put(TOKEN_KEY, token!!)
//        }
//        params.remove("blood_type_cat")
//        params.remove("blood_type_dog")

        if (msg.isNotEmpty()) {
            warning(msg)
            return
        }

        params[DonateBloodEnum.type.englishName] = type
        params[DonateBloodEnum.blood_type.englishName] = blood_type

        if (token != null) {
            params.put("token", token!!)
        }
        println(params)

        loading.show()
        NeedBloodService.update(this, params) { success ->
            if (success) {
                runOnUiThread {
                    try {
                        //println(MemberService.jsonString)
                        val successModel =
                            jsonToModel<SuccessModel<NeedBloodModel>>(NeedBloodService.jsonString)
                        if (successModel != null) {
                            if (successModel.success) {
                                val memberPetModel = successModel.model
                                success("新增/修改 我需要血成功") {
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
}