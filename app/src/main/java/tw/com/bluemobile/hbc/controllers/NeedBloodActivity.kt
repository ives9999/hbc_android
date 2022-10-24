package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.view.View
import androidx.navigation.ui.AppBarConfiguration
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import tw.com.bluemobile.hbc.databinding.ActivityMainBinding
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.*

class NeedBloodActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var moreCity: More? = null
    var moreArea: More? = null
    var typeRadio: TwoRadio? = null

    var moreDialog: MoreDialog? = null

    private val formItems: ArrayList<HashMap<NeedBloodEnum, MyLayout>> = arrayListOf()

    private val initData: MutableMap<String, String> = mutableMapOf(
//        MemberPetEnum.petName.englishName to "幸運貓",
//        MemberPetEnum.type.englishName to "狗",
//        MemberPetEnum.age.englishName to "5",
//        MemberPetEnum.weight.englishName to "10",
//        MemberPetEnum.blood_type.englishName to "A",
//        MemberPetEnum.IDo.englishName to "願意",
//        MemberPetEnum.traffic_fee.englishName to "100",
//        MemberPetEnum.nutrient_fee.englishName to "200"
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        able_enum = TabEnum.need_blood
        super.onCreate(savedInstanceState)

//        Thread.sleep(1000)
        val splashScreen = installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTop(false, "我需要血")
        setBottom(able_enum)
        loading = Loading(this)

        init()
    }

    override fun init() {
        super.init()

        val allEnums: ArrayList<NeedBloodEnum> = NeedBloodEnum.getAllEnum()
        for (enum in allEnums) {

            val key: String = enum.englishName
            val r: Int = resources.getIdentifier(enum.englishName, "id", packageName)
            findViewById<MyLayout>(r)?.let {

                if (enum == NeedBloodEnum.hospital_city_id || enum == NeedBloodEnum.hospital_area_id) {
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

                    val h: HashMap<NeedBloodEnum, MyLayout> = hashMapOf(enum to it)
                    formItems.add(h)
                } else {
                    val h: HashMap<NeedBloodEnum, MyLayout> = hashMapOf(enum to it)
                    formItems.add(h)
                }
            }

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

        for (formItem in formItems) {
            for ((enum, layout) in formItem) {
                if (layout.isEmpty()) {
                    msg += enum.errMsg()
                } else {
                    val k = enum.englishName
                    var v = layout.value
                    if (enum == NeedBloodEnum.type) {
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

    }
}