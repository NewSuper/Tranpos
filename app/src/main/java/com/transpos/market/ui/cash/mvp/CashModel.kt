package com.transpos.market.ui.cash.mvp

import com.trans.network.HttpManger
import com.trans.network.callback.JsonObjectCallback
import com.trans.network.callback.StringCallback
import com.trans.network.utils.GsonHelper
import com.transpos.market.base.BaseApp
import com.transpos.market.base.HttpUrl
import com.transpos.market.db.manger.PaymentParameterDbManger
import com.transpos.market.entity.Member
import com.transpos.market.entity.PaymentParameter
import com.transpos.market.entity.RegistrationCode
import com.transpos.market.entity.Worker
import com.transpos.market.utils.*
import com.transpos.market.utils.base64.Base64Utils
import com.transpos.market.utils.base64.DesUtils
import com.transpos.market.utils.base64.RSAAndroidUtil
import com.transpos.sale.utils.isListEmpty
import com.transpos.tools.tputils.TPUtils
import org.json.JSONObject
import java.util.HashMap
import java.util.LinkedHashMap

class CashModel : CashContract.Model {
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
    override fun queryMemberByBarcode(phoneNum: String, callback: JsonObjectCallback<*>) {
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
        reqData["property"] = "scanCode"
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

    private  var paymentParameter : PaymentParameter? = null

    override fun scanPay(totalMoney: Float,code: String, callback: StringCallback) {
        val list = PaymentParameterDbManger.getInstance().loadAll()
        if(!list.isListEmpty()){
            val serialNumber = String.format("%0" + 4 + "d", 0)
            val api = TPUtils.getObject(
                BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER,
                RegistrationCode::class.java
            )
            paymentParameter = list[0]
            var jsonObject = JSONObject(paymentParameter?.pbody)
            val payKey = jsonObject.optString("payKey").trim()
            val gatewayUrl = jsonObject.optString("gatewayUrl")
            val merchant_id = jsonObject.optString("merchant_id")
            val payNo = "${api.storeNo}1${DateUtil.getNowDateStr("yyMMddHHmm")}${api.posNo}$serialNumber${StringKotlin.generateString(2)}"

            val map: MutableMap<String, String> = LinkedHashMap()
            map["service"] = "upload_authcode"
            map["auth_code"] = code
            map["merchant_id"] = merchant_id
            map["third_order_id"] = payNo
            map["amount"] = "${StringKotlin.getFenValue(totalMoney)}"
            map["nonce_str"] = StringKotlin.generateString(32)

            var entries = map.entries
            val list : MutableList<MutableMap.MutableEntry<String,String>> = mutableListOf()
            entries.forEach {
                list.add(it)
            }
            list.sortWith(Comparator<MutableMap.MutableEntry<String, String>> { o1, o2 -> o1!!.key.compareTo( o2!!.key) })
            val sbR = Tools.getStringBuffer(list) + "&key="+payKey
            val sign = Tools.hex_md5(sbR).toUpperCase()
            map["sign"] = sign

            HttpManger.getSingleton().postFromString(gatewayUrl,map,callback)

        } else{
            UiUtils.showToastLong("支付参数未配置")
        }
    }

    override fun queryOrder(leshua_order_id: String, callback: StringCallback) {
        val map : MutableMap<String,String> = mutableMapOf()
        var jsonObject = JSONObject(paymentParameter?.pbody)
        val merchant_id = jsonObject.optString("merchant_id")
        val payKey = jsonObject.optString("payKey").trim()
        val gatewayUrl = jsonObject.optString("gatewayUrl")
        map["service"] = "query_status"
        map["merchant_id"] = merchant_id
        map["leshua_order_id"] = leshua_order_id
        map["nonce_str"] = StringKotlin.generateString(32)

        var entries = map.entries
        val list : MutableList<MutableMap.MutableEntry<String,String>> = mutableListOf()
        entries.forEach {
            list.add(it)
        }
        list.sortWith(Comparator<MutableMap.MutableEntry<String, String>> { o1, o2 -> o1!!.key.compareTo( o2!!.key) })
        val sbR = Tools.getStringBuffer(list) + "&key="+payKey
        val sign = Tools.hex_md5(sbR).toUpperCase()
        map["sign"] = sign

        HttpManger.getSingleton().postFromString(gatewayUrl,map,callback)
    }

    override fun queryMember(code: String, callback: JsonObjectCallback<*>) {
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

    override fun memberPay(totalMoney: Float, member: Member, callback: StringCallback) {
        val parameters = OpenApiUtils.getInstance().newApiParameters()
        parameters["name"] = "member.trade.pay"
        val api = TPUtils.getObject(
            BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER,
            RegistrationCode::class.java
        )
        val worker = TPUtils.getObject(
            BaseApp.getApplication(), KeyConstrant.KEY_WORKER,
            Worker::class.java
        )
        val serialNumber = String.format("%0" + 4 + "d", 0)
        val payNo = "${api.storeNo}1${DateUtil.getNowDateStr("yyMMddHHmm")}${api.posNo}$serialNumber${StringKotlin.generateString(2)}"
        val reqData: MutableMap<String, Any> = mutableMapOf()
        val public_key = RSAAndroidUtil.string2PublicKey(Global.PUBLIC_KEY)
        var encode_psw : String = ""
        var isNoPwd = 1
        if(member.isNoPwd == 1){
            isNoPwd = 1
            encode_psw = if(member.needShowPwdDialog){
                isNoPwd = 0
                DesUtils.getInstance().EncryptString(member.password)
            } else{
                Base64Utils.encode(RSAAndroidUtil.publicEncrypt(StringKotlin.getFenValue(totalMoney).toString().toByteArray(Charsets.UTF_8),public_key))
            }
        } else{
            isNoPwd = 0
            encode_psw = DesUtils.getInstance().EncryptString(member.password)
        }

        reqData["tradeNo"] = payNo
        reqData["memberId"] = member.id
        reqData["mobile"] = member.mobile
        reqData["cardNo"] = member.cardList.get(0).cardNo
        reqData["passwd"] = encode_psw
        reqData["isNoPwd"] = isNoPwd
        reqData["totalAmount"] = StringKotlin.getFenValue(totalMoney)
        reqData["pointValue"] = StringKotlin.getFenValue(totalMoney)
        reqData["shopNo"] = member.shopNo
        reqData["posNo"] = api.posNo
        reqData["workerNo"] = worker.no
        reqData["sourceSign"] = Global.TERMINAL_TYPE
        parameters["data"] = GsonHelper.toJsonPlus(reqData)
        parameters["sign"] = OpenApiUtils.getInstance().sign(api, parameters)
        HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL,parameters,callback)
    }

    override fun queryMemberOrder(totalMoney: Float, traceNo: String, member: Member, callback: StringCallback) {
        val parameters = OpenApiUtils.getInstance().newApiParameters()
        parameters["name"] = "member.trade.pay.check"
        val api = TPUtils.getObject(
            BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER,
            RegistrationCode::class.java
        )
        val worker = TPUtils.getObject(
            BaseApp.getApplication(), KeyConstrant.KEY_WORKER,
            Worker::class.java
        )
        val public_key = RSAAndroidUtil.string2PublicKey(Global.PUBLIC_KEY)
        var encode_psw : String = ""
        var isNoPwd = 1
        if(member.isNoPwd == 1){
            isNoPwd = 1
            encode_psw = if(member.needShowPwdDialog){
                isNoPwd = 0
                DesUtils.getInstance().EncryptString(member.password)
            } else{
                Base64Utils.encode(RSAAndroidUtil.publicEncrypt(StringKotlin.getFenValue(totalMoney).toString().toByteArray(Charsets.UTF_8),public_key))
            }
        } else{
            isNoPwd = 0
            encode_psw = DesUtils.getInstance().EncryptString(member.password)
        }

        val reqData: MutableMap<String, Any> = mutableMapOf()
        reqData["tradeNo"] = traceNo
        reqData["memberId"] = member.id
        reqData["mobile"] = member.mobile
        reqData["cardNo"] = member.cardList.get(0).cardNo
        reqData["isNoPwd"] = isNoPwd
        reqData["passwd"] = encode_psw
        reqData["totalAmount"] = StringKotlin.getFenValue(totalMoney)
        reqData["pointValue"] = StringKotlin.getFenValue(totalMoney)
        reqData["shopNo"] = member.shopNo
        reqData["posNo"] = api.posNo
        reqData["workerNo"] = worker.no
        reqData["sourceSign"] = Global.TERMINAL_TYPE
        parameters["data"] = GsonHelper.toJsonPlus(reqData)
        parameters["sign"] = OpenApiUtils.getInstance().sign(api, parameters)
        HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL,parameters,callback)
    }

