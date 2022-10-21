package tw.com.bluemobile.hbc.utilities

import android.content.res.Resources
import android.provider.ContactsContract
import android.widget.ImageView
import tw.com.bluemobile.hbc.extensions.setImage
import tw.com.bluemobile.hbc.views.EditTextNormal
import tw.com.bluemobile.hbc.views.MyLayout
import kotlin.reflect.KClass

enum class KeyEnum(val englishName: String, val chineseName: String) {
    city_id(CITY_ID_KEY, "縣市"),
    area_id(AREA_ID_KEY, "區域");

    companion object {
        fun enumFromString(value: String): KeyEnum {
            when (value) {
                CITY_ID_KEY -> return city_id
                AREA_ID_KEY -> return area_id
            }
            return city_id
        }
    }
}

enum class MemberHomeEnum(val englishName: String, val chineseName: String) {

    account("account", "帳戶資料"),
    pet("pet", "我的寶貝"),
    donate_blood("donate_blood", "我的捐血"),
    need_blood("need_blood", "我需要血"),
    reset_password("reset_password", "更改密碼"),
    bank_account("bank", "銀行帳號"),
    //credit_card("credit_card", "信用卡資料"),
    validate_email("validate_email", "信箱認證"),
    validate_mobile("validate_mobile", "手機認證"),
    refresh("refresh", "重新整理");

    companion object {
        fun enumFromString(value: String): MemberHomeEnum {
            when (value) {
                "account" -> return account
                "pet" ->return pet
                "need_blood" -> return need_blood
                "donate_blood" -> return donate_blood
                "bank_account" -> return bank_account
                "reset_password" -> return reset_password
                //"credit_card" -> return credit_card
                "validate_email" -> return validate_email
                "validate_mobile" -> return validate_mobile
                "refresh" -> return  refresh
            }
            return account
        }

        fun enumFromIdx(idx: Int): MemberHomeEnum {
            when (idx) {
                0 -> return account
                1 -> return pet
                2 -> return need_blood
                3 -> return donate_blood
                4 -> return bank_account
                5 -> return reset_password
                //5 -> return credit_card
                6 -> return validate_email
                7 -> return validate_mobile
                8 -> return  refresh
            }
            return account
        }

        fun getAllEnum(): Array<MemberHomeEnum> {
            return arrayOf(account, pet, donate_blood, need_blood, bank_account, reset_password, validate_email, validate_mobile, refresh)
        }
    }

    fun getIconID(resources: Resources, packageName: String): Int {
        return resources.getIdentifier("member_" + this.englishName, "drawable", packageName)
    }
}

enum class MemberPetEnum(val englishName: String, val chineseName: String) {

    petName("name", "名稱"),
    type("type", "品種"),
    age("age", "年齡"),
    weight("weight", "體重"),
    blood_type("blood_type", "血型"),
    IDo("IDo", "是否願意捐血"),
    traffic_fee("traffic_fee", "車馬費(每500公尺)"),
    nutrient_fee("nutrient_fee", "營養費(50CC共)"),
    blood_image("blood_image", "血檢資料"),
    body_image("body_image", "體檢資料");

    companion object {
        fun enumFromString(value: String): MemberPetEnum {
            return when (value) {
                "name" -> petName
                "type" -> type
                "age" -> age
                "weight" -> weight
                "blood_type" -> blood_type
                "IDo" -> IDo
                "traffic_fee" -> traffic_fee
                "nutrient_fee" -> nutrient_fee
                "blood_image" -> blood_image
                "body_image" -> body_image
                else -> petName
            }
        }

        fun getMustEnum(): ArrayList<MemberPetEnum> {
            return arrayListOf(petName, type, age, weight, blood_type, IDo)
        }

        fun getNonImageEnum(): ArrayList<MemberPetEnum> {
            return arrayListOf(petName, type, age, weight, blood_type, IDo, traffic_fee, nutrient_fee)
        }

        fun getAllEnum(): ArrayList<MemberPetEnum> {
            return arrayListOf(petName, type, age, weight, blood_type, IDo, traffic_fee, nutrient_fee, blood_image, body_image)
        }
    }

    fun radioTextToDBName(text: String): String {
        return when (text) {
            "貓" -> "cat"
            "狗" -> "dog"
            "願意" -> "1"
            "不願意" -> "0"

            else -> ""
        }
    }

    fun errMsg(): String {
        return when (this) {
            petName -> "請填名稱\n"
            type -> "請選擇品種\n"
            age -> "請填寫年齡\n"
            weight -> "請填重量\n"
            blood_type -> "請填寫血型\n"
            IDo -> "請選擇是否願意捐血\n"

            else -> ""
        }
    }

    fun getIcon(): String {
        return when (this) {
            traffic_fee -> "ic_fee"
            nutrient_fee -> "ic_fee"
            IDo -> "ic_blood_type"
            else -> "ic_${this.englishName}"
        }
    }

    fun getUnit(): String {
        return when (this) {
            petName -> ""
            age -> "歲"
            weight -> "公斤"
            blood_type -> "型"
            traffic_fee -> "元"
            nutrient_fee -> "元"
            else -> ""
        }
    }
}

enum class PasswordEnum(val englishName: String, val chineseName: String) {
    forget("forget", "忘記密碼"),
    reset("reset", "更改密碼"),
    none("none", "錯誤");

    companion object {
        fun enumFromString(value: String): PasswordEnum {
            return when (value) {
                "forget" -> forget
                "reset" -> reset
                else -> none
            }
        }
    }
}

enum class RegisterEnum(val englishName: String, val chineseName: String) {
    email(EMAIL_KEY, "Email"),
    password(PASSWORD_KEY, "密碼"),
    repassword(REPASSWORD_KEY, "確認密碼"),
    realname(NAME_KEY, "姓名"),
    nickname(NICKNAME_KEY, "暱稱"),
    city_id(CITY_ID_KEY, "縣市"),
    area_id(AREA_ID_KEY, "區域"),
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
                CITY_ID_KEY -> return city_id
                AREA_ID_KEY -> return area_id
                ROAD_KEY -> return road
                MOBILE_KEY -> return mobile
                LINE_KEY -> return line
                PRIVACY_KEY -> return privacy
                else -> return realname
            }
        }

        fun getRegisterAllEnum(): ArrayList<RegisterEnum> {
            return arrayListOf(email, password, repassword, realname, realname, nickname, city_id, area_id, road, mobile, line, privacy)
        }

        fun getUpdateAllEnum(): ArrayList<RegisterEnum> {
            return arrayListOf(email, realname, nickname, city_id, area_id, road, mobile, line)
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

    fun isUpdateHidden(): Boolean {
        val updateEnums: ArrayList<RegisterEnum> = getUpdateAllEnum()
        return !updateEnums.contains(this)
    }

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


enum class ValidateEnum(val englishName: String, val chineseName: String) {
    email("email", "信箱認證"),
    mobile("mobile", "手機認證"),
    none("none", "錯誤");

    companion object {
        fun enumFromString(value: String): ValidateEnum {
            return when (value) {
                "email" -> email
                "mobile" -> mobile
                else -> none
            }
        }
    }
}


