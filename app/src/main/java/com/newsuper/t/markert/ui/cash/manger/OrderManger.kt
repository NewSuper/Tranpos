package com.newsuper.t.markert.ui.cash.manger

import com.newsuper.t.markert.utils.StringKotlin
import com.newsuper.t.markert.base.BaseApp
import com.newsuper.t.markert.entity.*
import com.newsuper.t.markert.entity.state.OrderPaymentStatusFlag
import com.newsuper.t.markert.entity.state.OrderSourceFlag
import com.newsuper.t.markert.entity.state.OrderStatusFlag
import com.newsuper.t.markert.entity.state.PostWayFlag
import com.newsuper.t.markert.utils.*
import com.newsuper.t.markert.utils.DateUtil.SIMPLE_FORMAT
import com.newsuper.t.markert.db.manger.OrderObjectDbManger

import com.transpos.tools.tputils.TPUtils
import java.util.*

object OrderManger {
    private var orderBean : OrderObject? = null

    fun initalize(){
        val serialNumber = Tools.generateSerialNumber(4,KeyConstrant.KEY_SERIAL_ORDER)
        val api = TPUtils.getObject(
            BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER,
            RegistrationCode::class.java
        )
        val payNo = "${api.storeNo}1${DateUtil.getNowDateStr("yyMMddHHmm")}${api.posNo}$serialNumber${StringKotlin.generateString(2)}"
        orderBean = OrderObject()

        val worker = TPUtils.getObject(
            BaseApp.getApplication(), KeyConstrant.KEY_WORKER,
            Worker::class.java
        )
        orderBean!!.let{
            it.id = IdWorkerUtils.nextId()
            it.tenantId = api.tenantId
            it.objectId = IdWorkerUtils.nextId()
            it.tradeNo = payNo
            //it.orderNo
            it.storeId = api.storeId
            it.storeNo = api.storeNo
            it.storeName = api.storeName
            it.workerNo = worker.no
            it.workerName = worker.name
            it.saleDate = DateUtil.getNowDateStr(SIMPLE_FORMAT)
            //it.finishDate
            it.posNo = api.posNo
            it.deviceName = DeviceUtils.getInstance().computerName
            it.macAddress = DeviceUtils.getInstance().macAddress
            it.ipAddress = DeviceUtils.getInstance().ipAddress
            //it.itemCount
            //it.payCount
            //it.totalQuantity
            //it.amount
            //it.discountAmount
            //it.receivableAmount
            //it.paidAmount
            //it.receivedAmount
            //it.malingAmount
            //it.changeAmount
            //it.invoicedAmount
            //it.overAmount
            it.orderStatus = OrderStatusFlag.WAIT_STATE
            it.paymentStatus = OrderPaymentStatusFlag.NO_PAY_STATE
            //it.printStatus
            //it.printTimes
            it.postWay = PostWayFlag.ZITI_STATE
            it.orderSource = OrderSourceFlag.ASSISTANT_CASH
            it.people = 1
            it.shiftId = IdWorkerUtils.nextId()
            it.shiftName = "默认班次"
            it.shiftNo = Tools.generateSerialNumber(4,KeyConstrant.KEY_SERIAL)
            //it.discountRate
            //it.isMember
            //it.memberNo
            //it.memberName
            //it.memberMobileNo
            //it.cardFaceNo
            //it.prePoint
            //it.addPoint
            //it.refundPoint
            //it.aftPoint
            //it.aftAmount
            it.uploadStatus = 0
            //it.uploadErrors
            //it.uploadCode
            //it.uploadMessage
            //it.serverId
            //it.uploadTime
            //it.weather
            it.weeker = DateUtil.getWeekOfDate(Date())
            //it.remark
            it.createUser = worker.createUser
            it.createDate = DateUtil.getNowDateStr(SIMPLE_FORMAT)
            it.modifyUser = worker.modifyUser
            it.modifyDate = DateUtil.getNowDateStr(SIMPLE_FORMAT)
            //it.memberId
            //it.receivableRemoveCouponAmount
            //it.isPlus
            //it.plusDiscountAmount
            //it.salesCode
            //it.salesName
            //it.freightAmount
            //it.orgTradeNo
            //it.refundCause


            OrderItemManger.clear() //创建订单的时候必须把内存中订单项list清空
            OrderObjectDbManger.insert(it)
        }
    }

    fun getOrderBean() : OrderObject?{
        return orderBean
    }

    fun clear(){
        orderBean = null
        OrderItemManger.clear()
    }
}