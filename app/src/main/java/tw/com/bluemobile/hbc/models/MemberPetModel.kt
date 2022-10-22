package tw.com.bluemobile.hbc.models

import tw.com.bluemobile.hbc.utilities.BASE_URL

class MemberPetModel: BaseModel() {
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
    }
}