package tw.com.bluemobile.hbc.controllers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.utilities.TabEnum

class MemberActivity : BaseActivity() {

    private val image = intArrayOf(R.drawable.member_account, R.drawable.member_change_password, R.drawable.donate_blood_out, R.drawable.need_blood_out)
    private val imgText = arrayOf("帳戶資料", "更改密碼", "我的捐血", "我需要血")

    lateinit var itemList: ArrayList<GridViewModal>

    override fun onCreate(savedInstanceState: Bundle?) {

        able_enum = TabEnum.member
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)

        setTop()
        setBottomTabFocus()

        itemList = ArrayList<GridViewModal>()
        for (i in image.indices) {
            itemList.add(GridViewModal(image[i], imgText[i]))
        }

        val adapter: GridAdapter = GridAdapter(this, itemList)
        findViewById<GridView>(R.id.grid) ?. let {
            it.numColumns = 2
            it.adapter = adapter
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

        return holderView
    }
}



















