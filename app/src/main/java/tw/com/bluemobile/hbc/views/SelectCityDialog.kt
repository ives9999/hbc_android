package tw.com.bluemobile.hbc.views

import android.content.Context
import android.view.View
import tw.com.bluemobile.hbc.data.SelectRow
import tw.com.bluemobile.hbc.utilities.KeyEnum
import tw.com.bluemobile.hbc.utilities.Zones

class SelectCityDialog(
    context: Context,
    screenWidth: Int,
    viewHolderConstructor: selectViewHolder<CitySelectSingleViewHolder>,
    selected: String,
    delegate: MoreDialogDelegate?):
    MoreDialog<CitySelectSingleViewHolder>(context, screenWidth, viewHolderConstructor, KeyEnum.city_id, selected, delegate) {

    init {
        init(false, KeyEnum.city_id.chineseName)
    }

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

class CitySelectSingleViewHolder(view: View, keyEnum: KeyEnum?, private val delegate: MoreDialogDelegate?): SelectSingleViewHolder(view, keyEnum, delegate) {

    override fun rowSetOnclickListener(row: SelectRow) {
        delegate?.delegateCityClick(row.id)
    }
}