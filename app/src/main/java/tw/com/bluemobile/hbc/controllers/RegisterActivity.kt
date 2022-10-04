package tw.com.bluemobile.hbc.controllers

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.example.awesomedialog.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.JsonParseException
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.isInt
import tw.com.bluemobile.hbc.extensions.setLocalImage
import tw.com.bluemobile.hbc.models.MemberModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.*
import java.io.File

class RegisterActivity : BaseActivity() {

    companion object {

        private const val GITHUB_REPOSITORY = "https://github.com/Dhaval2404/ImagePicker"

        private const val PROFILE_IMAGE_REQ_CODE = 101
        private const val GALLERY_IMAGE_REQ_CODE = 102
        private const val CAMERA_IMAGE_REQ_CODE = 103
    }

    private var mCameraUri: Uri? = null
    private var mGalleryUri: Uri? = null
    private var mProfileUri: Uri? = null
//    private var imgProfile: AppCompatImageView? = null
    private var featured: Featured? = null

//    var editTextEmail: EditTextNormal? = null
    private var editTextPassword: EditTextNormal? = null
    var editTextRePassword: EditTextNormal? = null
//    var editTextName: EditTextNormal? = null
//    var editTextNickname: EditTextNormal? = null
//    var editTextMobile: EditTextNormal? = null
    private var moreCity: SelectCity? = null
    var moreArea: SelectArea? = null
//    var editTextRoad: EditTextNormal? = null
//    var editTextLine: EditTextNormal? = null
    private var privacy: Privacy? = null

//    val editTextNormals: ArrayList<EditTextNormal> = arrayListOf()
//    val mores: ArrayList<More> = arrayListOf()
    private val formItems: ArrayList<HashMap<RegisterEnum, MyLayout>> = arrayListOf()

    var moreDialog: MoreDialog? = null

//    var filePath: String = ""

    private val initData: HashMap<String, String> = hashMapOf(
        EMAIL_KEY to "john@housetube.tw",
        PASSWORD_KEY to "1234",
        REPASSWORD_KEY to "1234",
        NAME_KEY to "孫士君",
        NICKNAME_KEY to "孫士君",
        MOBILE_KEY to "0911299998",
        CITY_KEY to "218",
        AREA_KEY to "219",
        ROAD_KEY to "南華街101號8樓",
        LINE_KEY to "ives9999",
        PRIVACY_KEY to "1"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        able_enum = TabEnum.member

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setTop(true, "註冊")

        init()

//        findViewById<AppCompatImageView>(R.id.imgProfile) ?. let {
//            imgProfile = it
//            it.setOnClickListener {
//                featuredSelectDialog()
//            }
//        }
//
//        findViewById<FloatingActionButton>(R.id.fab_add_photo) ?. let {
//            it.setOnClickListener {
//                featuredSelectDialog()
//            }
//        }

        findViewById<LinearLayout>(R.id.submit) ?. let {
            it.setOnClickListener {
                submit()
            }
        }
    }

    // set setting after city and area click.
    override fun cellClick(keyEnum: KeyEnum, id: Int) {
//        println(key)
//        println(id)
        if (keyEnum == KeyEnum.city_id) {
            moreCity?.setText(Zones.zoneIDToName(id))
            moreCity?.setValue(id.toString())
            moreDialog?.hide()
        } else if (keyEnum == KeyEnum.area_id) {
            moreArea?.setText(Zones.zoneIDToName(id))
            moreArea?.setValue(id.toString())
            moreDialog?.hide()
        }
    }

//    private fun featuredSelectDialog() {
//        AwesomeDialog.build(this)
//            .title("選擇圖片", titleColor = ContextCompat.getColor(this, R.color.MY_BLACK))
//            .body("請選擇圖片", color = ContextCompat.getColor(this, R.color.MY_BLACK))
//            .icon(com.github.dhaval2404.imagepicker.R.drawable.ic_photo_black_48dp)
//            .onPositive("從圖庫中選擇") {
//                //println("gallery")
//                pickProfileImage()
//            }
//            .onNegative("開啟相機拍攝") {
//                //println("camera")
//                pickCameraImage()
//            }
//    }

