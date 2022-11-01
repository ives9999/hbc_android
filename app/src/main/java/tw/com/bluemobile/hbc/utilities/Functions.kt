package tw.com.bluemobile.hbc.utilities

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tw.com.bluemobile.hbc.models.BaseModel
import tw.com.bluemobile.hbc.models.MemberModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.MemberService
import java.lang.Exception
import java.lang.reflect.Type
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

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

inline fun <reified U> jsonToModelForOne(jsonString: String): U? {

    var u: U? = null
    try {
        u = Gson().fromJson<U>(jsonString, U::class.java)
    } catch (e: java.lang.Exception) {
        //Global.message = e.localizedMessage
        println(e.localizedMessage)
    }

    return u
}

//inline fun <reified U> jsonToModelForOne(jsonString: String, modelType: Type): U? {
//
//    var u: U? = null
//    try {
//        u = Gson().fromJson<U>(jsonString, modelType)
//    } catch (e: java.lang.Exception) {
//        //Global.message = e.localizedMessage
//        println(e.localizedMessage)
//    }
//
//    return U
//}


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

fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
    val property = instance::class.members
        // don't cast here to <Any, R>, it would succeed silently
        .first { it.name == propertyName } as KProperty1<Any, *>
    // force a invalid cast exception if incorrect type here
    return property.get(instance) as R
}

fun getPropertyValue(instance: Any, propertyName: String): String {
    instance::class.memberProperties
        .forEach {
            val name = it.name
            if (name == propertyName) {
                val tmpValue = it.getter.call(instance)
                var value: String = ""
                when (tmpValue) {
                    is Int -> value = tmpValue.toString()
                    is Boolean -> value = tmpValue.toString()
                    is String -> value = tmpValue
                }
                return value
            }
        }
    return ""
}


