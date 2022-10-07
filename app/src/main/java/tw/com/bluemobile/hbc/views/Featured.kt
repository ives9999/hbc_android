package tw.com.bluemobile.hbc.views

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.example.awesomedialog.*
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.controllers.BaseActivity
import tw.com.bluemobile.hbc.extensions.setImage
import tw.com.bluemobile.hbc.extensions.setLocalImage

class Featured @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
):
    MyLayout(context, attrs, defStyleAttr) {

    private val view: View = View.inflate(context, R.layout.featured, this)
    private var featured: AppCompatImageView? = null

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.Featured, 0, 0)
        }

        view.findViewById<AppCompatImageView>(R.id.featuredIV) ?. let {
            featured = it
        }
    }

    fun setFeatured(url: String, applyCircle: Boolean) {
        val a: Uri = Uri.parse(url)
        setFeatured(a, applyCircle)
//        Picasso.with(context)
//            .load(url)
//            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//            .into(featured)
        //featured?.setImage()
    }

    fun setFeatured(uri: Uri, applyCircle: Boolean) {
        featured?.setLocalImage(uri, true)
    }

    fun setOnImagePickListener(pickProfileImage: () -> Unit, pickCameraImage: () -> Unit) {
        featured?.setOnClickListener {
            sourceSelectDialog(pickProfileImage, pickCameraImage)
        }
    }

    fun sourceSelectDialog(pickProfileImage: () -> Unit, pickCameraImage: () -> Unit) {
        (context as? BaseActivity) ?. let {
            AwesomeDialog.build(it)
                .title("選擇圖片", titleColor = ContextCompat.getColor(it, R.color.MY_BLACK))
                .body("請選擇圖片", color = ContextCompat.getColor(it, R.color.MY_BLACK))
                .icon(com.github.dhaval2404.imagepicker.R.drawable.ic_photo_black_48dp)
                .onPositive("從圖庫中選擇") {
                    //println("gallery")
                    pickProfileImage()
                }
                .onNegative("開啟相機拍攝") {
                    //println("camera")
                    pickCameraImage()
                }
        }
    }
}