    override fun init() {

        findViewById<Featured>(R.id.featured) ?. let {
            featured = it
            featured!!.setOnImagePickListener(pickProfileImage, pickCameraImage)
        }

        val allEnum: Array<RegisterEnum> = RegisterEnum.getRegisterAllEnum()
        for (enum in allEnum) {
            val key: String = enum.englishName
            val r: Int = resources.getIdentifier(key, "id", packageName)
            findViewById<MyLayout>(r) ?. let {

                if (key == PASSWORD_KEY) {
                    editTextPassword = it as EditTextNormal
                } else if (key == REPASSWORD_KEY) {
                    editTextRePassword = it as EditTextNormal
                } else if (key == CITY_KEY || key == AREA_KEY) {
                    if (key == CITY_KEY) {
                        moreCity = it as SelectCity
                        it.setOnClickListener() {
                            val screenWidth = Global.getScreenWidth(resources)
                            moreDialog = moreCity?.toMoreDialog(screenWidth, it.getValue(), this)
                            //println(moreCity?.value)
                        }

                        it.setOnCancelClickListener {
                            it.clear()
                            moreArea?.clear()
                        }
                    } else {
                        moreArea = it as SelectArea
                        it.setOnClickListener {
                            if (moreCity == null || moreCity!!.getValue().isEmpty()) {
                                warning("請先選擇縣市")
                            } else {
                                val screenWidth = Global.getScreenWidth(resources)
                                val city_id: Int = moreCity?.getValue()?.toInt() ?: 0
                                moreDialog = moreArea?.toMoreDialog(screenWidth, city_id, moreArea!!.getValue(), this)
                            }
                        }

                    }
                } else if (key == PRIVACY_KEY) {
                    privacy = it as Privacy
                    it.setValue("1")
                    privacy!!.setOnCheckChangeListener { isCheck ->
                        if (!isCheck) {
                            warning("必須同意隱私權")
                        }
                    }
                }

                if (initData.containsKey(key)) {
                    it.setValue(initData[key]!!)

                    if (key == CITY_KEY || key == AREA_KEY) {
                        val b = it.setZone()
                        if (!b) {
                            warning("縣市或區域不是整數值")
                        }
                    }

                    if (key == PRIVACY_KEY) {
                        privacy = it as Privacy
                        if (initData[PRIVACY_KEY]!!.isInt()) {
                            val value: Int = initData[PRIVACY_KEY]!!.toInt()
                            it.setValue(value.toString())
                            it.setCheck(true)
                        }
                    }
                }

                val h: HashMap<RegisterEnum, MyLayout> = hashMapOf(enum to it)
                formItems.add(h)
            }
        }

//        var k: String = EMAIL_KEY
//        var r: Int = resources.getIdentifier(k, "id", packageName)
//        findViewById<EditTextNormal>(r) ?. let {
//            editTextEmail = it
//            setEditText(it, k)
//
//            val h: HashMap<String, MyLayout> = hashMapOf(k to it)
//            formItems.add(h)
//        }
//
//        k = PASSWORD_KEY
//        r = resources.getIdentifier(k, "id", packageName)
//        findViewById<EditTextNormal>(r) ?. let {
//            editTextPassword = it
//            setEditText(it, k)
//        }
//
//        k = REPASSWORD_KEY
//        r = resources.getIdentifier(k, "id", packageName)
//        findViewById<EditTextNormal>(r) ?. let {
//            editTextRePassword = it
//            setEditText(it, k)
//        }
//
//        k = NAME_KEY
//        r = resources.getIdentifier(k, "id", packageName)
//        findViewById<EditTextNormal>(r) ?. let {
//            editTextName = it
//            setEditText(it, k)
//        }
//
//        k = NICKNAME_KEY
//        r = resources.getIdentifier(k, "id", packageName)
//        findViewById<EditTextNormal>(R.id.nickname) ?. let {
//            editTextNickname = it
//            setEditText(it, NICKNAME_KEY)
//        }
//
//        k = MOBILE_KEY
//        r = resources.getIdentifier(k, "id", packageName)
//        findViewById<EditTextNormal>(r) ?. let {
//            editTextMobile = it
//            setEditText(it, k)
//        }
//
//        k = CITY_KEY
//        r = resources.getIdentifier(k, "id", packageName)
//        findViewById<More>(r) ?. let {
//            moreCity = it
//            setMore(it, k)
//
//            it.setOnClickListener() {
//                val screenWidth = Global.getScreenWidth(resources)
//                moreDialog = it.toMoreDialog(screenWidth, it.getValue(), this)
//                //println(moreCity?.value)
//            }
//
//            it.setOnCancelClickListener {
//                moreCity?.clear()
//                moreArea?.clear()
//            }
//        }
//
//        k = AREA_KEY
//        r = resources.getIdentifier(k, "id", packageName)
//        findViewById<More>(r) ?. let {
//            moreArea = it
//            setMore(it, k)
//
//            it.setOnClickListener {
//                if (moreCity == null || moreCity!!.getValue().isEmpty()) {
//                    warning("請先選擇縣市")
//                } else {
//                    val screenWidth = Global.getScreenWidth(resources)
//                    moreDialog = it.toMoreDialog(screenWidth, moreCity!!.getValue(), this)
//                }
//            }
//        }
//
//        k = ROAD_KEY
//        r = resources.getIdentifier(k, "id", packageName)
//        findViewById<EditTextNormal>(r) ?. let {
//            editTextRoad = it
//            setEditText(it, k)
//        }
//
//        k = LINE_KEY
//        r = resources.getIdentifier(k, "id", packageName)
//        findViewById<EditTextNormal>(r) ?. let {
//            editTextLine = it
//            setEditText(it, k)
//        }
//
//        k = PRIVACY_KEY
//        r = resources.getIdentifier(k, "id", packageName)
//        findViewById<Privacy>(r) ?. let {
//            privacy = it
//            it.setValue("1")
//
//            it.setOnCheckChangeListener { isCheck ->
//                if (!isCheck) {
//                    warning("必須同意隱私權")
//                }
//            }
//
//            if (initData.containsKey(PRIVACY_KEY)) {
//                if (initData[PRIVACY_KEY]!!.isInt()) {
//                    val value: Int = initData[PRIVACY_KEY]!!.toInt()
//                    it.setValue(value.toString())
//                    it.setCheck(true)
//                }
//            }
//        }
    }

//    private fun inputToParams(editTextNormal: EditTextNormal?, errMsg: String): HashMap<String, String> {
//
//        if (editTextNormal?.isEmpty() == true) {
//            msg += errMsg
//            return hashMapOf()
//        } else {
//            val key: String = editTextNormal!!.getKey()
//            val params: HashMap<String, String> = hashMapOf(key to editTextNormal.getValue())
//            return params
//        }
//    }
//
//    private fun moreToParams(more: More?, errMsg: String): HashMap<String, String> {
//        if (more?.isEmpty() == true) {
//            msg += errMsg
//            return hashMapOf()
//        } else {
//            val key: String = more!!.getKey()
//            val params: HashMap<String, String> = hashMapOf(key to more.getValue())
//            return params
//        }
//    }
//
//    private fun privacyToParams(privacy: Privacy?, errMsg: String): HashMap<String, String> {
//        if (privacy?.getCheck() == false) {
//            msg += errMsg
//            return hashMapOf()
//        } else {
//            val key: String = privacy!!.getKey()
//            val params: HashMap<String, String> = hashMapOf(key to privacy.getValue())
//            return params
//        }
//    }

//    private fun setEditText(it: EditTextNormal, key: String) {
//        editTextNormals.add(it)
//
//        if (initData.containsKey(key)) {
//            it.setValue(initData[key]!!)
//        }
//    }
//
//    private fun setMore(it: More, key: String) {
//        mores.add(it)
//
//        if (initData.containsKey(key)) {
//            val value: String = initData[key]!!
//            it.setValue(value)
//
//            if (value.isInt()) {
//                val text: String = Zones.zoneIDToName(value.toInt())
//                it.setText(text)
//            } else {
//                warning("縣市或區域不是整數值")
//            }
//        }
//    }

    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res: ActivityResult ->
        val resultCode: Int = res.resultCode
        val data: Intent? = res.data

