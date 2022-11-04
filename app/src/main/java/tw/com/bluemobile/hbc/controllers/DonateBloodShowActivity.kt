package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.setImage
import tw.com.bluemobile.hbc.models.DonateBloodModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.DonateBloodService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.IconText
import java.lang.reflect.Type

class DonateBloodShowActivity : ShowActivity() {

    var donateBloodToken: String? = null
    var donateBloodModel: DonateBloodModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_blood_show)

        if (intent.hasExtra("donateBloodToken")) {
            donateBloodToken = intent.getStringExtra("donateBloodToken")
        }

        if (donateBloodToken == null) {

        }

        setTop(true, "我的寶貝")

        refresh()
    }

    override fun init() {
        super.init()

        top!!.setTitle(donateBloodModel!!.name)
        top!!.showAdd(true)
        top!!.showEdit(true)

        val allEnums: ArrayList<DonateBloodEnum> = DonateBloodEnum.getAllEnum()
        for (enum in allEnums) {

            val value: String = getPropertyValue(donateBloodModel!!, enum.englishName)

            if (enum == DonateBloodEnum.type) {
                findViewById<ImageView>(R.id.type)?.let {
                    it.setImage("ic_${value}")
                }
            } else if (enum == DonateBloodEnum.IDo) {
                val r: Int = resources.getIdentifier(enum.englishName, "id", packageName)
                findViewById<IconText>(r)?.let {
                    it.setIconIV(enum.getIcon())
                    it.setTitleTV("${enum.chineseName}：")
                    it.setValueTV(((value == "1") then { "願意" }) ?: "不願意")
                    it.setUnitTV(enum.getUnit())
                }
            } else if (enum == DonateBloodEnum.blood_image) {
                findViewById<ImageView>(R.id.blood_imageIV) ?. let {
                    if (donateBloodModel?.blood_image != null) {
                        Picasso.with(this)
                            .load(donateBloodModel?.blood_image)
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .into(it)
                    }

                }
            } else if (enum == DonateBloodEnum.body_image) {
                findViewById<ImageView>(R.id.body_imageIV) ?. let {
                    if (donateBloodModel?.body_image != null) {
                        Picasso.with(this)
                            .load(donateBloodModel?.body_image)
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .into(it)
                    }

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
        toDonateBloodEdit(this)
    }

    override fun edit() {
        toDonateBloodEdit(this, donateBloodToken)
    }

    override fun refresh() {

        super.refresh()
        val params: HashMap<String, String> = hashMapOf("token" to donateBloodToken!!)
        DonateBloodService.getOne(this, params) { success ->
            if (success) {
                //println(DonateBloodService.jsonString)
                val modelType: Type = genericType<SuccessModel<DonateBloodModel>>()
                donateBloodModel = parseJSONAndInit<DonateBloodModel>(DonateBloodService.jsonString, modelType)
//                if (memberPetModel != null) {
//                    runOnUiThread {
//                        init()
//                        loading.hide()
//                    }
//                } else {
//                    runOnUiThread {
//                        warning(errMsg)
//                    }
//                }

//                parseJSON<SuccessModel<MemberPetModel>>(DonateBloodService.jsonString, modelType)
//                val successModel = jsonToModelForOne<SuccessModel<MemberPetModel>>(DonateBloodService.jsonString, modelType)
//                if (successModel != null && successModel.success) {
//                    memberPetModel = successModel.model
//                    memberPetModel?.filterRow()
//                    runOnUiThread {
//                        init()
//                        loading.hide()
//                    }
//                } else {
//                    runOnUiThread {
//                        warning(successModel!!.msgs.parseErrmsg())
//                    }
//                }
            }
        }
    }
}