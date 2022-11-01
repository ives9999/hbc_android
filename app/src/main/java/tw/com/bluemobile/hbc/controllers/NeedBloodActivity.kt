package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.databinding.ActivityMainBinding
import tw.com.bluemobile.hbc.extensions.parseErrmsg
import tw.com.bluemobile.hbc.models.MemberPetModel
import tw.com.bluemobile.hbc.models.NeedBloodModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.DonateBloodService
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.services.NeedBloodService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.*
import java.lang.Exception
import java.lang.reflect.Type

class NeedBloodActivity : EditActivity() {

    private lateinit var binding: ActivityMainBinding

    var moreCity: More? = null
    var moreArea: More? = null
    var typeRadio: TwoRadio? = null
    var catBloodTypeRadio: ThreeRadio? = null
    var dogBloodTypeRadio: TwoRadio? = null

    var moreDialog: MoreDialog? = null
    var token: String? = null
    var needBloodModel: NeedBloodModel? = null

    private val formItems: ArrayList<HashMap<NeedBloodEnum, MyLayout>> = arrayListOf()

    private val initData: MutableMap<String, String> = mutableMapOf(
//        NeedBloodEnum.hospital_name.englishName to "忍者貓侍",
//        NeedBloodEnum.hospital_city_id.englishName to "218",
//        NeedBloodEnum.hospital_area_id.englishName to "219",
//        NeedBloodEnum.hospital_road.englishName to "南華街101號8樓",
//        NeedBloodEnum.traffic_fee.englishName to "100",
//        NeedBloodEnum.nutrient_fee.englishName to "200",
//
//        NeedBloodEnum.type.englishName to "貓",
//        NeedBloodEnum.blood_type_cat.englishName to "B"
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        able_enum = TabEnum.need_blood
        super.onCreate(savedInstanceState)

        if (intent.hasExtra("token")) {
            token = intent.getStringExtra("token")!!
        }

        if (token == null) {
//        Thread.sleep(1000)
            val splashScreen = installSplashScreen()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        //setContentView(binding.root)

        setTop(false, "我需要血")
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
        val params: HashMap<String, String> = hashMapOf("needblood_token" to token!!)
        NeedBloodService.getOne(this, params) { success ->
            if (success) {
                val modelType: Type = genericType<SuccessModel<NeedBloodModel>>()
                needBloodModel = parseJSONAndInit<NeedBloodModel>(DonateBloodService.jsonString, modelType)

//                needBloodModel = parseJSON<NeedBloodModel>(NeedBloodService.jsonString)
//                needBloodModel?.filterRow()
//                runOnUiThread {
//                    if (needBloodModel != null) {
//                        for (enum in MemberPetEnum.getAllEnum()) {
//                            val name = enum.englishName
//                            val value = getPropertyValue(needBloodModel!!, name)
//                            initData.put(name, value)
//                        }
//                        init()
//                    }
//                    loading.hide()
//                }
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

                if (enum == NeedBloodEnum.hospital_city_id || enum == NeedBloodEnum.hospital_area_id) {

                    if (initData.containsKey(key)) {
                        it.value = initData[key]!!
                        it.setZone()
                    }

                    val screenWidth = Global.getScreenWidth(resources)
                    if (enum == NeedBloodEnum.hospital_city_id) {
                        moreCity = it as SelectCity
                        it.setOnClickListener {
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
                } else if (enum == NeedBloodEnum.type) {
                    typeRadio = it as TwoRadio

                    it.setOnGroupCheckedChangeListener { newType ->
                        typeChange(newType)
                    }

                    if (initData.containsKey(key)) {
                        it.setCheck(enum.DBNameToRadioText(initData[key]!!))
                    }

                } else if (enum == NeedBloodEnum.blood_type_cat) {
                    catBloodTypeRadio = it as ThreeRadio

                    if (initData.containsKey(key)) {
                        it.setCheck(enum.DBNameToRadioText(initData[key]!!))
                    }

                } else if (enum == NeedBloodEnum.blood_type_dog) {
                    dogBloodTypeRadio = it as TwoRadio

                    if (initData.containsKey(key)) {
                        it.setCheck(enum.DBNameToRadioText(initData[key]!!))
                    }

                } else {
                    if (initData.containsKey(key)) {
                        it.value = initData[key]!!
                    }

                }
                val h: HashMap<NeedBloodEnum, MyLayout> = hashMapOf(enum to it)
                formItems.add(h)
            }
        }

        findViewById<LinearLayout>(R.id.submitLL) ?. let {
            it.setOnClickListener {
                submit()
            }
        }
    }

    private fun typeChange(type: String) {
        if (type == "狗") {
            dogBloodTypeRadio?.visibility = View.VISIBLE
            catBloodTypeRadio?.visibility = View.GONE
        } else {
            dogBloodTypeRadio?.visibility = View.GONE
            catBloodTypeRadio?.visibility = View.VISIBLE
        }
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

        var type: String = "" //cat or dog
        for (formItem in formItems) {
            for ((enum, layout) in formItem) {
                if (layout.isEmpty()) {
                    msg += enum.errMsg()
                } else {
                    val k = enum.englishName
                    var v = layout.value
                    if (enum == NeedBloodEnum.type) {
                        v = enum.radioTextToDBName(v)
                        type = v
                    }
                    val temp: HashMap<String, String> =
                        hashMapOf(k to v)
                    params.putAll(temp)
                }
            }
        }

        var blood_type: String = ""

        for (formItem in formItems) {
            for ((enum, layout) in formItem) {
                if (enum.englishName == "blood_type_${type}") {
                    if (layout.value.isEmpty()) {
                        msg += "請選擇血液血型\n"
                    } else {
                        blood_type = layout.value
                    }
                }
            }
        }

        params.put("blood_type", blood_type)
        params.remove("blood_type_cat")
        params.remove("blood_type_dog")

        //println(params)

        if (msg.isNotEmpty()) {
            warning(msg)
            return
        }

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