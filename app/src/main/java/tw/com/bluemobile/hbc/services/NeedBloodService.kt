package tw.com.bluemobile.hbc.services

import tw.com.bluemobile.hbc.utilities.URL_HOME

object NeedBloodService: BaseService() {

    override fun getListURL(): String {
        return URL_HOME + "needBlood/getList"
    }

    override fun getOneURL(): String {
        return URL_HOME + "needBlood/postOne"
    }

    override fun getUpdateURL(): String {

        return URL_HOME + "needBlood/postUpdate"
    }

}