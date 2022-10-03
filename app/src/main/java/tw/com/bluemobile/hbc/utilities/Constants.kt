package tw.com.bluemobile.hbc.utilities

typealias CompletionHandler = (Success: Boolean) -> Unit

var isEmulator: Boolean = false

val AREA_KEY: String = "area_id"
val CITY_KEY: String = "city_id"
val EMAIL_KEY: String = "email"
val LINE_KEY: String = "line"
val MOBILE_KEY: String = "mobile"
val NAME_KEY: String = "name"
val NICKNAME_KEY: String = "nickname"
val PRIVACY_KEY: String = "privacy"
val PASSWORD_KEY: String = "password"
val REPASSWORD_KEY: String = "repassword"
val ROAD_KEY: String = "road"


val PARAMS: HashMap<String, String> = hashMapOf()

const val REMOTE_BASE_URL = "https://sandbox.hbc.com"
const val LOCALHOST_BASE_URL = "http://192.168.100.120"

var BASE_URL = REMOTE_BASE_URL
var URL_HOME: String = "$BASE_URL/"





















