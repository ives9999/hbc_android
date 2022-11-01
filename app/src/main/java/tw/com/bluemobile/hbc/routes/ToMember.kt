package tw.com.bluemobile.hbc.routes

import android.content.Intent
import tw.com.bluemobile.hbc.controllers.*
import tw.com.bluemobile.hbc.utilities.PasswordEnum
import tw.com.bluemobile.hbc.utilities.ValidateEnum

interface ToMember {

    fun toBankAccount(activity: BaseActivity) {
        val i = Intent(activity, BankAccountActivity::class.java)
        activity.startActivity(i)
    }

    fun toLogin(activity: BaseActivity) {
        val i = Intent(activity, LoginActivity::class.java)
        activity.startActivity(i)
    }

    fun toMemberHome(activity: BaseActivity) {
        val i = Intent(activity, MemberActivity::class.java)
        activity.startActivity(i)
    }

//    fun toMemberPetEdit(activity: BaseActivity, token: String? = null) {
//        val i = Intent(activity, MemberPetEditActivity::class.java)
//        i.putExtra("memberPetToken", token)
//        activity.memberPetEditAR.launch(i)
//    }

//    fun toMemberPetList(activity: BaseActivity) {
//        val i = Intent(activity, MemberPetListActivity::class.java)
//        activity.startActivity(i)
//    }

//    fun toMemberPetShhow(activity: BaseActivity, token: String) {
//        val i = Intent(activity, DonateBloodShowActivity::class.java)
//        i.putExtra("memberPetToken", token)
//        activity.startActivity(i)
//    }

    fun toPassword(activity: BaseActivity, type: PasswordEnum) {
        val i = Intent(activity, PasswordActivity::class.java)
        i.putExtra("type", type.englishName)
        activity.startActivity(i)
    }

    fun toRegister(activity: BaseActivity) {
        val i = Intent(activity, RegisterActivity::class.java)
        activity.startActivity(i)
    }

    fun toValidate(activity: MemberActivity, type: ValidateEnum) {
        val i = Intent(activity, ValidateActivity::class.java)
        i.putExtra("type", type.englishName)
        activity.validateAR.launch(i)
    }
}















