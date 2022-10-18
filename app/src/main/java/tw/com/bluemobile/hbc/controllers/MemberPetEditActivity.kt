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
import tw.com.bluemobile.hbc.models.MemberModel
import tw.com.bluemobile.hbc.models.MemberPetModel
import tw.com.bluemobile.hbc.models.SuccessModel
import tw.com.bluemobile.hbc.services.MemberService
import tw.com.bluemobile.hbc.utilities.Loading
import tw.com.bluemobile.hbc.utilities.MemberPetEnum
import tw.com.bluemobile.hbc.utilities.RegisterEnum
import tw.com.bluemobile.hbc.utilities.jsonToModel
import tw.com.bluemobile.hbc.views.EditTextNormal
import tw.com.bluemobile.hbc.views.MyLayout
import tw.com.bluemobile.hbc.views.TwoRadio
import tw.com.bluemobile.hbc.views.UploadImage
import java.io.File
import java.lang.Exception

class MemberPetEditActivity : BaseActivity() {

    var memberPetToken: String? = null
    var bloodImage: UploadImage? = null
    var bodyImge: UploadImage? = null
    var bloodImageUri: Uri? = null
    var bodyImageUri: Uri? = null

    var imagePickerKey: MemberPetEnum? = null
    private val formItems: ArrayList<HashMap<MemberPetEnum, MyLayout>> = arrayListOf()

    var typeRadio: TwoRadio? = null
    var iDoRadio: TwoRadio? = null

    private val initData: HashMap<String, String> = hashMapOf(
//        MemberPetEnum.petName.englishName to "幸運貓",
//        MemberPetEnum.type.englishName to "狗",
//        MemberPetEnum.age.englishName to "5",
//        MemberPetEnum.weight.englishName to "10",
//        MemberPetEnum.blood_type.englishName to "A",
//        MemberPetEnum.IDo.englishName to "願意",
//        MemberPetEnum.traffic_fee.englishName to "100",
//        MemberPetEnum.nutrient_fee.englishName to "200"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_pet_edit)

        if (intent.hasExtra("memberPetToken")) {
            memberPetToken = intent.getStringExtra("memberPetToken")
            //println(memberPetToken)
        }

        setTop(true, "我的寶貝")

        init()
    }

    override fun init() {
        super.init()
        loading = Loading(this)

        val allEnums: ArrayList<MemberPetEnum> = MemberPetEnum.getAllEnum()
        for (enum in allEnums) {

            val key: String = enum.englishName
            val r: Int = resources.getIdentifier(enum.englishName, "id", packageName)
            findViewById<MyLayout>(r) ?. let {

                if (enum == MemberPetEnum.blood_image) {
                    bloodImage = it as UploadImage

                    imagePickerKey = MemberPetEnum.blood_image
                    bloodImage!!.setOnImagePickListener(pickImage, pickCameraImage)
                } else if (enum == MemberPetEnum.body_image) {
                    bodyImge = it as UploadImage

                    imagePickerKey = MemberPetEnum.body_image
                    bodyImge!!.setOnImagePickListener(pickImage, pickCameraImage)
                } else if (enum == MemberPetEnum.type) {
                    typeRadio = it as TwoRadio
                    //use typeRadio.value can get radio value
                    //it.setOnGroupCheckedChangeListener(typeLambda)

                    if (initData.containsKey(key)) {
                        it.setCheck(initData[key]!!)
                    }

                    val h: HashMap<MemberPetEnum, MyLayout> = hashMapOf(enum to it)
                    formItems.add(h)
                } else if (enum == MemberPetEnum.IDo) {
                    iDoRadio = it as TwoRadio
                    //it.setOnGroupCheckedChangeListener(iDoLambda)

                    if (initData.containsKey(key)) {
                        it.setCheck(initData[key]!!)
                    }

                    val h: HashMap<MemberPetEnum, MyLayout> = hashMapOf(enum to it)
                    formItems.add(h)
                } else {
                    val h: HashMap<MemberPetEnum, MyLayout> = hashMapOf(enum to it)
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
                if (imagePickerKey == MemberPetEnum.blood_image) {
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
                    var v = layout.value
                    if (enum == MemberPetEnum.type || enum == MemberPetEnum.IDo) {
                        v = enum.radioTextToDBName(v)
                    }
                    val temp: HashMap<String, String> =
                        hashMapOf(k to v)
                    params.putAll(temp)
                }
            }
        }

//        println(params)

        loading.show()
        MemberService.pet(this, params, bloodImageUri?.path, bodyImageUri?.path) { success ->
            if (success) {
                runOnUiThread {
                    try {
                        //println(MemberService.jsonString)
                        val successModel =
                            jsonToModel<SuccessModel<MemberPetModel>>(MemberService.jsonString)
                        if (successModel != null) {
                            if (successModel.success) {
                                val memberPetModel = successModel.model
                                success("新增/修改 我的寶貝成功") {
                                    prev()
                                }
                            } else {
                                warning(successModel.msg)
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