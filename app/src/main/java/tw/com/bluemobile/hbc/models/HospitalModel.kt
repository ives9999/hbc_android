package tw.com.bluemobile.hbc.models

import tw.com.bluemobile.hbc.utilities.BASE_URL

class HospitalModel: BaseModel() {

    var license_image: String = ""
    var certificate_image: String = ""
    var member_id: Int = 0

    override fun filterRow() {
        super.filterRow()

        if (license_image != null && license_image.isNotEmpty()) {
            if (!license_image.startsWith("http://") && !license_image.startsWith("https://")) {
                license_image = BASE_URL + license_image
                //print(featured_path)
            }
        } else {
            license_image = "$BASE_URL/images/nophoto.png"
        }

        if (certificate_image != null && certificate_image.isNotEmpty()) {
            if (!certificate_image.startsWith("http://") && !certificate_image.startsWith("https://")) {
                certificate_image = BASE_URL + certificate_image
                //print(featured_path)
            }
        } else {
            certificate_image = "$BASE_URL/images/nophoto.png"
        }
    }
}