package tw.com.bluemobile.hbc.models

import tw.com.bluemobile.hbc.extensions.noSec

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

    var send_information_at_show: String = ""
    var arrive_hospitalA_at_show: String = ""
    var arrive_hospitalB_at_show: String = ""
    var meet_at_show: String = ""
    var trafficA_fee_at_show: String = ""
    var trafficP_fee_at_show: String = ""
    var trafficB_fee_at_show: String = ""
    var pair_at_show: String = ""
    var nutrient_feeA_at_show: String = ""
    var nutrient_feeP_at_show: String = ""
    var nutrient_feeB_at_show: String = ""
    var complete_at_show: String = ""

    override fun filterRow() {
        super.filterRow()

        if (send_information_at != null) {
            send_information_at_show = send_information_at.noSec()
        }
        if (arrive_hospitalA_at != null) {
            arrive_hospitalA_at_show = arrive_hospitalA_at.noSec()
        }
        if (arrive_hospitalB_at != null) {
            arrive_hospitalB_at_show = arrive_hospitalB_at.noSec()
        }
        if (meet_at != null) {
            meet_at_show = meet_at.noSec()
        }
        if (trafficA_fee_at != null) {
            trafficA_fee_at_show = trafficA_fee_at.noSec()
        }
        if (trafficB_fee_at != null) {
            trafficB_fee_at_show = trafficB_fee_at.noSec()
        }
        if (trafficP_fee_at != null) {
            trafficP_fee_at_show = trafficP_fee_at.noSec()
        }
        if (pair_at != null) {
            pair_at_show = pair_at.noSec()
        }
        if (nutrient_feeA_at != null) {
            nutrient_feeA_at_show = nutrient_feeA_at.noSec()
        }
        if (nutrient_feeB_at != null) {
            nutrient_feeB_at_show = nutrient_feeB_at.noSec()
        }
        if (nutrient_feeP_at != null) {
            nutrient_feeP_at_show = nutrient_feeP_at.noSec()
        }
        if (complete_at != null) {
            complete_at_show = complete_at.noSec()
        }
    }
}




















