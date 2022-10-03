package tw.com.bluemobile.hbc.extensions

import android.text.Editable

// mEditText.text = myString.toEditable()
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun String.isInt(): Boolean {
    if (toIntOrNull() == null) {
        return false
    }
    return true
}