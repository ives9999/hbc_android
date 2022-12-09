package tw.com.bluemobile.hbc.controllers

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.github.dhaval2404.imagepicker.ImagePicker
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.extensions.parseErrmsg
import tw.com.bluemobile.hbc.models.BaseModel
import tw.com.bluemobile.hbc.models.DonateBloodModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.DonateBloodService
import tw.com.bluemobile.hbc.utilities.*
import tw.com.bluemobile.hbc.views.MyLayout
import tw.com.bluemobile.hbc.views.ThreeRadio
import tw.com.bluemobile.hbc.views.TwoRadio
import tw.com.bluemobile.hbc.views.UploadImage
import java.io.File
import java.lang.Exception
import java.lang.reflect.Type

class DonateBloodEditActivity : EditActivity() {

    var donateBloodToken: String? = null
    var donateBloodModel: DonateBloodModel? = null

    var bloodImage: UploadImage? = null
    var bodyImge: UploadImage? = null
    var bloodImageUri: Uri? = null
    var bodyImageUri: Uri? = null

    var imagePickerKey: DonateBloodEnum? = null
    private val formItems: ArrayList<HashMap<DonateBloodEnum, MyLayout>> = arrayListOf()

    var typeRadio: TwoRadio? = null
    var catBloodTypeRadio: ThreeRadio? = null
    var dogBloodTypeRadio: TwoRadio? = null
    var iDoRadio: TwoRadio? = null

    var type: String = ""
    var blood_type: String = ""

    private val initData: MutableMap<String, String> = mutableMapOf(
//         DonateBloodEnum.petName.englishName to "幸運貓",
        DonateBloodEnum.type.englishName to "狗",
        DonateBloodEnum.blood_type_cat.englishName to "DEA1陰性",
//         DonateBloodEnum.age.englishName to "5",
//         DonateBloodEnum.weight.englishName to "10",
        DonateBloodEnum.IDo.englishName to "願意",
//         DonateBloodEnum.traffic_fee.englishName to "100",
//         DonateBloodEnum.nutrient_fee.englishName to "200"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_blood_edit)

        if (intent.hasExtra("donateBloodToken")) {
            donateBloodToken = intent.getStringExtra("donateBloodToken")
            //println(memberPetToken)
        }

        setTop(true, "我的寶貝")

        loading = Loading(this)
        if (donateBloodToken != null) {
            refresh()
        } else {
            init()
        }
    }

    override fun refresh() {

        super.refresh()

        loading.show()
        val params: HashMap<String, String> = hashMapOf("token" to donateBloodToken!!)
        DonateBloodService.getOne(this, params) { success ->
            if (success) {
                val modelType: Type = genericType<SuccessModel<DonateBloodModel>>()
                donateBloodModel = parseJSONAndInit<DonateBloodModel>(DonateBloodService.jsonString, modelType)
            }
        }
    }

    override fun init() {
        super.init()

        val allEnums: ArrayList<DonateBloodEnum> = DonateBloodEnum.getAllEnum()
        for (enum in allEnums) {

            val key: String = enum.englishName
            val r: Int = resources.getIdentifier(enum.englishName, "id", packageName)
            findViewById<MyLayout>(r) ?. let {

                if (enum == DonateBloodEnum.blood_image) {
                    bloodImage = it as UploadImage
                    bloodImage!!.setOnImagePickListener(key, pickImage, pickCameraImage)

                    if (initData.containsKey(key)) {
                        val n = initData[key]!!
                        it.setImage(initData[key]!!, false)
                    }
                } else if (enum == DonateBloodEnum.body_image) {
                    bodyImge = it as UploadImage
                    bodyImge!!.setOnImagePickListener(key, pickImage, pickCameraImage)

                    if (initData.containsKey(key)) {
                        it.setImage(initData[key]!!, false)
                    }
                } else if (enum == DonateBloodEnum.type) {
                    typeRadio = it as TwoRadio

                    it.setOnGroupCheckedChangeListener { newType ->
                        typeChange(newType)
                    }

                    if (initData.containsKey(key)) {
                        it.setCheck(enum.DBNameToRadioText(initData[key]!!))
                        type = it.value
                    }

//                    val h: HashMap<DonateBloodEnum, MyLayout> = hashMapOf(enum to it)
//                    formItems.add(h)
                } else if (enum == DonateBloodEnum.blood_type_cat) {
                    catBloodTypeRadio = it as ThreeRadio

//                    val h: HashMap<DonateBloodEnum, MyLayout> = hashMapOf(enum to it)
//                    formItems.add(h)

                } else if (enum == DonateBloodEnum.blood_type_dog) {
                    dogBloodTypeRadio = it as TwoRadio

//                    val h: HashMap<DonateBloodEnum, MyLayout> = hashMapOf(enum to it)
//                    formItems.add(h)

                } else if (enum == DonateBloodEnum.IDo) {
                    iDoRadio = it as TwoRadio
                    //it.setOnGroupCheckedChangeListener(iDoLambda)

                    if (initData.containsKey(key)) {
                        it.setCheck(enum.DBNameToRadioText(initData[key]!!))
                    }

                    val h: HashMap<DonateBloodEnum, MyLayout> = hashMapOf(enum to it)
                    formItems.add(h)
                } else {
                    val h: HashMap<DonateBloodEnum, MyLayout> = hashMapOf(enum to it)
                    formItems.add(h)
                }

                if (initData.containsKey(key)) {
                    it.value = initData[key]!!
                }
            }
        }

        initData[DonateBloodEnum.type.englishName]?.let {
            typeChange(it)
        }

        dogBloodTypeRadio?.setOnGroupCheckedChangeListener(dogChanged)
        catBloodTypeRadio?.setOnGroupCheckedChangeListener(catChanged)

        if (donateBloodModel != null) {
            top!!.setTitle(donateBloodModel!!.name)
        }

        findViewById<LinearLayout>(R.id.submitLL) ?. let {
            it.setOnClickListener {
                submit()
            }
        }
    }

