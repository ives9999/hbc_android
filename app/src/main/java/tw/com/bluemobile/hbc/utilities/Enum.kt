package tw.com.bluemobile.hbc.utilities

import android.content.res.Resources
import android.widget.ImageView
import tw.com.bluemobile.hbc.extensions.setImage

enum class BloodProcessEnum(val englishName: String, val chineseName: String) {

    send_information("send_information", "發出通知"),
    arrive_hospitalA("arrive_hospitalA", "需血方到達醫院"),
    arrive_hospitalB("arrive_hospitalB", "捐血方到達醫院"),
    meet("meet", "雙方見面"),
    traffic_feeA("traffic_feeA", "需血方發出車馬費"),
    traffic_feeP("traffic_feeP", "平台收到並發出車馬費"),
    traffic_feeB("traffic_feeB", "捐血方收到車馬費"),
    pair("pair","配對成功"),
    nutrient_feeA("nutrient_feeA", "需血方發出營養金"),
    nutrient_feeP("nutrient_feeP", "平台收到並發出營養金"),
    nutrient_feeB("nutrient_feeB", "捐血方收到營養金"),
    complete("complete", "完成");

    companion object {
        fun getAllEnum(): ArrayList<BloodProcessEnum> {
            return arrayListOf(
                send_information,
                arrive_hospitalA,
                arrive_hospitalB,
                meet,
                traffic_feeA,
                traffic_feeP,
                traffic_feeB,
                pair,
                nutrient_feeA,
                nutrient_feeP,
                nutrient_feeB,
                complete
            )
        }
    }
}

enum class DonateEnum(val englishName: String, val chineseName: String) {
    amount(AMOUNT_KEY, "金額"),
    credit_card_no(CREDIT_CARD_NO, "信用卡號"),
    credit_card_my(CREDIT_CARD_MY, "信用卡到期月年"),
    credit_card_cvv(CREDIT_CARD_CVV, "信用卡安全碼"),
    realname(NAME_KEY, "姓名"),
    tax_no(TAX_NO_KEY, "公司統編"),
    tel(TEL_KEY, "聯絡電話"),
    email(EMAIL_KEY, "Email"),
    city_id(CITY_ID_KEY, "縣市"),
    area_id(AREA_ID_KEY, "區域"),
    road(ROAD_KEY, "路、街，巷"),
    receipt(RECEIPT_KEY, "是否寄送收據");

    companion object {
        fun enumFromString(value: String): DonateEnum {
            when (value) {
                AMOUNT_KEY -> return amount
                CREDIT_CARD_NO -> return credit_card_no
                CREDIT_CARD_MY -> return credit_card_my
                CREDIT_CARD_CVV -> return credit_card_cvv
                NAME_KEY -> return realname
                TAX_NO_KEY -> return tax_no
                EMAIL_KEY -> return email
                TEL_KEY -> return tel
                CITY_ID_KEY -> return city_id
                AREA_ID_KEY -> return area_id
                ROAD_KEY -> return road
                RECEIPT_KEY -> return receipt
                else -> return realname
            }
        }

        fun getAllEnum(): ArrayList<DonateEnum> {
            return arrayListOf(amount, credit_card_no, credit_card_my, credit_card_cvv, realname, tax_no, email, tel, city_id, area_id, road, receipt)
        }

        fun getMustEnum(): ArrayList<DonateEnum> {
            return arrayListOf(
                amount,
                credit_card_no,
                credit_card_my,
                credit_card_no,
                realname,
                tel,
                email,
                receipt
            )
        }
    }

    fun radioTextToDBName(text: String): String {
        return when (text) {
            "是" -> "1"
            "否" -> "0"

            else -> text
        }
    }

    fun DBNameToRadioText(text: String): String {
        return when (text) {
            "1" -> "是"
            "0" -> "否"

            else -> text
        }
    }

    fun errMsg(): String {
        when (this) {
            amount -> return "請填捐款金額\n"
            credit_card_no -> return "請填信用卡號\n"
            credit_card_my -> return "請填信用到期月年\n"
            credit_card_cvv -> return "請填信用卡安全碼\n"
            realname -> return "請填真實姓名\n"
            email -> return "請填郵件\n"
            city_id -> return "請選擇縣市\n"
            area_id -> return "請選擇區域\n"
            road -> return "請填路名\n"
            tel -> return "請填聯絡電話\n"
            receipt -> return "請選擇是否寄送收據\n"

            else -> return ""
        }
    }

    fun getLayout(resources: Resources, packageName: String) {
        var r: Int = resources.getIdentifier(this.englishName, "id", packageName)
    }
}

enum class DonateBloodEnum(val englishName: String, val chineseName: String) {

    petName("name", "寶貝名稱"),
    type("type", "寶貝品種"),
    blood_type("blood_type", "寶貝血型"),
    blood_type_cat("blood_type_cat", "貓血型"),
    blood_type_dog("blood_type_dog", "狗血型"),
    age("age", "年齡"),
    weight("weight", "體重"),
    IDo("IDo", "是否願意捐血"),
    traffic_fee("traffic_fee", "車馬費(每500公尺)"),
    nutrient_fee("nutrient_fee", "營養費(50CC共)"),
    blood_image("blood_image", "血檢資料"),
    body_image("body_image", "體檢資料");

