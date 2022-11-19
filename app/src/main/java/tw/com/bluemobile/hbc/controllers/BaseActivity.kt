package tw.com.bluemobile.hbc.controllers

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.awesomedialog.*
import id.ionbit.ionalert.IonAlert
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.dump
import tw.com.bluemobile.hbc.views.Bottom
import tw.com.bluemobile.hbc.views.Top
import tw.com.bluemobile.hbc.routes.*
import tw.com.bluemobile.hbc.utilities.*


open class BaseActivity : AppCompatActivity(), ToMember, ToNeedBlood, To, ToDonateBlood, ToDonate {

    var able_enum: TabEnum = TabEnum.member //每一組頁面，都有一個專屬的代號的enum
    var msg: String = "" //目前使用在傳送錯誤訊息
    lateinit var loading: Loading
    var top: Top? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    open fun add() {
    }

    open fun cancel() {
        prev()
    }

    open fun cellClick(keyEnum: KeyEnum, id: Int) {
        //println(id)
    }

    open fun edit() {
    }

    open fun info(msg: String) {
        AwesomeDialog.build(this)
            .title("訊息")
            .body(msg)
            .icon(R.drawable.ic_info)
            .position(AwesomeDialog.POSITIONS.CENTER)
            .onPositive("關閉")
    }

    open fun init() {
    }

    open fun prev() {
        //hideKeyboard()
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    open fun refresh() {}

    protected fun setBottom(able_enum: TabEnum) {

        findViewById<Bottom>(R.id.bottom) ?. let {
            it.setFocus(packageName, able_enum)
        }
    }

    private fun setTitle(title: String? = null) {
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
            top = itTop
        }

        setTitle(title)
    }

    open fun submit() {

    }

    fun success(msg: String) {
        AwesomeDialog.build(this)
            .title("成功", null, R.color.MY_BLACK)
            .body(msg, null, R.color.MY_BLACK)
            .icon(R.drawable.ic_success)
            .position(AwesomeDialog.POSITIONS.CENTER)
            .onPositive("關閉")
    }

    fun success(msg: String, buttonAction: () -> Unit) {
        AwesomeDialog.build(this)
            .title("成功", null, R.color.MY_BLACK)
            .body(msg, null, R.color.MY_BLACK)
            .icon(R.drawable.ic_success)
            .position(AwesomeDialog.POSITIONS.CENTER)
            .onPositive("關閉") {
                buttonAction()
            }
    }

    fun success(msg: String, actionButtonTitle: String, buttonAction: () -> Unit) {
        AwesomeDialog.build(this)
            .title("成功", null, R.color.MY_BLACK)
            .body(msg, null, R.color.amber_900)
            .icon(R.drawable.ic_success)
            .position(AwesomeDialog.POSITIONS.CENTER)
            .onPositive(actionButtonTitle) {
                buttonAction()
            }
            .onNegative("關閉")
    }

    fun warning(msg: String) {
        AwesomeDialog.build(this)
            .title("警告", null, R.color.MY_BLACK)
            .body(msg, null, R.color.MY_BLACK)
            .icon(R.drawable.ic_warning)
            .position(AwesomeDialog.POSITIONS.CENTER)
            .onPositive("關閉")
    }

    fun warning(msg: String, buttonAction: () -> Unit) {

        AwesomeDialog.build(this)
            .title("警告", null, R.color.MY_BLACK)
            .body(msg, null, R.color.MY_BLACK)
            .icon(R.drawable.ic_warning)
            .position(AwesomeDialog.POSITIONS.CENTER)
            .onPositive("關閉") {
                buttonAction()
            }
    }

    fun warning(msg: String, actionButtonTitle: String, buttonAction: () -> Unit) {
        AwesomeDialog.build(this)
            .title("警告", null, R.color.MY_BLACK)
            .body(msg, null, R.color.amber_900)
            .icon(R.drawable.ic_warning)
            .position(AwesomeDialog.POSITIONS.CENTER)
            .onPositive(actionButtonTitle) {
                buttonAction()
            }
            .onNegative("關閉")
    }

    val memberPetEditAR: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            val i: Intent? = res.data
            if (i != null) {
                refresh()
            }
        }
    }
}





















