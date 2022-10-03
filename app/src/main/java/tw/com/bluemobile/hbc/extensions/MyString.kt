package tw.com.bluemobile.hbc.extensions

import android.text.Editable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.isInt(): Boolean {
    if (toIntOrNull() == null) {
        return false
    }
    return true
}

fun String.isDate(format: String="yyy-MM-dd"): Boolean {
    try {
        val df = SimpleDateFormat(format)
        df.isLenient = false
        df.parse(this)
        return true
    } catch (e: ParseException) {
        return false
    }
}

fun String.isDateTime(format: String="yyy-MM-dd HH:mm:ss"): Boolean {
    try {
        val df = SimpleDateFormat(format)
        df.isLenient = false
        df.parse(this)
        return true
    } catch (e: ParseException) {
        return false
    }
}

fun String.isTime(format: String="HH:mm:ss"): Boolean {
    try {
        val df = SimpleDateFormat(format)
        df.isLenient = false
        df.parse(this)
        return true
    } catch (e: ParseException) {
        return false
    }
}

fun String.mobileShow(): String {
    var res = this
    res = res.replace(" ", "")
    res = res.replace("-", "")
    val pattern = "^(09\\d\\d)\\-?(\\d\\d\\d)\\-?(\\d\\d\\d)\$"
    val matches = reMatches(pattern, res)
    if (matches.size > 3) {
        res = matches[1] + "-" + matches[2] + "-" + matches[3]
    }

    return res
}


fun String.noTime(): String {
    var res: String = this
    if (this.isDateTime()) {
        res = res.toDateTime()!!.toMyString("yyyy-MM-dd")
    }
    return res
}

fun String.noSec(): String {
    val arr: List<String> = this.split(":")
    var res: String = this
    if (arr.size > 2) {
        res = "${arr[0]}:${arr[1]}"
    }
    return res
}

fun String.reMatches(pattern: String, _str: String?=null): Array<String> {
    var str = _str
    if (str == null) {
        str = this
    }
    var matches: Array<String> = arrayOf()
    val re = pattern.toRegex()
    val b = re.containsMatchIn(str)
    if (b) {
        val matchRes = re.find(str)
        if (matchRes != null) {
            val arr = matchRes.groupValues
            matches = arr.toTypedArray()
        }
        //println(res)
    }


    return matches
}

fun String.telShow(): String {
    var res = this
    res = res.replace(" ", "")
    res = res.replace("-", "")
    val pattern = "^(0\\d)\\-?(\\d\\d\\d\\d)\\-?(\\d\\d\\d\\d?)$"
    val matches = reMatches(pattern, res)
    if (matches.size > 3) {
        res = matches[1] + "-" + matches[2] + "-" + matches[3]
    }

    return res
}


fun String.toDate(pattern: String = "yyyy-MM-dd"): Date? {
    var date: Date? = null
    if (this.isDate()) {
        //val formatter = DateTimeFormatter.ofPattern(pattern, Locale.TAIWAN)
        val formatter = SimpleDateFormat(pattern)
        date = formatter.parse(this)
    }

    return date
}

fun String.toDateTime(pattern: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    var date: Date? = null
    if (this.isDateTime(pattern) || this.isDate(pattern) || this.isTime(pattern)) {
        //val formatter = DateTimeFormatter.ofPattern(pattern, Locale.TAIWAN)
        val formatter = SimpleDateFormat(pattern)
        date = formatter.parse(this)
    }

    return date
}


// mEditText.text = myString.toEditable()
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
