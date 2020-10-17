package com.transpos.sale.ui.navi.mvp

import com.trans.network.HttpManger
import com.trans.network.callback.JsonObjectCallback
import com.trans.network.utils.GsonHelper
import com.transpos.sale.base.BaseApp
import com.transpos.sale.entity.RegistrationCode
import com.transpos.sale.entity.Worker
import com.transpos.sale.http.path.HttpUrl
import com.transpos.sale.utils.Global
import com.transpos.sale.utils.KeyConstrant
import com.transpos.sale.utils.OpenApiUtils
import com.transpos.tools.tputils.TPUtils
import java.util.*

class PhoneLoginModel : PhoneLoginContract.Model {

    override fun queryMemberByPhone(phoneNum: String, callback: JsonObjectCallback<*>) {
        val parameters =
            OpenApiUtils.getInstance().newApiParameters()
        parameters["name"] = "member.query"
        val api = TPUtils.getObject(
            BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER,
            RegistrationCode::class.java
        )
        val worker = TPUtils.getObject(
            BaseApp.getApplication(), KeyConstrant.KEY_WORKER,
            Worker::class.java
        )
        val reqData: MutableMap<String, Any> =
            HashMap()
        reqData["property"] = "mobile"
        reqData["keyword"] = phoneNum
        reqData["storeNo"] = api.storeNo
        reqData["posNo"] = api.posNo
        reqData["workerNo"] = worker.no
        reqData["sourceSign"] = Global.TERMINAL_TYPE
        parameters["data"] = GsonHelper.toJson(reqData)
        parameters["sign"] = OpenApiUtils.getInstance().sign(api, parameters)
        HttpManger.getSingleton().postJsonObject(
            HttpUrl.BASE_API_URL,
            parameters,
            null,
            callback
        )
    }
}