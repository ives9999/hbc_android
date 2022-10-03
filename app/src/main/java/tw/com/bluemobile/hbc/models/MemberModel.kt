package tw.com.bluemobile.hbc.models

class MemberModel: BaseModel() {

    var email: String = ""
    var nickname: String = ""
    var locale: String = ""
    var dob: String = ""
    var sex: String = ""
    var pid: String = ""
    var line: String = ""
    var area_id: Int = 0
    var zip: Int = 0
    var road: String = ""
    var avatar: String = ""
    var privacy: Int = 0

    var type: Int = 0
    var role: String = ""
    var validate: Int = 0
    var player_id: String = ""

}