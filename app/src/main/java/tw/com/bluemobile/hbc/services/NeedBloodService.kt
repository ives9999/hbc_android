package tw.com.bluemobile.hbc.services

import tw.com.bluemobile.hbc.utilities.URL_HOME

object NeedBloodService: BaseService() {

    override fun getDeleteURL(): String {
        return URL_HOME + "needBlood/postDelete"
    }

    override fun getListURL(): String {
        return URL_HOME + "needBlood/getList"
    }

    override fun getOneURL(): String {
        return URL_HOME + "needBlood/getOne"
    }

    override fun getUpdateURL(): String {
        return URL_HOME + "needBlood/postUpdate"
    }
}