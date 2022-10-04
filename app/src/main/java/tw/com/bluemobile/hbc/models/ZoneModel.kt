package tw.com.bluemobile.hbc.models

class ZoneModel: BaseModel() {

    var city_name: String = ""
    var area_id: Int = 0
    var area_name: String = ""
    var road: String = ""
    var zip: Int = 0

    override fun filterRow() {
        super.filterRow()

        address = city_name + area_name + zip + road
    }
}