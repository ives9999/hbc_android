package tw.com.bluemobile.hbc.controllers

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.ionbit.ionalert.IonAlert
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.views.Bottom
import tw.com.bluemobile.hbc.views.Top
import tw.com.bluemobile.hbc.routes.*
import tw.com.bluemobile.hbc.utilities.KeyEnum
import tw.com.bluemobile.hbc.utilities.TabEnum

var able_enum: TabEnum = TabEnum.member //每一組頁面，都有一個專屬的代號的enum
var msg: String = "" //目前使用在傳送錯誤訊息

open class BaseActivity : AppCompatActivity(), ToMember, ToNeedBlood, To {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    open fun cancel() {
        prev()
    }

    open fun cellClick(keyEnum: KeyEnum, id: Int) {
        //println(id)
    }

    open fun init() {
        //top.showPrev(isPrevIconShow)
    }

    open fun prev() {
        //hideKeyboard()
        val intent = Intent()
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    protected fun setBottom() {

        findViewById<Bottom>(R.id.bottom) ?. let {
            it.setFocus(packageName)
        }
    }

    protected fun setTitle(title: String? = null) {
        findViewById<Top>(R.id.top) ?. let { itTop ->
            title?.let {
                itTop.setTitle(it)
            } ?: run {
                itTop.setTitle(able_enum.chineseName)
            }
        }
    }

    protected fun setTop(isPrevShow: Boolean = false, title: String? = null) {

        findViewById<Top>(R.id.top) ?. let { itTop->
            itTop.showPrev(isPrevShow)
        }

        setTitle(title)
    }

    open fun submit() {

    }

    fun warning(msg: String) {
        val box = IonAlert(this, IonAlert.WARNING_TYPE)
        box.titleText = "警吿"
        box.contentText = msg
        box.confirmText = "關閉"
        box.show()
    }

    fun warning(msg: String, buttonAction: () -> Unit) {
        val box = IonAlert(this, IonAlert.WARNING_TYPE)
        box.titleText = "警吿"
        box.contentText = msg
        box.confirmText = "關閉"

        box.setConfirmClickListener {
            buttonAction()
            it.cancel()
        }
        box.show()
    }

    fun warning(msg: String, cancelButtonTitle: String, buttonAction: () -> Unit) {
        val box = IonAlert(this, IonAlert.WARNING_TYPE)
        box.titleText = "警吿"
        box.contentText = msg
        box.confirmText = "關閉"
        box.cancelText = cancelButtonTitle
        box.showCancelButton(true)

        box.setConfirmClickListener {
            buttonAction()
            it.cancel()
        }
        box.show()
    }
}




