    companion object {
        fun enumFromString(value: String): DonateBloodEnum {
            return when (value) {
                "name" -> petName
                "type" -> type
                "blood_type" -> blood_type
                "blood_type_cat" -> blood_type_cat
                "blood_type_dog" -> blood_type_dog
                "age" -> age
                "weight" -> weight
                "IDo" -> IDo
                "traffic_fee" -> traffic_fee
                "nutrient_fee" -> nutrient_fee
                "blood_image" -> blood_image
                "body_image" -> body_image
                else -> petName
            }
        }

        fun getMustEnum(): ArrayList<DonateBloodEnum> {
            return arrayListOf(petName, type, age, weight, blood_type_cat, blood_type_dog, IDo)
        }

        fun getNonImageEnum(): ArrayList<DonateBloodEnum> {
            return arrayListOf(petName, type, age, weight, blood_type_cat, blood_type_dog, IDo, traffic_fee, nutrient_fee)
        }

        fun getAllEnum(): ArrayList<DonateBloodEnum> {
            return arrayListOf(petName, type, age, weight, blood_type_cat, blood_type_dog, blood_type, IDo, traffic_fee, nutrient_fee, blood_image, body_image)
        }
    }

    fun radioTextToDBName(text: String): String {
        return when (text) {
            "貓" -> "cat"
            "狗" -> "dog"
            "願意" -> "1"
            "不願意" -> "0"

            else -> text
        }
    }

    fun DBNameToRadioText(text: String): String {
        return when (text) {
            "cat" -> "貓"
            "dog" -> "狗"
            "1" -> "願意"
            "0" -> "不願意"

            else -> text
        }
    }

    fun errMsg(): String {
        return when (this) {
            petName -> "請填名稱\n"
            type -> "請選擇品種\n"
            age -> "請填寫年齡\n"
            weight -> "請填重量\n"
            blood_type_cat -> "請選擇血型\n"
            blood_type_dog -> "請選擇血型\n"
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
            blood_type_cat -> "貓血型\n"
            blood_type_dog -> "狗血型\n"
            traffic_fee -> "元"
            nutrient_fee -> "元"
            else -> ""
        }
    }
}

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
    delete("delete", "刪除會員"),
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
                "delete" -> return delete
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
                8 -> return delete
                9 -> return  refresh
            }
            return account
        }

        fun getAllEnum(): Array<MemberHomeEnum> {
            return arrayOf(account, pet, donate_blood, need_blood, bank_account, reset_password, validate_email, validate_mobile, delete, refresh)
        }
    }

    fun getIconID(resources: Resources, packageName: String): Int {
        return resources.getIdentifier("member_" + this.englishName, "drawable", packageName)
    }
}

enum class NeedBloodEnum(val englishName: String, val chineseName: String) {

    hospital_name("hospital_name", "醫院名稱"),
    hospital_city_id("hospital_city_id", "醫院縣市"),
    hospital_area_id("hospital_area_id", "醫院區域"),
    hospital_road("hospital_road", "醫院住址"),
    petName("name", "寶貝名稱"),
    type("type", "寶貝類型"),
    blood_type("blood_type", "寶貝血型"),
    blood_type_cat("blood_type_cat", "寶貝貓血型"),
    blood_type_dog("blood_type_dog", "寶貝狗血型"),
    traffic_fee("traffic_fee", "車馬費"),
    nutrient_fee("nutrient_fee", "營養費");

    companion object {
        fun enumFromString(value: String): NeedBloodEnum {
            return when (value) {
                "hospital_name" -> hospital_name
                "hospital_city_id" -> hospital_city_id
                "hospital_area_id" -> hospital_area_id
                "hospital_road" -> hospital_road
                "name" -> petName
                "type" -> type
                "blood_type" -> blood_type
                "blood_type_cat" -> blood_type_cat
                "blood_type_dog" -> blood_type_dog
                "traffic_fee" -> traffic_fee
                "nutrient_fee" -> nutrient_fee
                else -> hospital_name
            }
        }

        fun getAllEnum(): ArrayList<NeedBloodEnum> {
            return arrayListOf(
                hospital_name,
                hospital_city_id,
                hospital_area_id,
                hospital_road,
                petName,
                type,
                blood_type,
                traffic_fee,
                nutrient_fee
            )
        }
    }

    fun radioTextToDBName(text: String): String {
        return when (text) {
            "貓" -> "cat"
            "狗" -> "dog"

            else -> text
        }
    }

    fun DBNameToRadioText(text: String): String {
        return when (text) {
            "cat" -> "貓"
            "dog" -> "狗"

            else -> text
        }
    }

    fun errMsg(): String {
        return when (this) {
            hospital_name -> "請填醫院名稱\n"
            hospital_city_id -> "請選擇縣市\n"
            hospital_area_id -> "請選擇區域\n"
            hospital_road -> "請填寫路名等\n"
            petName -> "請選擇寶貝名稱\n"
            type -> "請選擇品種\n"
            blood_type -> "請選擇血型\n"
            blood_type_cat -> "請選擇血型\n"
            blood_type_dog -> "請選擇血型\n"
            traffic_fee -> "請填寫車馬費\n"
            nutrient_fee -> "請填寫營養費\n"

            else -> ""
        }
    }

    fun getIcon(): String {
        return when (this) {
            blood_type -> "ic_blood_type"
            traffic_fee -> "ic_fee"
            nutrient_fee -> "ic_fee"
            hospital_name -> "ic_hospital"
            hospital_road -> "ic_hospital"
            else -> "ic_${this.englishName}"
        }
    }

    fun getUnit(): String {
        return when (this) {
            petName -> ""
            blood_type -> "血型\n"
            blood_type_cat -> "貓血型\n"
            blood_type_dog -> "狗血型\n"
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
            return arrayListOf(email, password, repassword, realname, nickname, city_id, area_id, road, mobile, line, privacy)
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


