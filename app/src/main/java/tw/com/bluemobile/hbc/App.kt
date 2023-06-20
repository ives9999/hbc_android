package tw.com.bluemobile.hbc

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging
import tw.com.bluemobile.hbc.models.Member
import tw.com.bluemobile.hbc.services.MemberService

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

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("fcm token: ${task.result}")
                MemberService.deviceToken(task.result, member!!.token)
            }
        }
    }
}