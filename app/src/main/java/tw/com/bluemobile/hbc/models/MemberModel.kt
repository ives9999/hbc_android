package tw.com.bluemobile.hbc.models

import android.content.Context
import android.content.SharedPreferences
import com.bumptech.glide.load.model.Model
import com.google.gson.annotations.SerializedName
import tw.com.bluemobile.hbc.extensions.dump
import tw.com.bluemobile.hbc.member
import tw.com.bluemobile.hbc.utilities.*
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

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
    var privacy: Int = 0

    var type: Int = 0
    var role: String = ""
    var validate: Int = 0
    var player_id: String = ""

    var isLoggedIn: Boolean = false

    @SerializedName("zone") var zoneModel: ZoneModel? = null
    @SerializedName("bank") var bankModel: MemberBankModel? = null

    override fun filterRow() {
        super.filterRow()
        zoneModel?.filterRow()
    }

    private fun toBankSession(session: SharedPreferences, memberBankTable: MemberBankModel) {

        MemberBankModel::class.memberProperties.forEach {
            val name: String = it.name
            when (val value = it.getter.call(memberBankTable)) {
                is Int ->
                    session.edit().putInt(name, value).apply()
                is String ->
                    session.edit().putString(name, value).apply()
                is Boolean ->
                    session.edit().putBoolean(name, value).apply()
            }
        }
    }

    fun toSession(context: Context, isLoggedIn: Boolean = false) {

        filterRow()
        this.isLoggedIn = isLoggedIn
        val session: SharedPreferences = context.getSharedPreferences(SESSION_FILENAME, 0)
        this::class.memberProperties.forEach {
            val name: String = it.name
            when (val value = it.getter.call(this)) {
                is Int ->
                    session.edit().putInt(name, value).apply()
                is String ->
                    session.edit().putString(name, value).apply()
                is Boolean ->
                    session.edit().putBoolean(name, value).apply()
                is ZoneModel -> {
                    val zoneModel: ZoneModel = value as ZoneModel
                    toZoneSession(session, zoneModel)
                }
                is MemberBankModel -> {
                    val memberBankModel: MemberBankModel = value as MemberBankModel
                    toBankSession(session, memberBankModel)
                }
            }
        }
    }

    private fun toZoneSession(session: SharedPreferences, zoneModel: ZoneModel) {
        zoneModel::class.memberProperties.forEach {
            val name: String = it.name
            when (val value = it.getter.call(zoneModel)) {
                is Int ->
                    session.edit().putInt(name, value).apply()
                is String ->
                    session.edit().putString(name, value).apply()
                is Boolean ->
                    session.edit().putBoolean(name, value).apply()
            }
        }
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
        get() = session.getBoolean(ISLOGGEDIN_KEY, false)
        set(value) {
            session.edit().putBoolean(ISLOGGEDIN_KEY, value).apply()
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

    fun dump() {
        val session: SharedPreferences = context.getSharedPreferences(SESSION_FILENAME, 0)
        session.dump()
    }

    fun reset() {

        Member::class.memberProperties.forEach {

            val name = it.name
            val t = it.returnType.jvmErasure
            //val t1 = String::class

            //val b = it.returnType.jvmErasure.isSubclassOf(String::class)

            if (t == String::class) {
                session.edit().putString(name, "").apply()
            } else if (t == Int::class) {
                session.edit().putInt(name, 0).apply()
            } else if (t == Boolean::class) {
                session.edit().putBoolean(name, false).apply()
            }
            session.edit().putString("dob", "").apply()
            session.edit().putInt("cartItemCount", 0).apply()
        }
        //member.dump()
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

