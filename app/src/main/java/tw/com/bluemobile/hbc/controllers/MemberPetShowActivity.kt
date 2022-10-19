package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.models.MemberPetModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.jsonToModelForOne

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
    }

    override fun refresh() {

        super.refresh()
        val params: HashMap<String, String> = hashMapOf("member_pet_token" to memberPetToken!!)
        MemberService.postPetOne(this, params) { success ->
            if (success) {
                //println(MemberService.jsonString)
                memberPetModel = jsonToModelForOne<MemberPetModel>(MemberService.jsonString)
                runOnUiThread {
                    init()
                }
            }
            loading.hide()
        }
    }
}