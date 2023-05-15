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

        val moreDialog = CityDialog(context, screenWidth, selected, delegate)
        moreDialog.setContentView(R.layout.select_single)
        moreDialog.init(false, "縣市")
        moreDialog.show(30)
        moreDialog.rowBridge()

        return moreDialog
    }

}

class CityDialog(context: Context, private val screenWidth: Int, private val selected: String, private val delegate: MoreDialogDelegate?): MoreDialog(context, screenWidth, KeyEnum.city_id, selected, delegate) {

    override fun rowBridge() {
        val citys = Zones.getCitys()

        val thisRows: ArrayList<SelectRow> = arrayListOf()
        for(city in citys) {
            val title = city.name
            val id = city.id
            thisRows.add(SelectRow(id, title, id.toString()))
        }
        setRows(thisRows)
    }
}

//class CityCSelectSingleAdapter(selected: String?, keyEnum: KeyEnum, delegate: MoreDialogDelegate?): SelectSingleAdapter(selected, keyEnum, delegate) {
//
//}