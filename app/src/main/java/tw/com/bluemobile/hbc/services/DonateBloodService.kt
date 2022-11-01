package tw.com.bluemobile.hbc.services

import tw.com.bluemobile.hbc.utilities.URL_HOME

object DonateBloodService: BaseService() {

    override fun getDeleteURL(): String {
        return URL_HOME + "donateBlood/getDelete"
    }

    override fun getListURL(): String {
        return URL_HOME + "donateBlood/getList"
    }

    override fun getOneURL(): String {
        return URL_HOME + "donateBlood/getOne"
    }

    override fun getUpdateURL(): String {

        return URL_HOME + "donateBlood/postUpdate"
    }

}