package com.transpos.sale.ui.scan.mvp

import com.trans.network.callback.JsonObjectCallback
import com.trans.network.callback.StringCallback
import com.transpos.sale.base.mvp.IBaseModel
import com.transpos.sale.base.mvp.IBaseView
import com.transpos.sale.entity.Member

interface PayContract {

    interface Model : IBaseModel{
        fun scanPay(totalMoney: Float,code : String,callback: StringCallback)
        fun queryOrder(leshua_order_id:String,callback: StringCallback)
        fun queryMember(code : String,callback: JsonObjectCallback<*>)
        fun memberPay(totalMoney: Float,member: Member ,callback: StringCallback)
        fun queryMemberOrder(totalMoney: Float,traceNo : String,member: Member,callback: StringCallback)
    }


    interface View : IBaseView{
        fun onPaySuccess()
    }


    interface Presenter {
        fun scanCode(money: Float,code : String)
        fun continueMemberPay(password : String)
    }
}