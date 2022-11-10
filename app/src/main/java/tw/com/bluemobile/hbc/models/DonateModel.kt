package tw.com.bluemobile.hbc.models

class DonateModel: BaseModel() {

    var amount: Int = 0
    var city_id: Int = 0
    var area_id: Int = 0
    var road: String = ""
    var member_id: Int = 0
    var receipt: Boolean = false

    override fun filterRow() {
        super.filterRow()
    }
}