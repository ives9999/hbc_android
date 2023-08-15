package tw.com.bluemobile.hbc.controllers

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import tw.com.bluemobile.hbc.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // This is used to hide the status bar and make the splash screen as a full screen activity
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        // we used the postDelayed(Runnable, time) method to send a message with a delayed time.
        // Normal Handler is deprecated, so we have to change the code little bit

        // Handler().postDelayed((
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, NeedBloodActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}















