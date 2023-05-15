package tw.com.bluemobile.hbc.views

import android.content.Context
import android.util.AttributeSet
import tw.com.bluemobile.hbc.utilities.HOSPITAL_ID_KEY
import tw.com.bluemobile.hbc.utilities.KeyEnum

class SelectHospital @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    More(context, attrs, defStyleAttr) {

    override fun setOnClickListener(lambda: () -> Unit) {


        valueTV?.setOnClickListener {
            lambda()
        }
    }

    override fun toMoreDialog(
        screenWidth: Int,
        selected: String,
        delegate: MoreDialogDelegate?
    ): MoreDialog {
        val moreDialog = MoreDialog(context, screenWidth, KeyEnum.hospital_id, selected, null)

        return moreDialog
    }
}