package tw.com.bluemobile.hbc.models

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.annotations.SerializedName
import tw.com.bluemobile.hbc.member
import tw.com.bluemobile.hbc.utilities.*
import kotlin.reflect.full.createType
import kotlin.reflect.full.memberProperties

class MemberModel: BaseModel() {

    var email: String = ""
    var nickname: String = ""
    var locale: String = ""
    var dob: String = ""
    var sex: String = ""
    var pid: String = ""
    var line: String = ""
    var area_id: Int = 0
    var zip: Int = 0
    var road: String = ""
    var avatar: String = ""
    var privacy: Int = 0

    var type: Int = 0
    var role: String = ""
    var validate: Int = 0
    var player_id: String = ""

    @SerializedName("zone") var zoneModel: ZoneModel? = null

    override fun filterRow() {
        super.filterRow()
        zoneModel?.filterRow()
    }
}

/**
 * Created by ivessun on 2018/2/5.
 */
class Member(val context: Context) {

    val session: SharedPreferences = context.getSharedPreferences(SESSION_FILENAME, 0)

    var id: Int
        get() = session.getInt(ID_KEY, 0)
        set(value) {
            session.edit().putInt(ID_KEY, value).apply()
        }
    var nickname: String?
        get() = session.getString(NICKNAME_KEY, "")
        set(value) {
            session.edit().putString(NICKNAME_KEY, value).apply()
        }
    var name: String?
        get() = session.getString(NAME_KEY, "")
        set(value) {
            session.edit().putString(NAME_KEY, value).apply()
        }
    var local: String?
        get() = session.getString(LOCALE_KEY, "bm")
        set(value) {
            session.edit().putString(LOCALE_KEY, value).apply()
        }
    var dob: String?
        get() = session.getString(DOB_KEY, "")
        set(value) {
            session.edit().putString(DOB_KEY, value).apply()
        }
    var sex: String?
        get() = session.getString(SEX_KEY, "")
        set(value) {
            session.edit().putString(SEX_KEY, value).apply()
        }
    var mobile: String?
        get() = session.getString(MOBILE_KEY, "")
        set(value) {
            session.edit().putString(MOBILE_KEY, value).apply()
        }
    var email: String?
        get() = session.getString(EMAIL_KEY, "")
        set(value) {
            session.edit().putString(EMAIL_KEY, value).apply()
        }
    var city_id: Int
        get() = session.getInt(CITY_ID_KEY, 0)
        set(value) {
            session.edit().putInt(CITY_ID_KEY, value).apply()
        }
    var area_id: Int
        get() = session.getInt(AREA_ID_KEY, 0)
        set(value) {
            session.edit().putInt(AREA_ID_KEY, value).apply()
        }
    var road: String?
        get() = session.getString(ROAD_KEY, "")
        set(value) {
            session.edit().putString(ROAD_KEY, value).apply()
        }
    var zip: Int
        get() = session.getInt(ZIP_KEY, 0)
        set(value) {
            session.edit().putInt(ZIP_KEY, value).apply()
        }
    var address: String?
        get() = session.getString(ADDRESS_KEY, "")
        set(value) {
            session.edit().putString(ADDRESS_KEY, value).apply()
        }
    var line: String?
        get() = session.getString(LINE_KEY, "")
        set(value) {
            session.edit().putString(LINE_KEY, value).apply()
        }
    var pid: String?
        get() = session.getString(PID_KEY, "")
        set(value) {
            session.edit().putString(PID_KEY, value).apply()
        }
    var featured: String?
        get() = session.getString(FEATURED_KEY, "")
        set(value) {
            session.edit().putString(FEATURED_KEY, value).apply()
        }
    var player_id: String?
        get() = session.getString(PLAYER_ID_KEY, "")
        set(value) {
            session.edit().putString(PLAYER_ID_KEY, value).apply()
        }
    var type: Int
        get() = session.getInt(TYPE_KEY, 0)
        set(value) {
            session.edit().putInt(TYPE_KEY, value).apply()
        }
    var role: String?
        get() = session.getString(ROLE_KEY, "member")
        set(value) {
            session.edit().putString(ROLE_KEY, value).apply()
        }
    var validate: Int
        get() = session.getInt(VALIDATE_KEY, 0)
        set(value) {
            session.edit().putInt(VALIDATE_KEY, value).apply()
        }
    var token: String?
        get() = session.getString(TOKEN_KEY, "")
        set(value) {
            session.edit().putString(TOKEN_KEY, value).apply()
        }
    var isLoggedIn: Boolean
        get() = session.getBoolean(ISLOGIN_KEY, false)
        set(value) {
            session.edit().putBoolean(ISLOGIN_KEY, value).apply()
        }
    var bank: String?
        get() = session.getString(BANK_KEY, "")
        set(value) {
            session.edit().putString(BANK_KEY, value).apply()
        }
    var branch: String?
        get() = session.getString(BRANCH_KEY, "")
        set(value) {
            session.edit().putString(BRANCH_KEY, value).apply()
        }
    var bank_code: Int?
        get() = session.getInt(BANK_CODE_KEY, 0)
        set(value) {
            if (value != null) {
                session.edit().putInt(BANK_CODE_KEY, value).apply()
            }
        }
    var bank_account: String?
        get() = session.getString(BANK_ACCOUNT_KEY, "")
        set(value) {
            session.edit().putString(BANK_ACCOUNT_KEY, value).apply()
        }

    fun reset() {

        MemberModel::class.memberProperties.forEach {

            val name = it.name
            val t = it.returnType
            if (t == String::class.createType()) {
                session.edit().putString(name, "").apply()
            } else if (t == Int::class.createType()) {
                session.edit().putInt(name, 0).apply()
            } else if (t == Boolean::class.createType()) {
                session.edit().putBoolean(name, false).apply()
            }
            session.edit().putString("dob", "").apply()
            session.edit().putInt("cartItemCount", 0).apply()
        }
    }



    fun checkEMailValidate(): Boolean {

        var isValidate: Boolean = false

        if (member.validate and 1 > 0) {
            isValidate = true
        }

        return isValidate
    }

    fun checkMobileValidate(): Boolean {

        var isValidate: Boolean = false

        if (member.validate and 2 > 0) {
            isValidate = true
        }

        return isValidate
    }
}