    /**
     * 刷脸业务
     */
//    fun startFace(money: Float,callback: StringCallback){
//        WxPayFace.getInstance().getWxpayfaceRawdata(object : IWxPayfaceCallback() {
//            @Throws(RemoteException::class)
//            override fun response(map: Map<*, *>) {
//                if (Tools.isSuccessInfo(map)) { //获取到RAW_DATA
//                    if (map[WxConstant.RAW_DATA] != null) {
//                        val rawdata = map[WxConstant.RAW_DATA].toString()
//                        getLePosFacePayVoucher(rawdata,money,callback)
//                    }
//                }
//            }
//        })
//    }

//    private fun getLePosFacePayVoucher(rawdata : String,money: Float,callback: StringCallback){
//        val map: MutableMap<String, String> = LinkedHashMap()
//        if(paymentParameter == null){
//            val list = PaymentParameterDbManger.getInstance().loadAll()
//            if(!list.isListEmpty()){
//                paymentParameter = list[0]
//            }
//        }
//        val pbody = paymentParameter?.pbody
//        var sub_mch_id = ""
//        var merchant_id = ""
//        if (!TextUtils.isEmpty(pbody)) {
//            try {
//                val jsonObject = JSONObject(pbody)
//                sub_mch_id = TPUtils.get(BaseApp.getApplication(), KeyConstrant.KEY_WX_MECHID, "")
//                merchant_id = jsonObject.optString("merchant_id")
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//        }
//        map["sub_appid"] = TPUtils.get(BaseApp.getApplication(), KeyConstrant.KEY_WX_APPID, WxConstant.LE_APP_ID)
//        map["sub_mch_id"] = sub_mch_id
//        map["merchant_id"] = merchant_id
//        map["raw_data"] = rawdata
//        map["store_id"] = paymentParameter!!.storeId
//        map["store_name"] = "shop"
//        map["device_id"] = DeviceUtils.getInstance().motherboardNumber
//
//        var entries = map.entries
//
//        val infoIds : MutableList<MutableMap.MutableEntry<String,String>> = mutableListOf()
//        entries.forEach {
//            infoIds.add(it)
//        }
//        infoIds.sortWith(Comparator<MutableMap.MutableEntry<String, String>> { o1, o2 ->
//            o1.key.compareTo(o2.key)
//        })
//
//        //使用&符号进行频率
////        String sbR = Tools.getStringBuffer(infoIds) + "&key=" + WxConstant.MCH_KEY_ID;
//        var payKey: String? = null
//        if (!TextUtils.isEmpty(pbody)) {
//            try {
//                val jsonObject = JSONObject(pbody)
//                payKey = jsonObject.optString("payKey").trim { it <= ' ' }
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//        }
//        if (payKey == null) {
//            payKey = WxConstant.LE_KEY
//        }
//
//        val sbR = Tools.getStringBuffer(infoIds) + "&key=" + payKey
//        Log.e("OkHttp", "签名参数$sbR")
//        //进行MD5加密之后  转大写
//        //进行MD5加密之后  转大写
//        val sign = Tools.hex_md5(sbR).toUpperCase()
//        map["sign"] = sign
//        HttpManger.getSingleton().postFromString(
//            HttpUrl.getLePosAuthInfo,
//            map,
//            object : StringCallback() {
//                override fun onSuccess(response: Response<String>) {
//                    super.onSuccess(response)
//                    val body = response.body()
//                    try {
//                        val resp_msg = Tools.parseGetAuthInfoXML(body, "resp_msg")
//                        val return_code =
//                            Tools.parseGetAuthInfoXML(body, "resp_code")
//                        if (return_code.equals("0")) {
//                            val auth_info = Tools.parseGetAuthInfoXML(body, "auth_info")
//                            getWxpayfaceUserInfo(auth_info,money,callback)
//                        } else {
//                            UiUtils.showToastShort("$resp_msg:$return_code")
//                        }
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                        UiUtils.showToastShort("parseGetAuthInfoXML error")
//                    }
//                }
//
//                override fun onError(response: Response<String>) {
//                    super.onError(response)
//                    Log.e("debug", "onError: " + response.message())
//                }
//            })
//    }

//    private fun getWxpayfaceUserInfo(authInfo: String,money: Float,callback : StringCallback) {
//        val m1: MutableMap<String, Any> =
//            HashMap()
//        m1["appid"] = TPUtils.get(BaseApp.getApplication(), KeyConstrant.KEY_WX_APPID, "") // 公众号，必填
//        m1["mch_id"] = TPUtils.get(BaseApp.getApplication(), KeyConstrant.KEY_WX_MECHID, "") // 商户号，必填
//        m1["store_id"] = paymentParameter!!.storeId // 门店编号，必填
//        m1["face_authtype"] ="FACEPAY" // 人脸识别模式， FACEID-ONCE`: 人脸识别(单次模式) FACEID-LOOP`: 人脸识别(循环模式), 必填
//        m1["authinfo"] = authInfo // 调用凭证，详见上方的接口参数
//        m1["face_code_type"] = "1"
//        m1["total_fee"] = StringUtils.getFenValue(money).toString()
//        m1["ask_face_permit"] = "0"
//
//        WxPayFace.getInstance().getWxpayfaceCode(m1,object : IWxPayfaceCallback(){
//            override fun response(info: MutableMap<Any?, Any?>?) {
//                if (info == null) {
//                    UiUtils.showToastShort("获取信息失败")
//                    return
//                }
//                Log.e("debug", JSON.toJSONString(info))
//                val code = info["return_code"] as String? // 错误码
//                val errcode = info["err_code"] as Int? // 二级错误码
//                val msg = info["return_msg"] as String? // 错误码描述
//                val openid = info["openid"].toString() // openid
//                var sub_openid = ""
//                if (info["sub_openid"] != null) sub_openid =
//                    info["sub_openid"].toString() // 子商户号下的openid(服务商模式)
//                val nickName = info["nickname"].toString() // 微信昵称
//                var token = ""
//                if (info["token"] != null) token =
//                    info["token"].toString() // facesid,用户获取unionid
//                val facecode = info["face_code"].toString()
//                scanPay(money,facecode,callback)
//                if (code == null || openid == null || nickName == null || code != "SUCCESS") {
//                    RuntimeException("调用返回非成功信息,return_msg:$msg   ")
//                        .printStackTrace()
//                    return
//                }
//            }
//        })
//    }

}