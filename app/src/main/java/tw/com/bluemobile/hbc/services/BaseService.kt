package tw.com.bluemobile.hbc.services

import android.content.Context
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import tw.com.bluemobile.hbc.utilities.CompletionHandler
import tw.com.bluemobile.hbc.utilities.PARAMS
import tw.com.bluemobile.hbc.utilities.mergeWith
import java.io.File

open class BaseService {

    var success: Boolean = false
    var msg: String = ""
    var totalCount: Int = 0
    var page: Int = 0
    var perPage: Int = 0

    open fun update(
        context: Context,
        params: MutableMap<String, String>,
        filePath: String,
        complete: CompletionHandler
    ) {

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


    }
}