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
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.data.SelectRow
import tw.com.bluemobile.hbc.models.AreaModel
import tw.com.bluemobile.hbc.utilities.KeyEnum
import tw.com.bluemobile.hbc.utilities.Zones

open class MoreDialog(context: Context, private val screenWidth: Int, private val keyEnum: KeyEnum, private val selected: String): Dialog(context) {

    //var city_id: Int? = null
    val selectRows: ArrayList<SelectRow> = arrayListOf()
    var delegate: MoreDialogDelegate? = null

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

        return selectRows
    }

    open fun rowBridge(): ArrayList<SelectRow> {
        return ArrayList<SelectRow>()
    }


    open fun setAdapter(): SelectSingleAdapter {

        val selectSingleAdapter: SelectSingleAdapter = SelectSingleAdapter(selected, keyEnum, delegate)

        val recyclerView: RecyclerView? = this.findViewById<RecyclerView>(R.id.tableView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = selectSingleAdapter

        selectSingleAdapter.rows = rowBridge()

//        if (keyEnum == KeyEnum.city_id) {
//            selectSingleAdapter.rows = rowBridgeForCity()
//        } else if (keyEnum == KeyEnum.area_id) {
//            selectSingleAdapter.rows = rowBridgeForArea()
//        }
        
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

open class SelectSingleAdapter(var selected: String?, val keyEnum: KeyEnum, val delegate: MoreDialogDelegate?): RecyclerView.Adapter<SelectSingleViewHolder>() {

    var rows: ArrayList<SelectRow> = arrayListOf()

    override fun onBindViewHolder(holder: SelectSingleViewHolder, position: Int) {

        val row: SelectRow = rows[position]
        holder.title.text = row.title

        if (selected == row.value) {
            holder.selected.visibility = View.VISIBLE
        } else {
            holder.selected.visibility = View.INVISIBLE
        }

        holder.viewHolder.setOnClickListener {
            cellOnClickListener(holder, row)
//            holder.selected.visibility = View.VISIBLE
//            delegate?.delegateCellClick(keyEnum, row.id)
        }
    }

    open fun cellOnClickListener(holder: SelectSingleViewHolder, row: SelectRow) {
        holder.selected.visibility = View.VISIBLE
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

interface MoreDialogDelegate {

    fun delegateCityClick(id: Int){}
}