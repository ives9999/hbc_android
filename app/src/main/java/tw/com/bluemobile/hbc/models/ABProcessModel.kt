package tw.com.bluemobile.hbc.models

class ABProcessModel: BaseModel() {

    var order_id: Int = 0
    var need_blood_id: Int = 0
    var donate_blood_id: Int = 0
    var memberA_id: Int = 0
    var memberB_id: Int = 0
    var process: String = ""
    var send_information_at: String = ""
    var arrive_hospitalA_at: String = ""
    var arrive_hospitalB_at: String = ""
    var meet_at: String = ""
    var trafficA_fee_at: String = ""
    var trafficP_fee_at: String = ""
    var trafficB_fee_at: String = ""
    var pair_at: String = ""
    var nutrient_feeA_at: String = ""
    var nutrient_feeP_at: String = ""
    var nutrient_feeB_at: String = ""
    var complete_at: String = ""

    override fun filterRow() {
        super.filterRow()
    }
}




















