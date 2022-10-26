package tw.com.bluemobile.hbc.models

import tw.com.bluemobile.hbc.utilities.BASE_URL

class NeedBloodModel: BaseModel() {

    var member_id: Int = 0
    var type: String = "cat"
    var blood_type: String = ""
    var traffic_fee: Int = 0
    var nutrient_fee: Int = 0

    override fun filterRow() {
        super.filterRow()

    }
}