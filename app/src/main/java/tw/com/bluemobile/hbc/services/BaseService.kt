package tw.com.bluemobile.hbc.services

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import tw.com.bluemobile.hbc.utilities.*
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.reflect.Method

open class BaseService {

    var success: Boolean = false
    var msg: String = ""

    var totalCount: Int = 0
    var page: Int = 0
    var perPage: Int = 0

    var jsonString: String = ""
    val okHttpClient = OkHttpClient()

    fun _isEmulator(): Boolean {

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

    fun _simpleService(context: Context, url: String, params: Map<String, String>, complete: CompletionHandler) {

        val request: okhttp3.Request = getRequest(url, params)
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                msg = "網路錯誤，無法跟伺服器更新資料"
                complete(success)
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {

                try {
                    jsonString = response.body!!.string()
//                    println(jsonString)
                    success = true
                } catch (e: Exception) {
                    success = false
                    msg = "parse json failed，請洽管理員"
                    println(e.localizedMessage)
                }
                complete(success)
            }
        })
    }

    protected fun getBaseUrl() {
        isEmulator = _isEmulator()
        isEmulator = true
        BASE_URL = (isEmulator then { LOCALHOST_BASE_URL }) ?: REMOTE_BASE_URL
        URL_HOME = "$BASE_URL/"
    }

    fun getRequest(url: String, params: Map<String, String>): okhttp3.Request {

        val j: JSONObject = JSONObject(params as Map<*, *>)
        val body: RequestBody = j.toString().toRequestBody(HEADER.toMediaTypeOrNull())

        return okhttp3.Request.Builder()
            .url(url)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .post(body)
            .build()
    }

    open fun getUpdateURL(): String {
        return ""
    }

    open fun update(
        context: Context,
        params: MutableMap<String, String>,
        filePath: String?,
        complete: CompletionHandler
    ) {
        getBaseUrl()
        val url: String = getUpdateURL()

        var _params: Map<String, String> = hashMapOf()
        _params = _params.mergeWith(PARAMS)
        _params = _params.mergeWith(params)

        val bodyBuilder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)

        for ((key, value) in _params) {
            bodyBuilder.addFormDataPart(key, value)
        }

        if (filePath != null) {
            val file: File = File(filePath)
            if (file.exists()) {
                val filePart = file.asRequestBody("image/png".toMediaType())
                bodyBuilder.addFormDataPart("avatar", file.name, filePart)
            }
        }

        val body: RequestBody = bodyBuilder.build()

        val request = okhttp3.Request.Builder()
            .addHeader("Content-Type", "multipart/form-data")
            .url(url)
            .post(body)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                msg = e.localizedMessage
                complete(success)
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {

                try {
                    jsonString = response.body!!.string()
                    println(jsonString)
                    success = true
                } catch (e: Exception) {
                    success = false
                    msg = "parse json failed，請洽管理員"
                    println(e.localizedMessage)
                }
                complete(success)
            }
        })
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