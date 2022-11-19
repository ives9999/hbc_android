package tw.com.bluemobile.hbc.models

class OrderModel: BaseModel() {

    var order_no: String = ""
    var member_id: Int = 0
    var process: String = ""
    var amount: Int = 0
    var handle_fee: Int = 0
}