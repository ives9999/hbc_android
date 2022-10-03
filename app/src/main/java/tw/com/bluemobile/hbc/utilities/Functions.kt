package tw.com.bluemobile.hbc.utilities

infix fun <T: Any?> Boolean?.then(block: () -> T): T? = if (this == true) block() else null

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


