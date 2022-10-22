package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.setImage
import tw.com.bluemobile.hbc.models.MemberPetModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.IconText
import tw.com.bluemobile.hbc.views.MyLayout
import kotlin.comparisons.then
import kotlin.reflect.full.memberProperties

class MemberPetShowActivity : ShowActivity() {

    var memberPetToken: String? = null
    var memberPetModel: MemberPetModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_pet_show)

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
        toMemberPetEdit(this)
    }

    override fun edit() {
        toMemberPetEdit(this, memberPetToken)
    }

    override fun refresh() {

        super.refresh()
        val params: HashMap<String, String> = hashMapOf("member_pet_token" to memberPetToken!!)
        MemberService.postPetOne(this, params) { success ->
            if (success) {
                memberPetModel = parseJSON<MemberPetModel>(MemberService.jsonString)
                memberPetModel?.filterRow()
            }
        }
    }
}