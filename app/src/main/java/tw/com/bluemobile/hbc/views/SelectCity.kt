package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.data.SelectRow
import tw.com.bluemobile.hbc.utilities.KeyEnum
import tw.com.bluemobile.hbc.utilities.Zones

class SelectCity @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    More(context, attrs, defStyleAttr) {

    override fun toMoreDialog(screenWidth: Int, selected: String, delegate: MoreDialogDelegate?): MoreDialog {

        val moreDialog = CityDialog(context, screenWidth, selected)
        moreDialog.delegate = delegate
        moreDialog.setContentView(R.layout.select_single)
        moreDialog.init(false, "縣市")
        //moreDialog.init(false, keyEnum.chineseName)
        moreDialog.setAdapter()
        moreDialog.show(30)

        return moreDialog
    }

}

class CityDialog(context: Context, private val screenWidth: Int, private val selected: String): MoreDialog(context, screenWidth, KeyEnum.city_id, selected) {

    override fun setAdapter(): SelectSingleAdapter {
        val selectSingleAdapter: CityCSelectSingleAdapter = CityCSelectSingleAdapter(selected, KeyEnum.city_id, delegate)

        val recyclerView: RecyclerView? = this.findViewById<RecyclerView>(R.id.tableView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = selectSingleAdapter

        selectSingleAdapter.rows = rowBridge()

        return selectSingleAdapter
    }
    override fun rowBridge(): ArrayList<SelectRow> {
        val citys = Zones.getCitys()

        for(city in citys) {
            val title = city.name
            val id = city.id
            selectRows.add(SelectRow(id, title, id.toString()))
        }

        return selectRows
    }
}

class CityCSelectSingleAdapter(selected: String?, keyEnum: KeyEnum, delegate: MoreDialogDelegate?): SelectSingleAdapter(selected, keyEnum, delegate) {

    override fun cellOnClickListener(holder: SelectSingleViewHolder, row: SelectRow) {
        super.cellOnClickListener(holder, row)
        delegate?.delegateCityClick(row.id)
    }
}