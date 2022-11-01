package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import tw.com.bluemobile.hbc.extensions.parseErrmsg
import tw.com.bluemobile.hbc.models.*
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.jsonToModelForList
import java.lang.reflect.Type

open class ShowActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        super.init()
    }

    inline fun<reified U: BaseModel> parseJSONAndInit(jsonString: String, modelType: Type): U? {

        //println(jsonString)
        val successModel = jsonToModelForList<SuccessModel<U>>(jsonString, modelType)
        if (successModel != null && successModel.success) {
            val model = successModel.model as U
            model.filterRow()
            runOnUiThread {
                init()
                loading.hide()
            }
            return model

        } else {
            runOnUiThread {
                warning(successModel!!.msgs.parseErrmsg())
            }
            return null
        }
    }

    override fun refresh() {
        loading = Loading(this)
        loading.show()

    }
}