package tw.com.bluemobile.hbc.controllers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.ionbit.ionalert.IonAlert
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.views.Bottom
import tw.com.bluemobile.hbc.views.Top
import tw.com.bluemobile.hbc.routes.*
import tw.com.bluemobile.hbc.utilities.KeyEnum
import tw.com.bluemobile.hbc.utilities.TabEnum
import tw.com.bluemobile.hbc.utilities.isEmulator
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Method

var able_enum: TabEnum = TabEnum.member //每一組頁面，都有一個專屬的代號的enum
var msg: String = "" //目前使用在傳送錯誤訊息

open class BaseActivity : AppCompatActivity(), ToMember, ToNeedBlood, To {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isEmulator = _isEmulator()
    }

    open fun cancel() {
        prev()
    }

    private fun _isEmulator(): Boolean {

        val result = (Build.FINGERPRINT.startsWith("google/sdk_gphone_")
                && Build.FINGERPRINT.endsWith(":user/release-keys")
                && Build.MANUFACTURER == "Google" && Build.PRODUCT.startsWith("sdk_gphone_") && Build.BRAND == "google"
                && Build.MODEL.startsWith("sdk_gphone_"))
                //
                || Build.FINGERPRINT.contains("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                //bluestacks
                || "QC_Reference_Phone" == Build.BOARD && !"Xiaomi".equals(Build.MANUFACTURER, ignoreCase = true) //bluestacks
                || Build.MANUFACTURER.contains("Genymotion")

                //Sony is true, so mark it. HOST:BuildHost
//                || Build.HOST.startsWith("Build") //MSI App Player
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.PRODUCT == "google_sdk"
                // another Android SDK emulator check
                || SystemProperties.getProp("ro.kernel.qemu") == "1"
        return result
    }

    open fun cellClick(keyEnum: KeyEnum, id: Int) {
        //println(id)
    }

    open fun init() {
        //top.showPrev(isPrevIconShow)
    }

    open fun prev() {
        //hideKeyboard()
        val intent = Intent()
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    protected fun setBottom() {

        findViewById<Bottom>(R.id.bottom) ?. let {
            it.setFocus(packageName)
        }
    }

    private fun setTitle(title: String? = null) {
        findViewById<Top>(R.id.top) ?. let { itTop ->
            title?.let {
                itTop.setTitle(it)
            } ?: run {
                itTop.setTitle(able_enum.chineseName)
            }
        }
    }

    protected fun setTop(isPrevShow: Boolean = false, title: String? = null) {

        findViewById<Top>(R.id.top) ?. let { itTop->
            itTop.showPrev(isPrevShow)
        }

        setTitle(title)
    }

    open fun submit() {

    }

    fun warning(msg: String) {
        val box = IonAlert(this, IonAlert.WARNING_TYPE)
        box.titleText = "警吿"
        box.contentText = msg
        box.confirmText = "關閉"
        box.show()
    }

    fun warning(msg: String, buttonAction: () -> Unit) {
        val box = IonAlert(this, IonAlert.WARNING_TYPE)
        box.titleText = "警吿"
        box.contentText = msg
        box.confirmText = "關閉"

        box.setConfirmClickListener {
            buttonAction()
            it.cancel()
        }
        box.show()
    }

    fun warning(msg: String, cancelButtonTitle: String, buttonAction: () -> Unit) {
        val box = IonAlert(this, IonAlert.WARNING_TYPE)
        box.titleText = "警吿"
        box.contentText = msg
        box.confirmText = "關閉"
        box.cancelText = cancelButtonTitle
        box.showCancelButton(true)

        box.setConfirmClickListener {
            buttonAction()
            it.cancel()
        }
        box.show()
    }
}

object SystemProperties {
    private var failedUsingReflection = false
    private var getPropMethod: Method? = null

    @SuppressLint("PrivateApi")
    fun getProp(propName: String, defaultResult: String = ""): String {
        if (!failedUsingReflection) try {
            if (getPropMethod == null) {
                val clazz = Class.forName("android.os.SystemProperties")
                getPropMethod = clazz.getMethod("get", String::class.java, String::class.java)
            }
            return getPropMethod!!.invoke(null, propName, defaultResult) as String? ?: defaultResult
        } catch (e: Exception) {
            getPropMethod = null
            failedUsingReflection = true
        }
        var process: Process? = null
        try {
            process = Runtime.getRuntime().exec("getprop \"$propName\" \"$defaultResult\"")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            return reader.readLine()
        } catch (e: IOException) {
        } finally {
            process?.destroy()
        }
        return defaultResult
    }
}




















