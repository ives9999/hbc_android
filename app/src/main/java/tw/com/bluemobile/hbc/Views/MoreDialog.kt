package tw.com.bluemobile.hbc.Views

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tw.com.bluemobile.hbc.R

class MoreDialog(context: Context, val screenWidth: Int, var key: String? = null): Dialog(context) {

    var city_id: Int? = null

    fun setBottomButtonPadding(bottom_button_count: Int, button_width: Int) {

        val padding: Int = (screenWidth - bottom_button_count * button_width) / (bottom_button_count + 1)

        findViewById<Button>(R.id.submitBtn) ?. let {
            if (it.visibility == View.VISIBLE) {
                val params: ViewGroup.MarginLayoutParams =
                    it.layoutParams as ViewGroup.MarginLayoutParams
                params.width = button_width
                params.marginStart = padding
                it.layoutParams = params
            }
        }

        findViewById<Button>(R.id.cancelBtn) ?. let {
            if (it.visibility == View.VISIBLE) {
                val params: ViewGroup.MarginLayoutParams =
                    it.layoutParams as ViewGroup.MarginLayoutParams
                params.width = button_width
                params.marginStart = padding
                it.layoutParams = params

                it.setOnClickListener {
                    this.dismiss()
                }
            }
        }
    }

    private fun rowBridgeForArea(): ArrayList<SelectRow> {
        val selectRows: ArrayList<SelectRow> = arrayListOf()
        if (city_id != null) {
            val areas: ArrayList<Area> = Global.getAreasByCityID(city_id!!)

            for (area in areas) {
                val title = area.name
                val id = area.id
                selectRows.add(SelectRow(title, id.toString()))
            }
        }

        return selectRows
    }

    private fun rowBridgeForCity(): ArrayList<SelectRow> {
        val citys = Global.getCitys()

        val selectRows: ArrayList<SelectRow> = arrayListOf()

        for(city in citys) {
            val title = city.name
            val id = city.id
            selectRows.add(SelectRow(title, id.toString()))
        }

        return selectRows
    }

    fun setSingleSelect(selected: String, delegate: List1CellDelegate? = null): SingleSelectAdapter {
        val singleSelectAdapter: SingleSelectAdapter = SingleSelectAdapter(selected, delegate)
        val recyclerView: RecyclerView? = this.findViewById<RecyclerView>(R.id.tableView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = singleSelectAdapter

        this.findViewById<Button>(R.id.submitBtn) ?.let {
            it.visibility = View.GONE
        }

        if (key == CITY_KEY) {
            this.topTitleLbl.text = "縣市"
            singleSelectAdapter.rows = rowBridgeForCity()
        } else if (key == AREA_KEY) {
            this.topTitleLbl.text = "區域"
            singleSelectAdapter.rows = rowBridgeForArea()
        }

        return singleSelectAdapter
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