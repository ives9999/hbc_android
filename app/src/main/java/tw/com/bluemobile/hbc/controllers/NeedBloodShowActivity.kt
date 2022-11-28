package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.ImageView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.setImage
import tw.com.bluemobile.hbc.models.NeedBloodModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.NeedBloodService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.IconText
import java.lang.reflect.Type

class NeedBloodShowActivity : ShowActivity() {

    var needBloodToken: String? = null
    var needBloodModel: NeedBloodModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_need_blood_show)

        if (intent.hasExtra("needBloodToken")) {
            needBloodToken = intent.getStringExtra("needBloodToken")
        }

        if (needBloodToken == null) {

        }

        setTop(true, "我的寶貝")

        refresh()
    }

    override fun init() {
        super.init()

        top!!.setTitle(needBloodModel!!.name)
        top!!.showAdd(true)
        top!!.showEdit(true)

        val allEnums: ArrayList<NeedBloodEnum> = NeedBloodEnum.getAllEnum()
        for (enum in allEnums) {

            val value: String = getPropertyValue(needBloodModel!!, enum.englishName)

            if (enum == NeedBloodEnum.type) {
                findViewById<ImageView>(R.id.type)?.let {
                    it.setImage("ic_${value}")
                }
            } else {
                val r: Int = resources.getIdentifier(enum.englishName, "id", packageName)
                findViewById<IconText>(r) ?. let {
                    it.setIconIV(enum.getIcon())
                    it.setTitleTV("${enum.chineseName}：")
                    it.setValueTV(value)
                    it.setUnitTV(enum.getUnit())
                }
            }
        }
    }

    override fun add() {
        super.add()
        toNeedBloodEdit(this)
    }

    override fun edit() {
        toNeedBloodEdit(this, needBloodToken)
    }

    override fun refresh() {

        super.refresh()
        val params: HashMap<String, String> = hashMapOf("token" to needBloodToken!!)
        NeedBloodService.getOne(this, params) { success ->
            if (success) {
                //println(DonateBloodService.jsonString)
                val modelType: Type = genericType<SuccessModel<NeedBloodModel>>()
                needBloodModel = parseJSONAndInit<NeedBloodModel>(NeedBloodService.jsonString, modelType)
            }
        }
    }
}