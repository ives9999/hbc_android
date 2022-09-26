package tw.com.bluemobile.hbc.utilities

import android.content.res.Resources
import android.widget.ImageView
import tw.com.bluemobile.hbc.extensions.setImage

enum class TabEnum(val englishName: String, val chineseName: String) {
    
    need_blood("need_blood", "我需要血"),
    donate_blood("donate_blood", "我要捐血"),
    donate("donate", "我要捐款"),
    member("member", "我的資訊");

    companion object {
        fun enumFromString(value: String): TabEnum {
            when (value) {
                "need_blood" -> return need_blood
                "donate_blood" -> return donate_blood
                "donate" -> return donate
                "member" -> return member
            }
            return member
        }

        fun getAllEnum(): Array<TabEnum> {
            return arrayOf(need_blood, donate_blood, donate, member)
        }
    }

    fun getIconID(resources: Resources, packageName: String): Int {
        return resources.getIdentifier(this.englishName + "TabIcon", "id", packageName)
    }

    fun setIn(it: ImageView) {
        it.setImage(this.englishName + "_in")
    }
}

enum class MemberHomeEnum(val englishName: String, val chineseName: String) {

    account("account", "帳戶資料"),
    change_password("change_password", "更改密碼"),
    donate_blood("donate_blood", "我的捐血"),
    need_blood("need_blood", "我需要血"),
    bank("bank", "銀行帳號"),
    credit_card("credit_card", "信用卡資料"),
    validate_email("validate_email", "信箱認證"),
    validate_mobile("validate_mobile", "手機認證"),
    refresh("refresh", "重新整理");

    companion object {
        fun enumFromString(value: String): MemberHomeEnum {
            when (value) {
                "account" -> return account
                "change_password" -> return change_password
                "need_blood" -> return need_blood
                "donate_blood" -> return donate_blood
                "bank" -> return bank
                "credit_card" -> return credit_card
                "validate_email" -> return validate_email
                "validate_mobile" -> return validate_mobile
                "refresh" -> return  refresh
            }
            return account
        }

        fun getAllEnum(): Array<MemberHomeEnum> {
            return arrayOf(account, change_password, donate_blood, need_blood, bank, credit_card, validate_email, validate_mobile, refresh)
        }
    }

    fun getIconID(resources: Resources, packageName: String): Int {
        return resources.getIdentifier("member_" + this.englishName, "drawable", packageName)
    }

}