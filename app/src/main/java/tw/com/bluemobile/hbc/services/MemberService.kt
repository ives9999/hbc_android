package tw.com.bluemobile.hbc.services

import android.content.Context
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import tw.com.bluemobile.hbc.utilities.*
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.util.*

object MemberService: BaseService() {

    override fun getDeleteURL(): String {
        return URL_HOME + "member/postDelete"
    }

    override fun getOneURL(): String {
        return URL_HOME + "member/postOne"
    }

    override fun getUpdateURL(): String {

        return URL_HOME + "member/register"
    }

    fun bank(context: Context, params: MutableMap<String, String>, complete: CompletionHandler) {
        getBaseUrl()
        val url = URL_HOME + "member/postBank"

        val _params: Map<String, String> = composeParams(params, true)

//        println(url)
//        println(_params.toJSON())

        val request: Request = getRequest(url, _params)

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

    fun forgetPassword(context: Context, email: String, complete: CompletionHandler) {
        val lowerCaseEmail = email.lowercase(Locale.ROOT)
        getBaseUrl()
        val url = URL_HOME + "member/forgetPassword"

        var _params: Map<String, String> = hashMapOf()
        _params = _params.mergeWith(PARAMS)
        _params = _params.mergeWith(mapOf(EMAIL_KEY to email))

        val request: Request = getRequest(url, _params)

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

//    fun getPetList(context: Context, page: Int, perPage: Int, complete: CompletionHandler) {
//
//        getBaseUrl()
//        val url = URL_HOME + "member/getPetList"
//        val params: MutableMap<String, String> = mutableMapOf(
//            "page" to page.toString(),
//            "perPage" to perPage.toString()
//        )
//        val _params: Map<String, String> = composeParams(params, true)
//        _simpleService(context, url, _params, complete, true)
//    }

//    fun postPetOne(context: Context, params: MutableMap<String, String>, complete: CompletionHandler /* = (Success: kotlin.Boolean) -> kotlin.Unit */) {
//        getBaseUrl()
//        val url: String = URL_HOME + "member/postPetOne"
//
//        val _params: Map<String, String> = composeParams(params)
//        _simpleService(context, url, _params, complete)
//    }

    fun login(
        context: Context,
        params: MutableMap<String, String>,
        complete: CompletionHandler
    ) {
        getBaseUrl()
        val url: String = URL_HOME + "member/login"

        var _params: Map<String, String> = hashMapOf()
        _params = _params.mergeWith(PARAMS)
        _params = _params.mergeWith(params)

        _simpleService(context, url, _params, complete)
    }

//    fun postDeletePet(context: Context, token: String, complete: CompletionHandler) {
//        getBaseUrl()
//        val url = URL_HOME + "member/postDeletePet"
//        val params: HashMap<String, String> = hashMapOf("member_pet_token" to token)
//        val _params: Map<String, String> = composeParams(params, true)
//
//        _simpleService(context, url, _params, complete)
//    }

//    fun postPet(context: Context, params: MutableMap<String, String>, blood_image: String?, body_image: String?, complete: CompletionHandler) {
//        getBaseUrl()
//        val url = URL_HOME + "member/postPet"
//
//        val _params: Map<String, String> = composeParams(params, true)
//
////        println(url)
////        println(_params)
//
//        val bodyBuilder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
//
//        for ((key, value) in _params) {
//            bodyBuilder.addFormDataPart(key, value)
//        }
//
//        if (blood_image != null) {
//            val file: File = File(blood_image)
//            if (file.exists()) {
//                val filePart = file.asRequestBody("image/png".toMediaType())
//                bodyBuilder.addFormDataPart(DonateBloodEnum.blood_image.englishName, file.name, filePart)
//            }
//        }
//
//        if (body_image != null) {
//            val file: File = File(body_image)
//            if (file.exists()) {
//                val filePart = file.asRequestBody("image/png".toMediaType())
//                bodyBuilder.addFormDataPart(DonateBloodEnum.body_image.englishName, file.name, filePart)
//            }
//        }
//
//        val body: RequestBody = bodyBuilder.build()
//
//        val request = okhttp3.Request.Builder()
//            .addHeader("Content-Type", "multipart/form-data")
//            .url(url)
//            .post(body)
//            .build()
//
//        okHttpClient.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                msg = "網路錯誤，無法跟伺服器更新資料"
//                complete(success)
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//
//                try {
//                    jsonString = response.body!!.string()
////                    println(jsonString)
//                    success = true
//                } catch (e: Exception) {
//                    success = false
//                    msg = "parse json failed，請洽管理員"
//                    println(e.localizedMessage)
//                }
//                complete(success)
//            }
//        })
//    }

    fun resetPassword(context: Context, password: String, complete: CompletionHandler) {
        getBaseUrl()
        val url = URL_HOME + "member/postResetPassword"

        val params: MutableMap<String, String> = mutableMapOf(PASSWORD_KEY to password, REPASSWORD_KEY to password)
        val _params: Map<String, String> = composeParams(params, true)

//        println(url)
//        println(_params.toJSON())

        val request: Request = getRequest(url, _params)

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

    fun validate(context: Context, type: ValidateEnum, code: String, complete: CompletionHandler) {
        getBaseUrl()
        val url = URL_HOME + "member/postValidate"

        val params: MutableMap<String, String> = mutableMapOf("code" to code, "type" to type.englishName)
        val _params: Map<String, String> = composeParams(params, true)

//        println(url)
//        println(_params.toJSON())

        val request: Request = getRequest(url, _params)

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