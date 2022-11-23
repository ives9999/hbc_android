package tw.com.bluemobile.hbc.models

class NeedBloodModel: BaseModel() {

    var hospital_name: String = ""
    var hospital_city_id: Int = 0
    var hospital_area_id: Int = 0
    var hospital_road: String = ""
    var member_id: Int = 0
    var type: String = "cat"
    var blood_type: String = ""
    var traffic_fee: Int = 0
    var nutrient_fee: Int = 0
    var member_token: String = ""
    var member_nickname: String = ""

    var order_token: String = ""
    var memberA_token: String = ""
    var memberB_token: String = ""

    override fun filterRow() {
        super.filterRow()
    }
}