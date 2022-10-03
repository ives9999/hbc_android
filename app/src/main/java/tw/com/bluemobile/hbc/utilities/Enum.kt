package tw.com.bluemobile.hbc.utilities

import android.content.res.Resources
import android.provider.ContactsContract
import android.widget.ImageView
import tw.com.bluemobile.hbc.extensions.setImage
import tw.com.bluemobile.hbc.views.EditTextNormal
import tw.com.bluemobile.hbc.views.MyLayout
import kotlin.reflect.KClass

enum class KeyEnum(val englishName: String, val chineseName: String) {
    city_id(CITY_KEY, "縣市"),
    area_id(AREA_KEY, "區域");

    companion object {
        fun enumFromString(value: String): KeyEnum {
            when (value) {
                CITY_KEY -> return city_id
                AREA_KEY -> return area_id
            }
            return city_id
        }
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

enum class RegisterEnum(val englishName: String, val chineseName: String) {
    email(EMAIL_KEY, "Email"),
    password(PASSWORD_KEY, "密碼"),
    repassword(REPASSWORD_KEY, "確認密碼"),
    realname(NAME_KEY, "姓名"),
    nickname(NICKNAME_KEY, "暱稱"),
    city_id(CITY_KEY, "縣市"),
    area_id(AREA_KEY, "區域"),
    road(ROAD_KEY, "路、街，巷"),
    mobile(MOBILE_KEY, "行動電話"),
    line(LINE_KEY, "line"),
    privacy(PRIVACY_KEY, "同意隱私權");

    companion object {
        fun enumFromString(value: String): RegisterEnum {
            when (value) {
                EMAIL_KEY -> return email
                PASSWORD_KEY -> return password
                REPASSWORD_KEY -> return repassword
                NAME_KEY -> return realname
                NICKNAME_KEY -> return nickname
                CITY_KEY -> return city_id
                AREA_KEY -> return area_id
                ROAD_KEY -> return road
                MOBILE_KEY -> return mobile
                LINE_KEY -> return line
                PRIVACY_KEY -> return privacy
                else -> return realname
            }
        }

        fun getRegisterAllEnum(): Array<RegisterEnum> {
            return arrayOf(email, password, realname, realname, nickname, city_id, area_id, road, mobile, line, privacy)
        }

        fun getUpdateAllEnum(): Array<RegisterEnum> {
            return arrayOf(email, realname, nickname, city_id, area_id, road, mobile, line)
        }
    }

    fun errMsg(): String {
        when (this) {
            email -> return "請填郵件\n"
            password -> return "請填密碼\n"
            repassword -> return "請填確認密碼\n"
            realname -> return "請填真實姓名\n"
            nickname -> return "請填暱稱\n"
            city_id -> return "請選擇縣市\n"
            area_id -> return "請選擇區域\n"
            road -> return "請填路名\n"
            mobile -> return "請填行動電話\n"
            line -> return "請填line\n"
            privacy -> return "請同意隱私權條款\n"

            else -> return ""
        }
    }

//    fun getClassType(): KClass<EditTextNormal> {
//        when (this) {
//            email -> return EditTextNormal::class
//            password -> return "請填密碼\n"
//            repassword -> return "請填確認密碼\n"
//            realname -> return "請填真實姓名\n"
//            nickname -> return "請填暱稱\n"
//            city_id -> return "請選擇縣市\n"
//            area_id -> return "請選擇區域\n"
//            road -> return "請填路名\n"
//            mobile -> return "請填行動電話\n"
//            line -> return "請填line\n"
//            privacy -> return "請同意隱私權條款\n"
//
//            else -> return ""
//        }
//    }

    fun getLayout(resources: Resources, packageName: String) {
        var r: Int = resources.getIdentifier(this.englishName, "id", packageName)
    }
}

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

