package tw.com.bluemobile.hbc.controllers

import android.graphics.Color
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import tw.com.bluemobile.hbc.R
import tw.com.bluemobile.hbc.compones.Top
import tw.com.bluemobile.hbc.databinding.ActivityMainBinding
import tw.com.bluemobile.hbc.routes.ToMember
import tw.com.bluemobile.hbc.utilities.TabEnum

class NeedBloodActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        able_enum = TabEnum.need_blood
        super.onCreate(savedInstanceState)

//        Thread.sleep(1000)
        val splashScreen = installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        top = findViewById<Top>(R.id.top)
        setTop()
        setBottomTabFocus()
    }
}