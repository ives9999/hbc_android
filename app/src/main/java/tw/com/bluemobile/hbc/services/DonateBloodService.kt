package tw.com.bluemobile.hbc.services

import android.content.Context
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import tw.com.bluemobile.hbc.utilities.CompletionHandler
import tw.com.bluemobile.hbc.utilities.DonateBloodEnum
import tw.com.bluemobile.hbc.utilities.URL_HOME
import java.io.File
import java.io.IOException
import java.lang.Exception

object DonateBloodService: BaseService() {

    override fun getDeleteURL(): String {
        return URL_HOME + "donateBlood/getDelete"
    }

    override fun getListURL(): String {
        return URL_HOME + "donateBlood/getList"
    }

    override fun getOneURL(): String {
        return URL_HOME + "donateBlood/getOne"
    }

    override fun getUpdateURL(): String {

        return URL_HOME + "donateBlood/postUpdate"
    }

    fun postUpdate(context: Context, params: MutableMap<String, String>, blood_image: String?, body_image: String?, complete: CompletionHandler) {
        getBaseUrl()
        val url = URL_HOME + "donateBlood/postUpdate"

        val _params: Map<String, String> = composeParams(params, true)

        println(url)
        println(_params)

        val bodyBuilder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)

        for ((key, value) in _params) {
            bodyBuilder.addFormDataPart(key, value)
        }

        if (blood_image != null) {
            val file: File = File(blood_image)
            if (file.exists()) {
                val filePart = file.asRequestBody("image/png".toMediaType())
                bodyBuilder.addFormDataPart(DonateBloodEnum.blood_image.englishName, file.name, filePart)
            }
        }

        if (body_image != null) {
            val file: File = File(body_image)
            if (file.exists()) {
                val filePart = file.asRequestBody("image/png".toMediaType())
                bodyBuilder.addFormDataPart(DonateBloodEnum.body_image.englishName, file.name, filePart)
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
                msg = "網路錯誤，無法跟伺服器更新資料"
                complete(success)
            }

            override fun onResponse(call: Call, response: Response) {

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
}