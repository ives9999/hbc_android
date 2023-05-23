package tw.com.bluemobile.hbc.models

import com.google.gson.annotations.SerializedName
import tw.com.bluemobile.hbc.utilities.DonateBloodEnum
import tw.com.bluemobile.hbc.utilities.NeedBloodEnum
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
    var type_show: String = ""

    @SerializedName("hospital") var hospitalModel: HospitalModel? = null

    override fun filterRow() {
        super.filterRow()
        hospitalModel?.filterRow()

        if (hospital_city_id != null && hospital_city_id > 0) {
            hospital_city_show = Zones.zoneIDToName(hospital_city_id)
        }

        if (hospital_area_id != null && hospital_area_id > 0) {
            hospital_area_show = Zones.zoneIDToName(hospital_area_id)
        }

        val typeEnum: NeedBloodEnum = NeedBloodEnum.enumFromString(type)
        type_show = typeEnum.DBNameToRadioText(type)
    }
}