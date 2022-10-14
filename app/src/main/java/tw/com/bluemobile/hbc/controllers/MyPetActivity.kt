package tw.com.bluemobile.hbc.controllers

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.github.dhaval2404.imagepicker.ImagePicker
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.MyPetEnum
import tw.com.bluemobile.hbc.utilities.RegisterEnum
import tw.com.bluemobile.hbc.views.EditTextNormal
import tw.com.bluemobile.hbc.views.MyLayout
import tw.com.bluemobile.hbc.views.TwoRadio
import tw.com.bluemobile.hbc.views.UploadImage
import java.io.File

class MyPetActivity : BaseActivity() {

    var bloodImage: UploadImage? = null
    var bodyImge: UploadImage? = null
    var bloodImageUri: Uri? = null
    var bodyImageUri: Uri? = null

    var imagePickerKey: MyPetEnum? = null
    private val formItems: ArrayList<HashMap<MyPetEnum, MyLayout>> = arrayListOf()

    var typeRadio: TwoRadio? = null
    var iDoRadio: TwoRadio? = null

    private val initData: HashMap<String, String> = hashMapOf(
        MyPetEnum.petName.englishName to "幸運貓",
        MyPetEnum.type.englishName to "狗",
        MyPetEnum.age.englishName to "5",
        MyPetEnum.weight.englishName to "10",
        MyPetEnum.blood_type.englishName to "A",
        MyPetEnum.iDo.englishName to "願意",
        MyPetEnum.traffic_fee.englishName to "100",
        MyPetEnum.nutrient_fee.englishName to "200"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_pet)

        setTop(true, "我的寶貝")

        init()
    }

    override fun init() {
        super.init()
        loading = Loading(this)

        val allEnums: ArrayList<MyPetEnum> = MyPetEnum.getAllEnum()
        for (enum in allEnums) {

            val key: String = enum.englishName
            val r: Int = resources.getIdentifier(enum.englishName, "id", packageName)
            findViewById<MyLayout>(r) ?. let {

                if (enum == MyPetEnum.blood_image) {
                    bloodImage = it as UploadImage

                    imagePickerKey = MyPetEnum.blood_image
                    bloodImage!!.setOnImagePickListener(pickImage, pickCameraImage)
                } else if (enum == MyPetEnum.body_image) {
                    bodyImge = it as UploadImage

                    imagePickerKey = MyPetEnum.body_image
                    bodyImge!!.setOnImagePickListener(pickImage, pickCameraImage)
                } else if (enum == MyPetEnum.type) {
                    typeRadio = it as TwoRadio
                    //use typeRadio.value can get radio value
                    //it.setOnGroupCheckedChangeListener(typeLambda)

                    if (initData.containsKey(key)) {
                        it.setCheck(initData[key]!!)
                    }

                    val h: HashMap<MyPetEnum, MyLayout> = hashMapOf(enum to it)
                    formItems.add(h)
                } else if (enum == MyPetEnum.iDo) {
                    iDoRadio = it as TwoRadio
                    //it.setOnGroupCheckedChangeListener(iDoLambda)

                    if (initData.containsKey(key)) {
                        it.setCheck(initData[key]!!)
                    }

                    val h: HashMap<MyPetEnum, MyLayout> = hashMapOf(enum to it)
                    formItems.add(h)
                } else {
                    val h: HashMap<MyPetEnum, MyLayout> = hashMapOf(enum to it)
                    formItems.add(h)
                }

                if (initData.containsKey(key)) {
                    it.value = initData[key]!!
                }
            }
        }

        findViewById<LinearLayout>(R.id.submitLL) ?. let {
            it.setOnClickListener {
                submit()
            }
        }
    }

//    private val iDoLambda: (String)-> Unit = {
//        println(it)
//    }
//
//    private val typeLambda: (String)-> Unit = {
//        println(it)
//    }

    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res: ActivityResult ->
        val resultCode: Int = res.resultCode
        val data: Intent? = res.data

        if (resultCode == Activity.RESULT_OK) {
            if (data != null && data.data != null) {
                val mProfileUri = data.data!!
                if (imagePickerKey == MyPetEnum.blood_image) {
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

    val pickImage: () -> Unit = {
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
                    val v = layout.value
                    if (layout.visibility == View.VISIBLE) {
                        val temp: HashMap<String, String> =
                            hashMapOf(enum.englishName to layout.value)
                        params.putAll(temp)
                    }
                }
            }
        }

        println(params)
    }
}