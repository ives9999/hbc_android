package tw.com.bluemobile.hbc.models

import tw.com.bluemobile.hbc.extensions.mobileShow
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
    var traffic_feeA_at: String = ""
    var traffic_feeP_at: String = ""
    var traffic_feeB_at: String = ""
    var pair_at: String = ""
    var nutrient_feeA_at: String = ""
    var nutrient_feeP_at: String = ""
    var nutrient_feeB_at: String = ""
    var complete_at: String = ""

    var memberA_nickname: String = ""
    var memberA_mobile: String = ""
    var memberA_token: String = ""
    var memberB_nickname: String = ""
    var memberB_mobile: String = ""
    var memberB_token: String = ""
    var order_token: String = ""

    var send_information_at_show: String = ""
    var arrive_hospitalA_at_show: String = ""
    var arrive_hospitalB_at_show: String = ""
    var meet_at_show: String = ""
    var traffic_feeA_at_show: String = ""
    var traffic_feeP_at_show: String = ""
    var traffic_feeB_at_show: String = ""
    var pair_at_show: String = ""
    var nutrient_feeA_at_show: String = ""
    var nutrient_feeP_at_show: String = ""
    var nutrient_feeB_at_show: String = ""
    var complete_at_show: String = ""

    var memberA_mobile_show: String = ""
    var memberB_mobile_show: String = ""

    var needBloodModel: NeedBloodModel? = null
    var donateBloodModel: DonateBloodModel? = null

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
        if (traffic_feeA_at != null) {
            traffic_feeA_at_show = traffic_feeA_at.noSec()
        }
        if (traffic_feeB_at != null) {
            traffic_feeB_at_show = traffic_feeB_at.noSec()
        }
        if (traffic_feeP_at != null) {
            traffic_feeP_at_show = traffic_feeP_at.noSec()
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

        if (memberA_mobile != null && memberA_mobile.length > 0) {
            memberA_mobile_show = memberA_mobile.mobileShow()
        }

        if (memberB_mobile != null && memberB_mobile.length > 0) {
            memberB_mobile_show = memberB_mobile.mobileShow()
        }

        if (needBloodModel != null) {
            needBloodModel!!.filterRow()
        }

        if (donateBloodModel != null) {
            donateBloodModel!!.filterRow()
        }
    }
}




















