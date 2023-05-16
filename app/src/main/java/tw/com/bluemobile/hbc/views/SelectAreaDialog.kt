package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import tw.com.bluemobile.hbc.data.SelectRow
import tw.com.bluemobile.hbc.models.AreaModel
import tw.com.bluemobile.hbc.utilities.KeyEnum
import tw.com.bluemobile.hbc.utilities.Zones

class SelectAreaDialog(
    context: Context,
    screenWidth: Int,
    viewHolderConstructor: selectViewHolder<AreaSelectSingleViewHolder>,
    selected: String,
    private val city_id: Int? = null,
    delegate: MoreDialogDelegate?):
    MoreDialog<AreaSelectSingleViewHolder>(context, screenWidth, viewHolderConstructor, KeyEnum.area_id, selected, delegate) {

    init {
        init(false, KeyEnum.city_id.chineseName)
    }

    override fun rowBridge() {

        if (city_id != null) {
            val areas: ArrayList<AreaModel> = Zones.getAreasByCityID(city_id)

            val thisRows: ArrayList<SelectRow> = arrayListOf()
            for (area in areas) {
                val title = area.name
                val id = area.id
                thisRows.add(SelectRow(id, title, id.toString()))
            }
            setRows(thisRows)
        }
    }
}

class AreaSelectSingleViewHolder(view: View, keyEnum: KeyEnum?, private val delegate: MoreDialogDelegate?): SelectSingleViewHolder(view, keyEnum, delegate) {

    override fun rowSetOnclickListener(row: SelectRow) {
        delegate?.delegateAreaClick(row.id)
    }
}