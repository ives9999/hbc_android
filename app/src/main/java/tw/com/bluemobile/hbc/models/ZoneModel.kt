package tw.com.bluemobile.hbc.models

import tw.com.bluemobile.hbc.utilities.Zones

class ZoneModel: BaseModel() {

    var city_id: Int = 0
    var area_id: Int = 0
    var city_name: String = ""
    var area_name: String = ""
    var road: String = ""
    var zip: Int = 0

    var address: String = ""

    override fun filterRow() {
        super.filterRow()

        if (city_id > 0) {
            city_name = Zones.zoneIDToName(city_id)
        }

        if (area_id > 0) {
            area_name = Zones.zoneIDToName(area_id)
        }

        if (city_id > 0 && area_id > 0) {
            address = city_name + area_name + zip + road
        }
    }
}