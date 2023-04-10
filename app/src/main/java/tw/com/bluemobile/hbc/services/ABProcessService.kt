package tw.com.bluemobile.hbc.services

import tw.com.bluemobile.hbc.utilities.URL_HOME

object ABProcessService: BaseService() {

    override fun getListURL(): String {
        return URL_HOME + "ABProcess/getList"
    }
}