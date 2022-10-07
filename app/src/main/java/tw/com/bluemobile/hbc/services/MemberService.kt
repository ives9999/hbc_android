package tw.com.bluemobile.hbc.services

import android.content.Context
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import tw.com.bluemobile.hbc.utilities.*
import java.io.File
import java.io.IOException
import java.lang.Exception

object MemberService: BaseService() {

    override fun getUpdateURL(): String {

        return URL_HOME + "member/register"
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
}