package tw.com.bluemobile.hbc.services

import android.content.Context
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import tw.com.bluemobile.hbc.utilities.CompletionHandler
import tw.com.bluemobile.hbc.utilities.PARAMS
import tw.com.bluemobile.hbc.utilities.mergeWith
import java.io.File
import java.io.IOException
import java.lang.Exception

open class BaseService {

    var success: Boolean = false
    var msg: String = ""

    var totalCount: Int = 0
    var page: Int = 0
    var perPage: Int = 0

    var jsonString: String = ""
    val okHttpClient = OkHttpClient()

    open fun getUpdateURL(): String {
        return ""
    }

    open fun update(
        context: Context,
        params: MutableMap<String, String>,
        filePath: String,
        complete: CompletionHandler
    ) {

        val url: String = getUpdateURL()

        var _params: Map<String, String> = hashMapOf()
        _params = _params.mergeWith(PARAMS)
        _params = _params.mergeWith(params)

        val bodyBuilder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)

        for ((key, value) in _params) {
            bodyBuilder.addFormDataPart(key, value)
        }

        val file: File = File(filePath)
        if (file.exists()) {
            val filePart = file.asRequestBody("image/png".toMediaType())
            bodyBuilder.addFormDataPart("file", file.name, filePart)
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