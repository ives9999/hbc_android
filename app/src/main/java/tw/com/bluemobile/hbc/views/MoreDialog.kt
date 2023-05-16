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
import tw.com.bluemobile.hbc.utilities.Zones
import tw.com.bluemobile.hbc.utilities.then
import tw.com.bluemobile.hbc.utilities.viewHolder
import java.security.Key

typealias selectViewHolder<T> = (View, KeyEnum?, MoreDialogDelegate?)-> T

open class MoreDialog<T: SelectSingleViewHolder>(
        context: Context,
        private val screenWidth: Int,
        private val viewHolderConstructor: selectViewHolder<T>,
        private val keyEnum: KeyEnum,
        private val selected: String,
        private val delegate: MoreDialogDelegate?
    ): Dialog(context) {

    //var city_id: Int? = null
    open lateinit var adapter: SelectSingleAdapter<T>

    init {

    }

    open fun init(isPrev: Boolean, title: String) {

        setContentView(R.layout.select_single)

        findViewById<Top>(R.id.top) ?. let { itTop->
            itTop.showPrev(isPrev)
            itTop.setTitle(title)
        }

        findViewById<Action>(R.id.action) ?. let {
            it.setOnCancelListener() {
                this.hide()
            }
        }

        adapter = SelectSingleAdapter<T>(viewHolderConstructor, keyEnum, selected, delegate)

        val recyclerView: RecyclerView? = this.findViewById<RecyclerView>(R.id.tableView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

        setBottomButtonPadding(1, 300)

        rowBridge()
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

    fun setRows(rows: ArrayList<SelectRow>) {
        adapter.rows = rows
        adapter.notifyDataSetChanged()
    }

    open fun rowBridge() {
//        val citys = Zones.getCitys()
//
//        val thisRows: ArrayList<SelectRow> = arrayListOf()
//        for(city in citys) {
//            val title = city.name
//            val id = city.id
//            thisRows.add(SelectRow(id, title, id.toString()))
//        }
//        setRows(thisRows)
    }

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
    open val viewHolderConstructor: selectViewHolder<T>,
    private val keyEnum: KeyEnum? = null,
    private val selected: String? = null,
    private val delegate: MoreDialogDelegate? = null
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
        val t = viewHolderConstructor(viewHolder, keyEnum, delegate)

        return t
    }
}

open class SelectSingleViewHolder(val view: View, val keyEnum: KeyEnum? = null, private val delegate: MoreDialogDelegate? = null): RecyclerView.ViewHolder(view) {

    val titleTV: TextView = view.findViewById(R.id.title)
    private val selectedIV: ImageView = view.findViewById(R.id.selected)

    fun bind(row: SelectRow, idx: Int, isSelected: Boolean = false) {
        titleTV.text = row.title

        if (isSelected) {
            selectedIV.visibility = View.VISIBLE
        } else {
            selectedIV.visibility = View.INVISIBLE
        }

        view.setOnClickListener {
            selectedIV.visibility = View.VISIBLE
            rowSetOnclickListener(row)
        }
    }

    open fun rowSetOnclickListener(row: SelectRow) {
        delegate?.delegateCellClick(row.id)
    }
}

interface MoreDialogDelegate {

    fun delegateCellClick(id: Int){}
    fun delegateCityClick(id: Int){}
    fun delegateAreaClick(id: Int){}
}