package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.parseErrmsg
import tw.com.bluemobile.hbc.extensions.setImage
import tw.com.bluemobile.hbc.models.BaseModels
import tw.com.bluemobile.hbc.models.MemberModel
import tw.com.bluemobile.hbc.models.MemberPetModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.DonateBloodService
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.IconText
import java.lang.Exception
import java.lang.reflect.Type

class DonateBloodShowActivity : ShowActivity() {

    var memberPetToken: String? = null
    var memberPetModel: MemberPetModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_blood_show)

        if (intent.hasExtra("memberPetToken")) {
            memberPetToken = intent.getStringExtra("memberPetToken")
        }

        if (memberPetToken == null) {

        }

        setTop(true, "我的寶貝")

        refresh()
    }

    override fun init() {
        super.init()

        top!!.setTitle(memberPetModel!!.name)
        top!!.showAdd(true)
        top!!.showEdit(true)

        val allEnums: ArrayList<MemberPetEnum> = MemberPetEnum.getAllEnum()
        for (enum in allEnums) {

            val value: String = getPropertyValue(memberPetModel!!, enum.englishName)

            if (enum == MemberPetEnum.type) {
                findViewById<ImageView>(R.id.type)?.let {
                    it.setImage("ic_${value}")
                }
            } else if (enum == MemberPetEnum.IDo) {
                val r: Int = resources.getIdentifier(enum.englishName, "id", packageName)
                findViewById<IconText>(r)?.let {
                    it.setIconIV(enum.getIcon())
                    it.setTitleTV("${enum.chineseName}：")
                    it.setValueTV(((value == "1") then { "願意" }) ?: "不願意")
                    it.setUnitTV(enum.getUnit())
                }
            } else if (enum == MemberPetEnum.blood_image) {
                findViewById<ImageView>(R.id.blood_imageIV) ?. let {
                    if (memberPetModel?.blood_image != null) {
                        Picasso.with(this)
                            .load(memberPetModel?.blood_image)
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .into(it)
                    }

                }
            } else if (enum == MemberPetEnum.body_image) {
                findViewById<ImageView>(R.id.body_imageIV) ?. let {
                    if (memberPetModel?.body_image != null) {
                        Picasso.with(this)
                            .load(memberPetModel?.body_image)
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
        toDonateBloodEdit(this, memberPetToken)
    }

    override fun refresh() {

        super.refresh()
        val params: HashMap<String, String> = hashMapOf("token" to memberPetToken!!)
        DonateBloodService.getOne(this, params) { success ->
            if (success) {
                //println(DonateBloodService.jsonString)
                val modelType: Type = genericType<SuccessModel<MemberPetModel>>()
                memberPetModel = parseJSONAndInit<MemberPetModel>(DonateBloodService.jsonString, modelType)
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