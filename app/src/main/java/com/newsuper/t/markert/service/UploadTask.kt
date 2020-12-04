package com.newsuper.t.markert.service

import android.text.TextUtils
import android.util.Log
import com.google.gson.reflect.TypeToken
import com.trans.network.HttpManger
import com.trans.network.utils.GsonHelper
import com.newsuper.t.markert.base.BaseApp
import com.newsuper.t.markert.base.HttpUrl
import com.newsuper.t.markert.db.manger.OrderItemDbManger
import com.newsuper.t.markert.db.manger.OrderPayDbManger
import com.newsuper.t.markert.db.manger.PromotionOrderDbManger
import com.newsuper.t.markert.entity.*
import com.newsuper.t.markert.entity.state.OrderPaymentStatusFlag
import com.newsuper.t.markert.entity.state.OrderStatusFlag
import com.newsuper.t.markert.entity.state.UploadStatusFlag

import com.newsuper.t.markert.utils.DateUtil
import com.newsuper.t.markert.utils.KeyConstrant
import com.newsuper.t.markert.utils.OpenApiUtils
import com.newsuper.t.markert.db.manger.OrderObjectDbManger
import com.transpos.tools.tputils.TPUtils


class UploadTask : Runnable {

//    private val mQueue: LinkedBlockingQueue<Int> = LinkedBlockingQueue()

    init {

    }

    override fun run() {
        Log.e("debug","${DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT)} test thread start loop ")
        val orderList = OrderObjectDbManger.loadAll()
        val datas = mutableListOf<OrderObject>()
        orderList.forEach {
            if(it.uploadStatus == UploadStatusFlag.NOUPLOAD
                && it.orderStatus == OrderStatusFlag.PAY_STATE
                && it.paymentStatus == OrderPaymentStatusFlag.PAY_COMPLETE_STATE
                && it.uploadStatus == UploadStatusFlag.NOUPLOAD){
                datas.add(it)
            }
        }
        datas.forEach {
            val parameters = OpenApiUtils.getInstance().newApiParameters()
            parameters["name"] = "upload.business.order"
            val auth = TPUtils.getObject(
                BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER,
                RegistrationCode::class.java
            )
            val reqData: MutableMap<String,Any> = mutableMapOf()
            reqData["clientId"] = it.id
            reqData["storeId"] = it.storeId
            reqData["storeNo"] = it.storeNo
            reqData["storeName"] = it.storeName
            reqData["objectId"] = it.objectId
            reqData["tradeNo"] = it.tradeNo
            reqData["orderNo"] = it.orderNo
            reqData["workerNo"] = it.workerNo
            reqData["workerName"] = it.workerName
            reqData["posNo"] = it.posNo
            reqData["deviceName"] = it.deviceName
            reqData["macAddress"] = it.macAddress
            reqData["ipAddress"] = it.ipAddress
            reqData["salesCode"] = it.salesCode
            reqData["salesName"] = it.salesName
            reqData["tableNo"] = it.tableNo
            reqData["tableName"] = it.tableName
            reqData["people"] = it.people
            reqData["shiftId"] = it.shiftId
            reqData["shiftNo"] = it.shiftNo
            reqData["shiftName"] = it.shiftName
            reqData["saleDate"] = it.saleDate
            reqData["finishDate"] = it.finishDate
            reqData["weeker"] = it.weeker
            reqData["weather"] = it.weather
            reqData["totalQuantity"] = it.totalQuantity
            reqData["itemCount"] = it.itemCount
            reqData["payCount"] = it.payCount
            reqData["amount"] = it.amount
            reqData["discountAmount"] = it.discountAmount
            reqData["receivableAmount"] = it.receivableAmount
            reqData["paidAmount"] = it.paidAmount
            reqData["receivedAmount"] = it.receivedAmount
            reqData["malingAmount"] = it.malingAmount
            reqData["changeAmount"] = it.changeAmount
            reqData["invoicedAmount"] = it.invoicedAmount
            reqData["overAmount"] = it.overAmount
            reqData["isMember"] = it.isMember
            reqData["isPlus"] = it.isPlus
            reqData["memberName"] = it.memberName
            reqData["memberMobileNo"] = it.memberMobileNo
            reqData["memberNo"] = it.memberNo
            reqData["cardFaceNo"] = it.cardFaceNo
            reqData["prePoint"] = it.prePoint
            reqData["addPoint"] = it.addPoint
            reqData["aftPoint"] = it.aftPoint
            reqData["remark"] = it.remark
            reqData["discountRate"] = it.discountRate
            reqData["postWay"] = it.postWay
            reqData["orderSource"] = it.orderSource
            reqData["orderStatus"] = it.orderStatus
            reqData["paymentStatus"] = it.paymentStatus
            reqData["orgTradeNo"] = it.orgTradeNo
            reqData["refundCause"] = it.refundCause
            reqData["receivableRemoveCouponAmount"] = it.receivableRemoveCouponAmount

            reqData["orderItems"] = findOrderItemsByTradeno(it.tradeNo)
            reqData["pays"] = getOrderPayInfos(it.tradeNo)
            reqData["orderPromotions"] = getPromotionOrder(it.tradeNo)


            parameters["data"] = GsonHelper.toJson(reqData)
            parameters["sign"] = OpenApiUtils.getInstance().sign(auth, parameters)
//            HttpManger.getSingleton()
//                .postString(HttpUrl.BASE_API_URL, parameters, object : StringCallback(){
//                    override fun onSuccess(response: Response<String>?) {
//                        super.onSuccess(response)
//                    }
//                })
            //已经在异步线程了，这里同步请求数据更好处理
            val result = HttpManger.getSingleton()
                .postString(HttpUrl.BASE_API_URL, parameters)
            if(TextUtils.isEmpty(result)){
                //上传失败
                it.uploadStatus = UploadStatusFlag.FAILED
                it.uploadErrors++

            } else{
                val resp = GsonHelper.fromJson(result,object  : TypeToken<EntityResponse<BusinessUploadBean>>(){}.type) as EntityResponse<BusinessUploadBean>
                if(resp.code == BaseResponse.SUCCESS && resp.data.status == 0){
                    it.uploadStatus = UploadStatusFlag.SUCCESS
                    it.uploadMessage = resp.data.msg
                    it.uploadCode = resp.code.toString()
                } else{
                    it.uploadStatus = UploadStatusFlag.FAILED
                    it.uploadMessage = resp.data.msg
                    it.uploadCode = resp.code.toString()
                }
            }
            OrderObjectDbManger.update(it)

        }
    }

    private  fun getPromotionOrder(tradeNo: String): MutableList<PromotionOrder> {
        return PromotionOrderDbManger.findPromotionOrders(tradeNo)
    }

    private  fun findOrderItemsByTradeno(tradeNo : String) : MutableList<OrderItem> {
        val list = OrderItemDbManger.findOrderItemsByTradeno(tradeNo)
        list.forEach {
            it.promotions = mutableListOf()
            it.itemPayList = mutableListOf()
        }
        return list
    }

    private  fun getOrderPayInfos(tradeNo : String) : MutableList<OrderPay>{
        return OrderPayDbManger.findOderPayInfos(tradeNo)
    }
}