        if (resultCode == Activity.RESULT_OK) {
            if (data != null && data.data != null) {
                //val fileUri: Uri = data.data!!
                mProfileUri = data.data!!
                //mProfileUri = fileUri
                //val a = mProfileUri.toString()
                featured?.setFeatured(mProfileUri!!, true)
                //imgProfile?.setLocalImage(mProfileUri!!, true)
            } else {
                warning("選擇圖片後，回傳為空值")
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            warning(ImagePicker.getError(data))
        } else {
            warning("選取圖片錯誤")
        }
    }

    /**
     * Ref: https://gist.github.com/granoeste/5574148
     */
    //@Suppress("UNUSED_PARAMETER")
    val pickCameraImage: ()-> Unit =  {
        ImagePicker.with(this)
            // User can only capture image from Camera
            .cameraOnly()
            // Image size will be less than 1024 KB
            // .compress(1024)
            //  Path: /storage/sdcard0/Android/data/package/files
            .saveDir(getExternalFilesDir(null)!!)
            //  Path: /storage/sdcard0/Android/data/package/files/DCIM
            .saveDir(getExternalFilesDir(Environment.DIRECTORY_DCIM)!!)
            //  Path: /storage/sdcard0/Android/data/package/files/Download
            .saveDir(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!)
            //  Path: /storage/sdcard0/Android/data/package/files/Pictures
            .saveDir(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
            //  Path: /storage/sdcard0/Android/data/package/files/Pictures/ImagePicker
            .saveDir(File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!, "ImagePicker"))
            //  Path: /storage/sdcard0/Android/data/package/files/ImagePicker
            .saveDir(getExternalFilesDir("ImagePicker")!!)
            //  Path: /storage/sdcard0/Android/data/package/cache/ImagePicker
            .saveDir(File(getExternalCacheDir(), "ImagePicker"))
            //  Path: /data/data/package/cache/ImagePicker
            .saveDir(File(getCacheDir(), "ImagePicker"))
            //  Path: /data/data/package/files/ImagePicker
            .saveDir(File(getFilesDir(), "ImagePicker"))

            // Below saveDir path will not work, So do not use it
            //  Path: /storage/sdcard0/DCIM
            //  .saveDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM))
            //  Path: /storage/sdcard0/Pictures
            //  .saveDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES))
            //  Path: /storage/sdcard0/ImagePicker
            //  .saveDir(File(Environment.getExternalStorageDirectory(), "ImagePicker"))
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
            //.start(CAMERA_IMAGE_REQ_CODE)
    }

