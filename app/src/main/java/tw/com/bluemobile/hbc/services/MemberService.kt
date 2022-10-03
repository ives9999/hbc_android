package tw.com.bluemobile.hbc.services

import tw.com.bluemobile.hbc.utilities.URL_HOME

object MemberService: BaseService() {

    override fun getUpdateURL(): String {
        return URL_HOME + "member/register"
    }
}