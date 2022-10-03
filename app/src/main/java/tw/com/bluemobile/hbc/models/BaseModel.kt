package tw.com.bluemobile.hbc.models

import tw.com.bluemobile.hbc.extensions.mobileShow
import tw.com.bluemobile.hbc.extensions.noTime
import tw.com.bluemobile.hbc.extensions.telShow
import tw.com.bluemobile.hbc.utilities.BASE_URL
import kotlin.reflect.full.memberProperties
import tw.com.bluemobile.hbc.utilities.Zones

abstract class BaseModels<T: BaseModel> {
    var success: Boolean = true
    var msg: String = ""
    var page: Int = 0
    var totalCount: Int = 0
    var perPage: Int = 0
    var rows: ArrayList<T> = arrayListOf()
}

abstract class BaseModel {
    var id: Int = -1
    var name: String = ""
    var title: String = ""
    var tel: String = ""
    var mobile: String = ""
    var city_id: Int = -1
    var status: String = "online"
    var token: String = ""
    var content: String = ""
    var sort_order: Int = 0
    var pv: Int = 0
    var created_id: Int = 0
    var featured_path: String = ""
    var created_at: String = ""
    var updated_at: String = ""

    var created_at_show: String = ""
    var updated_at_show: String = ""

    var address: String = ""
    var city_show: String = ""
    var mobile_show: String = ""
    var tel_show: String = ""
    var status_show: String = "上線"

    var no: Int = 1
    var selected: Boolean = false

    open fun filterRow() {
        if (featured_path != null && featured_path.isNotEmpty()) {
            if (!featured_path.startsWith("http://") && !featured_path.startsWith("https://")) {
                featured_path = BASE_URL + featured_path
                //print(featured_path)
            }
        } else {
            featured_path = BASE_URL + "/imgs/nophoto.png"
        }

        if (name == null) { name = "" }
        if (title == null) { title = "" }
        if (tel == null) { tel = "" }
        if (mobile == null) { mobile = "" }

        if (city_id > 0) {
            city_show = Zones.zoneIDToName(city_id)
        }

        if (mobile != null && mobile.isNotEmpty()) {
            mobile_show = mobile.mobileShow()
        }

        if (tel != null && tel.isNotEmpty()) {
            tel_show = tel.telShow()
        }

        if (created_at.isNotEmpty()) {
            created_at_show = created_at.noTime()
        }

        if (updated_at.isNotEmpty()) {
            updated_at_show = updated_at.noTime()
        }
        if (content == null) {
            content = ""
        }

//        if (status != null) {
//            status_show = STATUS.from(status).value
//        }
    }

    open fun dump() {
        val kc = this::class
        kc.memberProperties.forEach {
            println("${it.name}: ${it.getter.call(this).toString()}")
        }
    }
}