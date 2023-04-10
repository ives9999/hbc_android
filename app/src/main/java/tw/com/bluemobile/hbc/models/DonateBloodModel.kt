package tw.com.bluemobile.hbc.models

import tw.com.bluemobile.hbc.utilities.BASE_URL
import tw.com.bluemobile.hbc.utilities.DonateBloodEnum
import tw.com.bluemobile.hbc.utilities.NeedBloodEnum

class DonateBloodModel: BaseModel() {
    var member_id: Int = 0
    var type: String = "cat"
    var age: Int = 0
    var weight: Int = 0
    var blood_type: String = ""
    var IDo: Int = 0
    var traffic_fee: Int = 0
    var nutrient_fee: Int = 0
    var blood_image: String = ""
    var body_image: String = ""
    var member_token: String = ""
    var member_nickname: String = ""

    var order_token: String = ""
    var memberA_token: String = ""
    var memberB_token: String = ""

    var type_show: String = ""

    override fun filterRow() {
        super.filterRow()

        if (blood_image != null && blood_image.isNotEmpty()) {
            if (!blood_image.startsWith("http://") && !blood_image.startsWith("https://")) {
                blood_image = BASE_URL + blood_image
                //print(featured_path)
            }
        } else {
            blood_image = "$BASE_URL/images/nophoto.png"
        }

        if (body_image != null && body_image.isNotEmpty()) {
            if (!body_image.startsWith("http://") && !body_image.startsWith("https://")) {
                body_image = BASE_URL + body_image
                //print(featured_path)
            }
        } else {
            body_image = "$BASE_URL/images/nophoto.png"
        }

        val typeEnum: DonateBloodEnum = DonateBloodEnum.enumFromString(type)
        type_show = typeEnum.DBNameToRadioText(type)
    }
}