    override fun modelToInitData(model: BaseModel) {

        val donateBloodModel = model as DonateBloodModel
        for (enum in DonateBloodEnum.getAllEnum()) {
            val name = enum.englishName
            val value = getPropertyValue(donateBloodModel, name)
            initData.put(name, value)
        }
    }

    private fun typeChange(type: String) {
        if (type == "狗") {
            dogBloodTypeRadio?.visibility = View.VISIBLE
            catBloodTypeRadio?.visibility = View.GONE

            if (initData[DonateBloodEnum.type.englishName] == type) {
                dogBloodTypeRadio?.setCheck(DonateBloodEnum.blood_type_dog.englishName)
                blood_type = dogBloodTypeRadio?.value.toString()
            } else {
                dogBloodTypeRadio?.setCheck("DEA1陽性")
                blood_type = "DEA1陽性"
            }

            this.type = "dog"
        } else {
            dogBloodTypeRadio?.visibility = View.GONE
            catBloodTypeRadio?.visibility = View.VISIBLE

            if (initData[DonateBloodEnum.type.englishName] == type) {
                catBloodTypeRadio?.setCheck(DonateBloodEnum.blood_type_cat.englishName)
                blood_type = catBloodTypeRadio?.value.toString()
            } else {
                catBloodTypeRadio?.setCheck("A")
                blood_type = "A"
            }

            this.type = "cat"
        }
    }

    private val dogChanged: (String)-> Unit = {
        this.blood_type = it
    }

    private val catChanged: (String)-> Unit = {
        this.blood_type = it
    }

    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res: ActivityResult ->
        val resultCode: Int = res.resultCode
        val data: Intent? = res.data

        if (resultCode == Activity.RESULT_OK) {
            if (data != null && data.data != null) {
                val mProfileUri = data.data!!
                if (imagePickerKey == DonateBloodEnum.blood_image) {
                    bloodImage?.setImage(mProfileUri, false)
                    bloodImageUri = mProfileUri
                } else {
                    bodyImge?.setImage(mProfileUri, false)
                    bodyImageUri = mProfileUri
                }
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
                //intent.putExtra("key", key)
                startForProfileImageResult.launch(intent)
            }
        //.start(CAMERA_IMAGE_REQ_CODE)
    }

    val pickImage: (String) -> Unit = { key ->
        ImagePicker.with(this)
            // Crop Square image
            .galleryOnly()
            .cropSquare()
            .saveDir(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
            .setImageProviderInterceptor { imageProvider -> // Intercept ImageProvider
                //Log.d("ImagePicker", "Selected ImageProvider: " + imageProvider.name)
            }
            .setDismissListener {
                //Log.d("ImagePicker", "Dialog Dismiss")
            }
            // Image resolution will be less than 512 x 512
            .maxResultSize(200, 200)
            .createIntent { intent ->
                imagePickerKey = DonateBloodEnum.enumFromString(key)
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
                    val k = enum.englishName
                    var v = layout.value
                    if (enum == DonateBloodEnum.IDo) {
                        v = enum.radioTextToDBName(v)
                    }

//                    if (type == "cat" && enum == DonateBloodEnum.blood_type_cat) {
//                        v = enum.radioTextToDBName(v)
//                    }
//
//                    if (type == "dog" && enum == DonateBloodEnum.blood_type_dog) {
//                        v = enum.radioTextToDBName(v)
//                    }

                    val temp: HashMap<String, String> =
                        hashMapOf(k to v)
                    params.putAll(temp)
                }
            }
        }

        if (msg.isNotEmpty()) {
            warning(msg)
            return
        }

        params[DonateBloodEnum.type.englishName] = type
        params[DonateBloodEnum.blood_type.englishName] = blood_type

//        type = params[DonateBloodEnum.type.englishName]!!
//        if (type == "貓" && params.containsKey(DonateBloodEnum.blood_type_cat.englishName)) {
//            params[DonateBloodEnum.blood_type.englishName] = params[DonateBloodEnum.blood_type_cat.englishName]!!
//            val n = params.remove(DonateBloodEnum.blood_type_cat.englishName)
//        } else if (type == "狗" && params.containsKey(DonateBloodEnum.blood_type_dog.englishName)) {
//            params[DonateBloodEnum.blood_type.englishName] = params[DonateBloodEnum.blood_type_dog.englishName]!!
//            val n = params.remove(DonateBloodEnum.blood_type_dog.englishName)
//        }

        if (donateBloodToken != null) {
            params.put("token", donateBloodToken!!)
        }

        //println(params)

        loading.show()
        DonateBloodService.postUpdate(this, params, bloodImageUri?.path, bodyImageUri?.path) { success ->
            if (success) {
                runOnUiThread {
                    try {
                        //println(DonateBloodService.jsonString)
                        val successModel =
                            jsonToModel<SuccessModel<DonateBloodModel>>(DonateBloodService.jsonString)
                        if (successModel != null) {
                            if (successModel.success) {
                                val donateBloodModel = successModel.model
                                success("新增/修改 我的寶貝成功") {
                                    prev()
                                }
                            } else {
                                warning(successModel.msgs.parseErrmsg())
                            }
                        } else {
                            warning("app無法解析系統傳回值，請洽管理員")
                        }
                    } catch (e: Exception) {
                        warning(e.localizedMessage)
                    }
                }
            } else {
                runOnUiThread {
                    warning("伺服器錯誤，請稍後再試，或洽管理人員")
                }
            }

            runOnUiThread {
                loading.hide()
            }
        }
    }
}