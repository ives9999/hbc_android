package tw.com.bluemobile.hbc.models

class OrderItemModel: BaseModel() {

    var order_id: Int = 0
    var product_id: Int = 0
    var attribute: String = ""
    var amount: Int = 0
    var discount: Int = 0
    var quantity: Int = 0
    var shipping_at: String = ""
    var shipping_id: Int = 0
}