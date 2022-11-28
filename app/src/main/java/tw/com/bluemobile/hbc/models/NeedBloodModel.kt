package tw.com.bluemobile.hbc.models

import tw.com.bluemobile.hbc.utilities.Zones

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

    var hospital_city_show: String = ""
    var hospital_area_show: String = ""

    override fun filterRow() {
        super.filterRow()

        if (hospital_city_id != null && hospital_city_id > 0) {
            hospital_city_show = Zones.zoneIDToName(hospital_city_id)
        }

        if (hospital_area_id != null && hospital_area_id > 0) {
            hospital_area_show = Zones.zoneIDToName(hospital_area_id)
        }
    }
}