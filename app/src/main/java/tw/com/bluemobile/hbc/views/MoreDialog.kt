package tw.com.bluemobile.hbc.views

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.data.SelectRow
import tw.com.bluemobile.hbc.utilities.KeyEnum
import tw.com.bluemobile.hbc.utilities.then
import tw.com.bluemobile.hbc.utilities.viewHolder

open class MoreDialog(context: Context, private val screenWidth: Int, private val keyEnum: KeyEnum, private val selected: String, private val delegate: MoreDialogDelegate?): Dialog(context) {

    //var city_id: Int? = null
    open lateinit var adapter: SelectSingleAdapter<SelectSingleViewHolder>

    fun init(isPrev: Boolean, title: String) {

        findViewById<Top>(R.id.top) ?. let { itTop->
            itTop.showPrev(isPrev)
            itTop.setTitle(title)
        }

        findViewById<Action>(R.id.action) ?. let {
            it.setOnCancelListener() {
                this.hide()
            }
        }

        adapter = SelectSingleAdapter(::SelectSingleViewHolder, keyEnum, selected, delegate)

        val recyclerView: RecyclerView? = this.findViewById<RecyclerView>(R.id.tableView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

        //selectSingleAdapter.rows = rowBridge()

        setBottomButtonPadding(1, 300)
    }

    private fun setBottomButtonPadding(bottom_button_count: Int, button_width: Int) {

        val padding: Int = (screenWidth - bottom_button_count * button_width) / (bottom_button_count + 1)

        findViewById<Button>(R.id.submit) ?. let {
            if (it.visibility == View.VISIBLE) {
                val params: ViewGroup.MarginLayoutParams =
                    it.layoutParams as ViewGroup.MarginLayoutParams
                params.width = button_width
                params.marginStart = padding
                //params.bottomMargin = 60
                it.layoutParams = params
            }
        }

        findViewById<Button>(R.id.cancel) ?. let {
            if (it.visibility == View.VISIBLE) {
                val params: ViewGroup.MarginLayoutParams =
                    it.layoutParams as ViewGroup.MarginLayoutParams
                params.width = button_width
                params.marginStart = padding
                //params.bottomMargin = 60
                it.layoutParams = params
            }
        }
    }

    private fun rowBridgeForArea(): ArrayList<SelectRow> {
//        if (city_id != null) {
//            val areas: ArrayList<AreaModel> = Zones.getAreasByCityID(city_id!!)
//
//            for (area in areas) {
//                val title = area.name
//                val id = area.id
//                selectRows.add(SelectRow(id, title, id.toString()))
//            }
//        }

        return ArrayList()
    }

    fun setRows(rows: ArrayList<SelectRow>) {
        adapter.rows = rows
        adapter.notifyDataSetChanged()
    }

    open fun rowBridge() {}

    fun show(padding: Int=0) {
        if (padding > 0) {
            val lp: WindowManager.LayoutParams = WindowManager.LayoutParams()
            lp.copyFrom(this.window!!.attributes)
            lp.width = screenWidth - padding
            this.window!!.attributes = lp
        }

        super.show()
    }
}

//typealias selectViewHolder<T> = (View, KeyEnum, MoreDialogDelegate?)-> T
open class SelectSingleAdapter<T: SelectSingleViewHolder>(
    open val viewHolderConstructor: viewHolder<T>,
    val keyEnum: KeyEnum,
    val selected: String,
    val delegate: MoreDialogDelegate?
    ): RecyclerView.Adapter<T>() {

    var rows: ArrayList<SelectRow> = arrayListOf()

    override fun onBindViewHolder(holder: T, position: Int) {

        val row: SelectRow = rows[position]
        val isSelected: Boolean = ((selected == row.value) then { true }) ?: false
        holder.bind(row, position, isSelected)
    }
    override fun getItemCount(): Int {
        return rows.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {

        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = inflater.inflate(R.layout.select_row, parent, false)
        val t = viewHolderConstructor(parent.context, viewHolder)
        t.keyEnum = keyEnum
        t.delegate = delegate

        return t
    }
}

class SelectSingleViewHolder(context: Context, val view: View): RecyclerView.ViewHolder(view) {

    val titleTV: TextView = view.findViewById(R.id.title)
    private val selectedIV: ImageView = view.findViewById(R.id.selected)
    var keyEnum: KeyEnum? = null
    var delegate: MoreDialogDelegate? = null

    fun bind(row: SelectRow, idx: Int, isSelected: Boolean = false) {
        titleTV.text = row.title

        if (isSelected) {
            selectedIV.visibility = View.VISIBLE
        } else {
            selectedIV.visibility = View.INVISIBLE
        }

        view.setOnClickListener {
            selectedIV.visibility = View.VISIBLE
            //println(delegate)
            //delegate?.delegateCellClick(row.id)
            delegate?.delegateCityClick(row.id)
        }
    }
}

interface MoreDialogDelegate {

    fun delegateCellClick(id: Int){}
    fun delegateCityClick(id: Int){}
}