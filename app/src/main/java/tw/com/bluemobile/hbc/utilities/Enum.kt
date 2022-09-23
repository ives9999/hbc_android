package tw.com.bluemobile.hbc.utilities

import android.content.res.Resources
import android.widget.ImageView
import tw.com.bluemobile.hbc.extensions.setImage

enum class TabEnum(val englishName: String, val chineseName: String) {
    
    need_blood("need_blood", "我需要寫"),
    donate_blood("donate_blood", "我要捐血"),
    donate("donate", "我要捐款"),
    member("member", "我的資訊");

    companion object {

        fun enumFromString(value: String): TabEnum {
            when (value) {
                "need_blood" -> return need_blood
                "donate_blood" -> return donate_blood
                "donate" -> return donate
                "member" -> return member
            }

            return member
        }
    }

    fun getIconID(resources: Resources, packageName: String): Int {
        return resources.getIdentifier(this.englishName + "TabIcon", "id", packageName)
    }

    fun setIn(it: ImageView) {
        it.setImage(this.englishName + "_in")
    }
}