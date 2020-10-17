package com.transpos.market.ui.cash.mvp

import com.trans.network.callback.JsonObjectCallback
import com.trans.network.callback.StringCallback
import com.transpos.market.base.mvp.IBaseModel
import com.transpos.market.base.mvp.IBaseView
import com.transpos.market.entity.Member
import com.transpos.market.entity.MultipleQueryProduct


interface CashContract {
    interface Model : IBaseModel {
        fun queryMemberByPhone(phoneNum: String, callback: JsonObjectCallback<*>)
        fun queryMemberByBarcode(barCode: String, callback: JsonObjectCallback<*>)
        fun scanPay(totalMoney: Float, code: String, callback: StringCallback)
        fun queryOrder(leshua_order_id: String, callback: StringCallback)
        fun queryMember(code: String, callback: JsonObjectCallback<*>)
        fun memberPay(totalMoney: Float, member: Member, callback: StringCallback)
        fun queryMemberOrder(totalMoney: Float, traceNo: String, member: Member, callback: StringCallback)
    }

    interface View : IBaseView {
        fun queryMemberSuccessByPay(member: Member)
        fun queryMemberSuccess(member: Member)
        fun queryProductSuccess(product: List<MultipleQueryProduct?>?)
        fun onPaySuccess()
    }


    interface Presenter {
        fun queryMemberByPhone(phoneNum: String)
        fun queryMemberByBarcode(barcode: String)
        fun queryProductByBarcode(barCode: String?)
        fun scanCode(money: Float, code: String)
        fun continueMemberPay(password: String)
       fun continueMemberPay2(member: Member,password: String,money: Float)
        fun createOrder()
    }
}