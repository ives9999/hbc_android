package tw.com.bluemobile.hbc.views

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.data.SelectRow
import tw.com.bluemobile.hbc.utilities.AREA_KEY
import tw.com.bluemobile.hbc.utilities.CITY_KEY
import tw.com.bluemobile.hbc.utilities.Zone

class MoreDialog(context: Context, val screenWidth: Int, var key: String? = null, val delegate: BaseActivity): Dialog(context) {

    var city_id: Int? = null

    fun setBottomButtonPadding(bottom_button_count: Int, button_width: Int) {

        val padding: Int = (screenWidth - bottom_button_count * button_width) / (bottom_button_count + 1)

//        findViewById<Button>(R.id.submitBtn) ?. let {
//            if (it.visibility == View.VISIBLE) {
//                val params: ViewGroup.MarginLayoutParams =
//                    it.layoutParams as ViewGroup.MarginLayoutParams
//                params.width = button_width
//                params.marginStart = padding
//                it.layoutParams = params
//            }
//        }
//
//        findViewById<Button>(R.id.cancelBtn) ?. let {
//            if (it.visibility == View.VISIBLE) {
//                val params: ViewGroup.MarginLayoutParams =
//                    it.layoutParams as ViewGroup.MarginLayoutParams
//                params.width = button_width
//                params.marginStart = padding
//                it.layoutParams = params
//
//                it.setOnClickListener {
//                    this.dismiss()
//                }
//            }
//        }
    }

//    private fun rowBridgeForArea(): ArrayList<SelectRow> {
//        val selectRows: ArrayList<SelectRow> = arrayListOf()
//        if (city_id != null) {
//            val areas: ArrayList<Area> = Global.getAreasByCityID(city_id!!)
//
//            for (area in areas) {
//                val title = area.name
//                val id = area.id
//                selectRows.add(SelectRow(title, id.toString()))
//            }
//        }
//
//        return selectRows
//    }

    private fun rowBridgeForCity(): ArrayList<SelectRow> {
        val citys = Zone.getCitys()

        val selectRows: ArrayList<SelectRow> = arrayListOf()

        for(city in citys) {
            val title = city.name
            val id = city.id
            selectRows.add(SelectRow(title, id.toString()))
        }

        return selectRows
    }

    fun setSelectSingle(selected: String, delegate: BaseActivity): SelectSingleAdapter {

        val selectSingleAdapter: SelectSingleAdapter = SelectSingleAdapter(selected, delegate)

        val recyclerView: RecyclerView? = this.findViewById<RecyclerView>(R.id.tableView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = selectSingleAdapter

//        this.findViewById<Button>(R.id.submitBtn) ?.let {
//            it.visibility = View.GONE
//        }
//
        if (key == CITY_KEY) {
            selectSingleAdapter.rows = rowBridgeForCity()
        } else if (key == AREA_KEY) {
            //singleSelectAdapter.rows = rowBridgeForArea()
        }
        
        return selectSingleAdapter
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

class SelectSingleAdapter(var selected: String?, val delegate: BaseActivity): RecyclerView.Adapter<SelectSingleViewHolder>() {

    var rows: ArrayList<SelectRow> = arrayListOf()

    override fun onBindViewHolder(holder: SelectSingleViewHolder, position: Int) {

        val row: SelectRow = rows[position]
        holder.title.text = row.title

        if (selected == row.value) {
            holder.selected.visibility = View.VISIBLE
            //holder.title.selected()
        } else {
            holder.selected.visibility = View.INVISIBLE
            //holder.title.unSelected()
        }

        holder.viewHolder.setOnClickListener {
            delegate.cellClick(position)
        }
    }

    override fun getItemCount(): Int {
        return rows.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectSingleViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = inflater.inflate(R.layout.select_row, parent, false)

        return SelectSingleViewHolder(viewHolder)
    }
}

class SelectSingleViewHolder(val viewHolder: View): RecyclerView.ViewHolder(viewHolder) {

    val title: TextView = viewHolder.findViewById(R.id.title)
    val selected: ImageView = viewHolder.findViewById(R.id.selected)
}