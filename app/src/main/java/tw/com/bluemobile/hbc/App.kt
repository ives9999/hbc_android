package tw.com.bluemobile.hbc

import android.app.Application
import tw.com.bluemobile.hbc.models.Member

val member: Member by lazy {
    App.member!!
}
class App: Application() {

    companion object {
        var member: Member? = null
    }

    override fun onCreate() {
        member = Member(applicationContext)
        super.onCreate()
    }
}