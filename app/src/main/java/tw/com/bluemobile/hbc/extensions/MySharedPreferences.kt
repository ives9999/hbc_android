package tw.com.bluemobile.hbc.extensions

import android.content.SharedPreferences

fun SharedPreferences.dump() {
    this.all.map {
        println("${it.key}=>${it.value}")
    }
}

fun SharedPreferences.clear() {
    this.edit().clear().apply()
}