package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.data.SelectRow
import tw.com.bluemobile.hbc.models.BaseModels
import tw.com.bluemobile.hbc.models.HospitalModel
import tw.com.bluemobile.hbc.services.HospitalService
import tw.com.bluemobile.hbc.utilities.*
import java.lang.reflect.Type

class SelectHospitalDialog(
    context: Context,
    screenWidth: Int,
    viewHolderConstructor: selectViewHolder<HospitalSelectSingleViewHolder>,
    selected: String,
    delegate: MoreDialogDelegate?):
    MoreDialog<HospitalSelectSingleViewHolder>(context, screenWidth, viewHolderConstructor, KeyEnum.hospital_id, selected, delegate) {

    init {
        val activity = context as BaseActivity
        init(false, KeyEnum.hospital_id.chineseName)
        val params: MutableMap<String, String> = hashMapOf()
        val page: Int = 1
        val perpage: Int = 1

        activity.runOnUiThread {
            activity.loading.show()
        }
        HospitalService.getList(context, params, page, perpage) { success ->
            println(HospitalService.jsonString)
            val modelType: Type = genericType<BaseModels<HospitalModel>>()
            val baseModels = jsonToModelForList<BaseModels<HospitalModel>>(HospitalService.jsonString, modelType)
            val thisRows: ArrayList<SelectRow> = arrayListOf()
            for(hospitalModel in baseModels!!.rows) {
                val title = hospitalModel.name
                val id = hospitalModel.id
                thisRows.add(SelectRow(id, title, id.toString()))
            }
            setRows(thisRows)
            activity.runOnUiThread {
                activity.loading.hide()
                this.show(30)
            }
        }
    }
}

class HospitalSelectSingleViewHolder(view: View, keyEnum: KeyEnum?, private val delegate: MoreDialogDelegate?): SelectSingleViewHolder(view, keyEnum, delegate) {

    override fun rowSetOnclickListener(row: SelectRow) {
        delegate?.delegateHospitalClick(row.id, row.title)
    }
}