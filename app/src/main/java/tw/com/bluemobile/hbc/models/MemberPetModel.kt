package tw.com.bluemobile.hbc.models

class MemberPetModel: BaseModel() {
    var member_id: Int = 0
    var type: String = "cat"
    var age: Int = 0
    var weight: Int = 0
    var blood_type: String = ""
    var IDo: Boolean = false
    var traffic_fee: Int = 0
    var nutrient_fee: Int = 0
    var blood_image: String = ""
    var body_image: String = ""

    override fun filterRow() {
        super.filterRow()
    }
}