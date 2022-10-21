package tw.com.bluemobile.hbc.views

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.example.awesomedialog.*
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.extensions.setLocalImage

class UploadImage @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.upload_image, this)
    private var image: AppCompatImageView? = null

    init {
        attrs?.let { attributeSet ->
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.UploadImage, 0, 0)

            view.findViewById<TextView>(R.id.titleTV) ?. let {
                titleTV = it
                it.text = typedArray.getString(R.styleable.UploadImage_uploadImageTitleTV) ?: ""
            }

            view.findViewById<ImageView>(R.id.starIV) ?. let {
                starIV = it
                val b: Boolean = typedArray.getBoolean(R.styleable.UploadImage_uploadImageStar, true)
                if (!b) {
                    starIV!!.visibility = View.GONE
                }
            }

            key = typedArray.getString(R.styleable.UploadImage_uploadImageKey) ?: ""
        }



        view.findViewById<AppCompatImageView>(R.id.imageIV) ?. let {
            image = it
        }
    }

    fun setImage(uri: Uri, applyCircle: Boolean) {
        image?.setLocalImage(uri, applyCircle)
    }

    fun setOnImagePickListener(key: String, pickImage: (key: String) -> Unit, pickCameraImage: () -> Unit) {
        this.key = key
        image?.setOnClickListener {
            sourceSelectDialog(pickImage, pickCameraImage)
        }
    }

    private fun sourceSelectDialog(pickImage: (key: String) -> Unit, pickCameraImage: () -> Unit) {
        (context as? BaseActivity) ?. let {
            AwesomeDialog.build(it)
                .title("選擇圖片", titleColor = ContextCompat.getColor(it, R.color.MY_BLACK))
                .body("請選擇圖片", color = ContextCompat.getColor(it, R.color.MY_BLACK))
                .icon(com.github.dhaval2404.imagepicker.R.drawable.ic_photo_black_48dp)
                .onPositive("從圖庫中選擇") {
                    //println("gallery")
                    pickImage.invoke(key)
                }
                .onNegative("開啟相機拍攝") {
                    //println("camera")
                    pickCameraImage.invoke()
                }
        }
    }
}