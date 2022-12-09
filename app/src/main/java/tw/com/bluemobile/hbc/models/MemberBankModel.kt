package tw.com.bluemobile.hbc.models

class MemberBankModel: BaseModel() {

    var bank: String = ""
    var branch: String = ""
    var bank_code: String = ""
    var bank_account: String = ""

    override fun filterRow() {
        super.filterRow()
    }

}