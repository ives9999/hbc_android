package tw.com.bluemobile.hbc.controllers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
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

        findViewById<TextView>(R.id.nicknameTV) ?. let {
            it.text = member.nickname
        }

        findViewById<TextView>(R.id.vesionTV) ?. let {
            it.text = version(this)
        }

        val validateEmail: Boolean = member.checkEMailValidate()
        val validateMobile: Boolean = member.checkMobileValidate()

        itemList = ArrayList<GridViewModal>()
        for (enum in MemberHomeEnum.getAllEnum()) {
            if (enum == MemberHomeEnum.validate_email && validateEmail) {
                continue
            }
            if (enum == MemberHomeEnum.validate_mobile && validateMobile) {
                continue
            }

            val text: String = enum.chineseName
            val icon: Int = enum.getIconID(resources, packageName)
            itemList.add(GridViewModal(icon, text, enum))
        }

        val adapter: MemberGridAdapter = MemberGridAdapter(this, this, itemList)
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

    private fun delete() {
        warning("是否確定要刪除自己的帳號？", "刪除") {
            loading.show()
            MemberService.postDelete(this, member.token!!) { success ->
                loading.hide()
                if (success) {
                    logout()
                }
            }
        }
    }

    private fun logout() {
        member.isLoggedIn = false
        member.reset()
        //member.dump()

        toMemberHome(this)
    }

    fun onClick(memberHomeEnum: MemberHomeEnum) {
        //println(idx)
        when (memberHomeEnum) {
            MemberHomeEnum.account -> toRegister(this)
            MemberHomeEnum.pet -> toDonateBloodList(this, "member")
            MemberHomeEnum.need_blood -> toMemberNeedBloodList(this)
            MemberHomeEnum.donate_blood -> toMemberDonateBloodList(this)
            MemberHomeEnum.reset_password -> toPassword(this, PasswordEnum.reset)
            MemberHomeEnum.validate_email -> toValidate(this, ValidateEnum.email)
            MemberHomeEnum.validate_mobile -> toValidate(this, ValidateEnum.mobile)
            MemberHomeEnum.bank_account -> toBankAccount(this)
            MemberHomeEnum.delete -> delete()
            MemberHomeEnum.refresh -> refresh()
            else -> {}
        }
    }

    override fun refresh() {
        loading.show()
        val params: HashMap<String, String> = hashMapOf("token" to member.token!!)
        MemberService.getOne(this, params) { success ->
            if (success) {
                //println(MemberService.jsonString)
                val successModel = jsonToModel<SuccessModel<MemberModel>>(MemberService.jsonString)
                if (successModel != null) {
                    member.reset()
                    val memberModel = successModel.model
                    memberModel?.filterRow()
                    //memberModel?.dump()
                    memberModel?.toSession(this, true)
                    //member.dump()
                    runOnUiThread {
                        init()
                    }
                }
            }
            loading.hide()
        }
    }

    val validateAR: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            val i: Intent? = res.data
            if (i != null) {
                refresh()
            }
        }
    }
}

data class GridViewModal (
    val itemImg: Int,
    val itemName: String,
    val memberHomeEnum: MemberHomeEnum
)

// on below line we are createing an adapter class for our grid view.
internal class MemberGridAdapter (
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
            val gridViewModal: GridViewModal = itemList[position]
            memberActivity.onClick(gridViewModal.memberHomeEnum)
        }

        return holderView
    }
}



















