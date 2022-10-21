package tw.com.bluemobile.hbc.extensions

fun ArrayList<String>.parseErrmsg(): String {

    var str: String = ""
    for (msg in this) {
        str += msg + "\n"
    }

    return str
}