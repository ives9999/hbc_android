package tw.com.bluemobile.hbc.controllers

import android.os.Bundle
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.reflect.TypeToken
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.adapters.BaseList
import tw.com.bluemobile.hbc.extensions.setInfo
import tw.com.bluemobile.hbc.models.BaseModel
import tw.com.bluemobile.hbc.utilities.PERPAGE
import java.lang.reflect.Type

open class ListActivity: BaseActivity() {

    var recyclerView: RecyclerView? = null

    protected var page: Int = 1
    protected var perPage: Int = PERPAGE
    protected var totalCount: Int = 0
    protected var totalPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showNoRows() {
        findViewById<RelativeLayout>(R.id.root)?.setInfo(this, "目前暫無資料！！")
    }
}