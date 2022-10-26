package tw.com.bluemobile.hbc.services

import tw.com.bluemobile.hbc.utilities.URL_HOME

object NeedBloodService: BaseService() {

    override fun getOneURL(): String {
        return URL_HOME + "need_blood/postOne"
    }

    override fun getUpdateURL(): String {

        return URL_HOME + "needBlood/postUpdate"
    }

}