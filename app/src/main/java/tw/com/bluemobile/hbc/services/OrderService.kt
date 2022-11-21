package tw.com.bluemobile.hbc.services

import tw.com.bluemobile.hbc.utilities.URL_HOME

object OrderService: BaseService() {

    override fun getOneURL(): String {
        return URL_HOME + "order/getOne"
    }

    override fun getUpdateURL(): String {
        return URL_HOME + "order/postUpdate"
    }
}