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

    override fun onCreate(savedInstanceState: Bundle?) {

        able_enum = TabEnum.member
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)

        setTop()
        setBottomTabFocus()

        val items: ArrayList<Map<String, Any>> = arrayListOf()
        for (i in image.indices) {
            val item: HashMap<String, Any> = hashMapOf()
            item["image"] = image[i]
            item["text"] = imgText[i]
            items.add(item)
        }

        val adapter: SimpleAdapter = SimpleAdapter(this, items, R.layout.member_home_cell, arrayOf("image", "text"), intArrayOf(R.id.image, R.id.text))
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

internal class GridAdapter (
    private val itemList: List<GridViewModal>,
    private val context: Context): BaseAdapter() {

            private var layoutInflater: LayoutInflater? = null
            private lateinit var iconIV: ImageView
            private lateinit var textTV: TextView

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View, paretn: ViewGroup): View {
        val holderView: View = view

        if (layoutInflater == null) {
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        iconIV = holderView.findViewById(R.id.image)
        textTV = holderView.findViewById(R.id.text)

        iconIV.setImageResource(itemList.get(position).itemImg)
        textTV.setText(itemList.get(position).itemName)

        return holderView
    }
}



















