package tw.com.bluemobile.hbc.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tw.com.bluemobile.hbc.adapters.BaseViewHolder
import tw.com.bluemobile.hbc.models.BaseModel
import tw.com.bluemobile.hbc.models.MemberPetModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.jsonToModel
import tw.com.bluemobile.hbc.utilities.jsonToModelForOne
import java.lang.reflect.Type

open class ShowActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun init() {
        super.init()
    }

    inline fun<reified U> parseJSON(jsonString: String): U? {

        //println(jsonString)
        val baseModel = jsonToModelForOne<U>(jsonString)
        runOnUiThread {
            init()
            loading.hide()
        }

        return baseModel
    }

    open fun refresh() {
        loading = Loading(this)
        loading.show()

    }
}