package tw.com.bluemobile.hbc.utilities

typealias CompletionHandler = (Success: Boolean) -> Unit

var isEmulator: Boolean = false
val SESSION_FILENAME: String = "tw.com.bluemobile.hbc.session"

val ADDRESS_KEY: String = "address"
val AREA_ID_KEY: String = "area_id"
val BANK_ACCOUNT_KEY: String = "bank_account"
val BANK_KEY: String = "bank"
val BANK_CODE_KEY: String = "bank_code"
val BRANCH_KEY: String = "branch"
val CITY_ID_KEY: String = "city_id"
val DOB_KEY: String = "dob"
val EMAIL_KEY: String = "email"
val FEATURED_KEY: String = "featured"
val ID_KEY: String = "id"
val ISLOGGEDIN_KEY: String = "isLoggedIn"
val LINE_KEY: String = "line"
val LOCALE_KEY: String = "locale"
val MOBILE_KEY: String = "mobile"
val NAME_KEY: String = "name"
val NICKNAME_KEY: String = "nickname"
val PRIVACY_KEY: String = "privacy"
val PASSWORD_KEY: String = "password"
val PET_AGE_KEY: String = "pet_age"
val PET_NAME_KEY: String = "pet_name"
val PET_TYPE_KEY: String = "pet_type"
val PID_KEY: String = "pid"
val PLAYER_ID_KEY: String = "player_id"
val REPASSWORD_KEY: String = "repassword"
val ROAD_KEY: String = "road"
val ROLE_KEY: String = "role"
val SEX_KEY: String = "sex"
val TOKEN_KEY: String = "token"
val TYPE_KEY: String = "type"
val VALIDATE_KEY: String = "validate"
val ZIP_KEY: String = "zip"


val PARAMS: HashMap<String, String> = hashMapOf("device" to "app")

//const val REMOTE_BASE_URL = "https://hbc.bluemobile.com.tw"
const val REMOTE_BASE_URL = "http://192.168.100.120"
//const val LOCALHOST_BASE_URL = "https://hbc.bluemobile.com.tw"
const val LOCALHOST_BASE_URL = "http://192.168.100.120"

const val PERPAGE = 20

var BASE_URL = REMOTE_BASE_URL
var URL_HOME: String = "$BASE_URL/"

// Header
var HEADER = "application/json; charset=utf-8"
var MULTIPART = "multipart/form-data"






















