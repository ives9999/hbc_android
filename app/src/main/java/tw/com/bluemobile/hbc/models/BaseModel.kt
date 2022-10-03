package tw.com.bluemobile.hbc.models

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
}