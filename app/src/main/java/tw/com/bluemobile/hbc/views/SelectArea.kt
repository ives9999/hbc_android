package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.utilities.KeyEnum

class SelectArea @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    More(context, attrs, defStyleAttr) {

    fun toMoreDialog(screenWidth: Int, city_id: Int, selected: String, delegate: BaseActivity): MoreDialog {

        val moreDialog = MoreDialog(context, screenWidth, KeyEnum.area_id, selected, null)
        //moreDialog.city_id = city_id
        moreDialog.setContentView(R.layout.select_single)
        moreDialog.init(false, keyEnum.chineseName)
        moreDialog.show(30)

        return moreDialog
    }
}