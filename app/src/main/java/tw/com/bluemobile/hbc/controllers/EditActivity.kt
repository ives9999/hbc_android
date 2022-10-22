package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.jsonToModelForOne

open class EditActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun init() {
        super.init()
    }

    inline fun<reified U> parseJSON(jsonString: String): U? {

        //println(jsonString)
        val baseModel = jsonToModelForOne<U>(jsonString)

        return baseModel
    }

    override fun refresh() {
        loading.show()
    }

}