    //@Suppress("UNUSED_PARAMETER")
    val pickProfileImage: () -> Unit = {
        ImagePicker.with(this)
            // Crop Square image
            .galleryOnly()
            .cropSquare()
            .saveDir(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
            .setImageProviderInterceptor { imageProvider -> // Intercept ImageProvider
                Log.d("ImagePicker", "Selected ImageProvider: " + imageProvider.name)
            }
            .setDismissListener {
                Log.d("ImagePicker", "Dialog Dismiss")
            }
            // Image resolution will be less than 512 x 512
            .maxResultSize(200, 200)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
            //.start(PROFILE_IMAGE_REQ_CODE)
    }

    override fun submit() {

        val params: MutableMap<String, String> = hashMapOf()

        for (formItem in formItems) {
            for ((enum, layout) in formItem) {
                if (layout.isEmpty()) {
                    msg += enum.errMsg()
                } else {
                    val temp: HashMap<String, String> = hashMapOf(enum.englishName to layout.getValue())
                    params.putAll(temp)
                }
            }
        }

        if (editTextPassword != null && editTextRePassword != null) {
            if (editTextPassword!!.getValue() != editTextRePassword!!.getValue()) {
                msg += "密碼不符合\n"
            }
        }
        //println(params)

//        var temp: HashMap<String, String> = inputToParams(editTextEmail, RegisterEnum.password.errMsg())
//        params.putAll(temp)
//
//        temp = inputToParams(editTextPassword, RegisterEnum.password.errMsg())
//        params.putAll(temp)
//
//        temp = inputToParams(editTextName, RegisterEnum.realname.errMsg())
//        params.putAll(temp)
//
//        temp = inputToParams(editTextNickname, RegisterEnum.nickname.errMsg())
//        params.putAll(temp)
//
//        temp = inputToParams(editTextMobile, RegisterEnum.mobile.errMsg())
//        params.putAll(temp)
//
//        if (editTextRePassword?.getValue()?.isEmpty() == true) {
//            msg += RegisterEnum.repassword.errMsg()
//        }



//        temp = moreToParams(moreCity, RegisterEnum.city_id.errMsg())
//        params.putAll(temp)
//
//        temp = moreToParams(moreArea, RegisterEnum.area_id.errMsg())
//        params.putAll(temp)
//
//        temp = inputToParams(editTextRoad, RegisterEnum.road.errMsg())
//        params.putAll(temp)
//
//        temp = inputToParams(editTextLine, RegisterEnum.line.errMsg())
//        params.putAll(temp)
//
//        temp = privacyToParams(privacy, RegisterEnum.privacy.errMsg())
//        params.putAll(temp)

        if (msg.isNotEmpty()) {
            warning(msg)
            return
        }

        //println(params)

        val featured: String? = mProfileUri?.path

        MemberService.update(this, params, featured) { success ->
            if (success) {
                if (MemberService.success) {
                    runOnUiThread {
                        try {
                            //println(MemberService.jsonString)
                            val registerResModel = Gson().fromJson<RegisterResModel>(
                                MemberService.jsonString,
                                RegisterResModel::class.java
                            )
                            if (registerResModel != null) {
                                if (!registerResModel.success) {
                                    msg = ""
                                    for (error in registerResModel.errors) {
                                        msg += error + "\n"
                                    }
                                    warning(msg)
                                } else {
                                    if (registerResModel.model != null) {
                                        val memberModel: MemberModel = registerResModel.model!!
                                        memberModel.filterRow()
                                        memberModel.dump()
                                        memberModel.zoneModel!!.dump()
                                        //memberModel.toSession(this, true)
//                                        info(msg, "", "關閉") {
//                                            setResult(Activity.RESULT_OK, intent)
//                                            finish()
//                                            //prev()
//                                        }
                                    }
                                }
                            } else {
                                warning("伺服器回傳錯誤，請稍後再試，或洽管理人員")
                            }
                        } catch (e: JsonParseException) {
                            warning(e.localizedMessage!!)
                        }
                    }
                } else {
                    runOnUiThread {
                        warning(MemberService.msg)
                    }
                }
            } else {
                runOnUiThread {
                    warning("伺服器錯誤，請稍後再試，或洽管理人員")
                }
            }
        }

    }

//    private fun isEmpty(resource: Int): Boolean {
//
//        var b: Boolean = true
//        findViewById<EditTextNormal>(resource) ?. let { editTextNormal->
//            if (!editTextNormal.isEmpty()) {
//                b = false
//            }
//        }
//
//        return b
//    }
}


class RegisterResModel {
    var success: Boolean = false
    var errors: ArrayList<String> = arrayListOf()
    var id: Int = 0
    var update: String = ""
    var model: MemberModel? = null
}
