package tw.com.bluemobile.hbc.extensions

import android.content.res.Resources.getSystem
import java.text.NumberFormat

fun Int.quotientAndRemainder(dividingBy: Int): Pair<Int, Int> {
    val q: Int = this / dividingBy
    val r: Int = this % dividingBy
    return Pair(q, r)
}

fun Int.formattedWithSeparator(): String {
    return NumberFormat.getNumberInstance().format(this)
}

fun Int.dpToPx(): Int {

    return (this * getSystem().displayMetrics.density).toInt()
}

fun Int.numberToChinese(): String {
    when (this) {
        1->
            return "一"
        2->
            return "二"
        3->
            return "三"
        4->
            return "四"
        5->
            return "五"
        6->
            return "六"
        7->
            return "七"
        8->
            return "八"
        9->
            return "九"
        10->
            return "十"
        else->
            return ""
    }
}