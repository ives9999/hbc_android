package tw.com.bluemobile.hbc.utilities

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tw.com.bluemobile.hbc.models.BaseModel
import tw.com.bluemobile.hbc.models.MemberModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.MemberService
import java.lang.Exception
import java.lang.reflect.Type

infix fun <T: Any?> Boolean?.then(block: () -> T): T? = if (this == true) block() else null

inline fun <reified U> genericType(): Type = object : TypeToken<U>() {}.type

inline fun <reified U> jsonToModel(jsonString: String): U? {
    var u: U? = null
    try {
        val modelType: Type = genericType<U>()
        u = Gson().fromJson<U>(jsonString, modelType)
    } catch (e: Exception) {
        println(e.localizedMessage)
    }

    return u
}

inline fun <reified U> jsonToModelForList(jsonString: String, modelType: Type): U? {
    var u: U? = null
    try {
        u = Gson().fromJson<U>(jsonString, modelType)
    } catch (e: Exception) {
        println(e.localizedMessage)
    }

    return u
}

inline fun <reified T> jsonToModelForOne(jsonString: String): T? {

    var t: T? = null
    try {
        t = Gson().fromJson<T>(jsonString, T::class.java)
    } catch (e: java.lang.Exception) {
        //Global.message = e.localizedMessage
        println(e.localizedMessage)
    }

    return t
}

infix fun Map<String, String>.mergeWith(anotherMap: Map<String, String>): Map<String, String> {
    val result = this.toMutableMap()
    anotherMap.forEach {
        var value = result[it.key]
        value = if (value == null || value == it.value) it.value else value + ", ${it.value}"
        result[it.key] = value
    }
    return result
}

infix fun HashMap<String, String>.mergeWith(anotherMap: HashMap<String, String>): HashMap<String, String> {
    val mutableMap: MutableMap<String, String> = this.toMutableMap()
    anotherMap.forEach {
        var value = mutableMap[it.key]
        value = if (value == null || value == it.value) it.value else value + ", ${it.value}"
        mutableMap[it.key] = value
    }
    return HashMap(mutableMap)
}


