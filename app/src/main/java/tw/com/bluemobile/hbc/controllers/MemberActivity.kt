package tw.com.bluemobile.hbc.controllers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.awesomedialog.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.member
import tw.com.bluemobile.hbc.models.MemberModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.Featured

class MemberActivity : BaseActivity() {

    lateinit var itemList: ArrayList<GridViewModal>
    private var featured: Featured? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        if (!member.isLoggedIn) {
            toLogin(this)
        }

        able_enum = TabEnum.member
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)

        setTop()
        setBottom(able_enum)

        init()
    }

    override fun init() {
        super.init()
        loading = Loading(this)

        findViewById<Featured>(R.id.featured) ?. let {
            featured = it
            it.setFeatured(member.featured!!, true)
            //it.setOnImagePickListener(pickProfileImage, pickCameraImage)
        }

        itemList = ArrayList<GridViewModal>()
        for (enum in MemberHomeEnum.getAllEnum()) {
            val text: String = enum.chineseName
            val icon: Int = enum.getIconID(resources, packageName)
            itemList.add(GridViewModal(icon, text))
        }

        val adapter: GridAdapter = GridAdapter(this, this, itemList)
        findViewById<GridView>(R.id.grid) ?. let {
//            it.numColumns = 2
            it.adapter = adapter
        }

        findViewById<LinearLayout>(R.id.logout) ?. let {
            it.setOnClickListener {
                AwesomeDialog.build(this)
                    .title(member.nickname!!)
                    .body("確定要登出嗎？")
                    .icon(R.drawable.ic_person)
                    .onPositive("登出") {
                        logout()
                    }
                    .onNegative("取消")
            }
        }
    }

    private fun logout() {
        member.isLoggedIn = false
        member.reset()
        member.dump()

        toMemberHome(this)
    }

    fun onClick(idx: Int) {
        //println(idx)
        when (MemberHomeEnum.enumFromIdx(idx)) {
            MemberHomeEnum.account -> toRegister(this)
            MemberHomeEnum.reset_password -> toPassword(this, PasswordEnum.reset)
            MemberHomeEnum.refresh -> refresh()
            else -> {}
        }
    }

    private fun refresh() {
        loading.show()
        MemberService.getOne(this, hashMapOf()) { success ->
            if (success) {
                //println(MemberService.jsonString)
//                val modelType = genericType<SuccessModel<MemberModel>>()
//                val successModel = Gson().fromJson<SuccessModel<MemberModel>>(MemberService.jsonString, modelType)
                val successModel = jsonToModel<SuccessModel<MemberModel>>(MemberService.jsonString)
                if (successModel != null) {
                    successModel.model?.toSession(this, true)
                }
            }
            loading.hide()
        }
    }
}

data class GridViewModal (
    val itemImg: Int,
    val itemName: String
)

// on below line we are createing an adapter class for our grid view.
internal class GridAdapter (
    private val context: Context,
    private val memberActivity: MemberActivity,
    private val itemList: List<GridViewModal>): BaseAdapter() {

    // in base adapter class we are creating variables
    // for layout inflater, image view and text view.
    private var layoutInflater: LayoutInflater? = null
    private lateinit var iconIV: ImageView
    private lateinit var textTV: TextView

    // below method is use to return the count of list.
    override fun getCount(): Int {
        return itemList.size
    }

    // below function is use to return the item of grid view.
    override fun getItem(position: Int): Any? {
        return null
    }

    // below function is use to return item id of grid view.
    override fun getItemId(position: Int): Long {
        return 0
    }

    // in below function we are getting individual item of grid view.
    override fun getView(position: Int, view: View?, paretn: ViewGroup?): View? {
        var holderView = view

        // on blow line we are checking if layout inflater
        // is null, if it is null we are initializing it.
        if (layoutInflater == null) {
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (holderView == null) {
            holderView = layoutInflater!!.inflate(R.layout.member_home_cell, null)
        }

        // on below line we are initializing our image view and text view with their ids
        iconIV = holderView!!.findViewById(R.id.image)
        textTV = holderView.findViewById(R.id.text)

        // on below line we are setting image and text in view
        iconIV.setImageResource(itemList[position].itemImg)
        textTV.setText(itemList[position].itemName)

        holderView.setOnClickListener {
            memberActivity.onClick(position)
        }

        return holderView
    }
}



















