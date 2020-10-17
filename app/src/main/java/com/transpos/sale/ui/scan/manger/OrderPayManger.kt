package com.transpos.sale.ui.scan.manger

import com.transpos.sale.entity.Member
import com.transpos.sale.entity.OrderPay
import com.transpos.sale.entity.state.OrderPaymentStatusFlag
import com.transpos.sale.entity.state.PayChannelFlag
import com.transpos.sale.utils.DateUtil
import com.transpos.sale.utils.IdWorkerUtils

object OrderPayManger {

    lateinit var payInfo : OrderPay
    /**
     * payInfo做了懒加载，在外部判空会报错，所以使用了下面的方法
     *  kotlin有时候还是蛮蛋疼的
     */
    fun isInfoInit()=::payInfo.isInitialized
    private var serialNum = 0

    fun createPayInfo(){
        payInfo = OrderPay()
        var orderBean = OrderManger.getOrderBean()
        orderBean?.let {
            payInfo.id = IdWorkerUtils.nextId()
            payInfo.tenantId = it.tenantId
            payInfo.tradeNo = it.tradeNo
            payInfo.orderId = it.id
            payInfo.orgPayId = ""
            payInfo.orderNo = ++serialNum
            //payInfo.no  lateinit
            //payInfo.name lateinit
            payInfo.amount = it.amount
            payInfo.inputAmount = 0.toDouble()
            payInfo.faceAmount = 0.toDouble()
            payInfo.paidAmount = it.amount
            payInfo.ramount = 0.toDouble()
            payInfo.changeAmount = 0.toDouble()
            payInfo.platformDiscount = 0.toDouble()
            payInfo.platformPaid = 0.toDouble()
            payInfo.payNo = ""
            payInfo.channelNo = ""
            payInfo.voucherNo = ""
            payInfo.status = OrderPaymentStatusFlag.NO_PAY_STATE //lateinit
            payInfo.statusDesc = ""  // lateinit
            payInfo.payTime = ""  // lateinit
            payInfo.finishDate = ""  //lateinit
            payInfo.payChannel = PayChannelFlag.NONE //lateinit
            payInfo.incomeFlag = 1
            payInfo.pointFlag = 1
            payInfo.subscribe = ""
            payInfo.useConfirmed = 0 // lateinit
            payInfo.accountName = ""
            payInfo.bankType = ""
            payInfo.memo = ""
            payInfo.cardNo = ""
            payInfo.cardPreAmount = 0.toDouble()
            payInfo.cardChangeAmount = 0.toDouble()
            payInfo.cardAftAmount = 0.toDouble()
            payInfo.cardPrePoint = 0.toDouble()
            payInfo.cardChangePoint = 0.toDouble()
            payInfo.cardAftPoint = 0.toDouble()

            payInfo.shiftId = orderBean.shiftId
            payInfo.shiftName = orderBean.shiftName
            payInfo.shiftNo = orderBean.shiftNo
            payInfo.createUser = orderBean.createUser
            payInfo.createDate = DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT)
            payInfo.modifyUser = orderBean.modifyUser
            payInfo.modifyDate = DateUtil.getNowDateStr(DateUtil.SIMPLE_FORMAT)
            payInfo.sourceSign = ""

        }

    }

    fun clear(){

    }

    fun setMemberInfo(member : Member?){
        member?.run {
            payInfo.memberMobileNo = this.mobile
            payInfo.cardFaceNo = ""
            payInfo.pointAmountRate = 0.toDouble()
        }
    }


    enum class PayModeEnum(val payNo : String,val payName : String){
        PAYCASH("01","人民币"),
        PAYMEMBER("02","储值卡"),
        PAYWX("05","微信"),
        PAYAIL("04","支付宝")
    }
}