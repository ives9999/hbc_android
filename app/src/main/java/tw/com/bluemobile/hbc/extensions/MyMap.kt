package tw.com.bluemobile.hbc.extensions

import com.google.gson.Gson

fun Map<*, *>.toJSON(): String {
    return Gson().toJson(this).toString()
}