package tw.com.bluemobile.hbc.services

import tw.com.bluemobile.hbc.utilities.URL_HOME

object DonateService: BaseService() {

    override fun getDeleteURL(): String {
        return URL_HOME + "donate/postDelete"
    }

    override fun getListURL(): String {
        return URL_HOME + "donate/getList"
    }

    override fun getOneURL(): String {
        return URL_HOME + "donate/getOne"
    }

    override fun getUpdateURL(): String {

        return URL_HOME + "donate/postUpdate"
    }

}