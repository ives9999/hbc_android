package tw.com.bluemobile.hbc.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tw.com.bluemobile.hbc.utilities.Loading

open class ShowActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loading = Loading(this)
    }


    override fun init() {
        super.init()
    }

    open fun refresh() {
        loading.show()

    }
}