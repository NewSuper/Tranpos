package com.newsuper.t.sale.ui.navi.mvp

import com.newsuper.t.sale.ui.navi.mvp.ScanCodeContract
import com.trans.network.HttpManger
import com.trans.network.callback.JsonObjectCallback
import com.trans.network.utils.GsonHelper
import com.newsuper.t.sale.base.BaseApp
import com.newsuper.t.sale.entity.RegistrationCode
import com.newsuper.t.sale.entity.Worker
import com.newsuper.t.sale.http.path.HttpUrl
import com.newsuper.t.sale.utils.Global
import com.newsuper.t.sale.utils.KeyConstrant
import com.newsuper.t.sale.utils.OpenApiUtils
import com.transpos.tools.tputils.TPUtils

class ScanCodeModel : ScanCodeContract.Model {
    override fun queryMemberByCode(code: String, callback: JsonObjectCallback<*>) {
        val parameters = OpenApiUtils.getInstance().newApiParameters()
        parameters["name"] = "member.query"
        val api = TPUtils.getObject(BaseApp.getApplication(),KeyConstrant.KEY_AUTH_REGISTER,
            RegistrationCode::class.java)
        val worker = TPUtils.getObject(
            BaseApp.getApplication(), KeyConstrant.KEY_WORKER,
            Worker::class.java
        )
        val reqData : MutableMap<String,Any> = HashMap()
        reqData["property"] = "scanCode"
        reqData["keyword"] = code
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