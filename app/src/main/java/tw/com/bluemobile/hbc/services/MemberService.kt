package tw.com.bluemobile.hbc.services

import android.content.Context
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import tw.com.bluemobile.hbc.extensions.toJSON
import tw.com.bluemobile.hbc.utilities.*
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

object MemberService: BaseService() {

    override fun getOneURL(): String {
        return URL_HOME + "member/postOne"
    }

    override fun getUpdateURL(): String {

        return URL_HOME + "member/register"
    }

    fun bank(context: Context, params: MutableMap<String, String>) { success ->

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