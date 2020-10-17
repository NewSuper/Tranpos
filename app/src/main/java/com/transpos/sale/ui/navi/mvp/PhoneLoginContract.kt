package com.transpos.sale.ui.navi.mvp

import com.trans.network.callback.JsonObjectCallback
import com.transpos.sale.base.mvp.IBaseModel
import com.transpos.sale.base.mvp.IBaseView

interface PhoneLoginContract {

    interface Model : IBaseModel{
        fun queryMemberByPhone(phoneNum : String,callback : JsonObjectCallback<*>)
    }

    interface View : IBaseView{
        fun queryMemberSuccess()
    }


    interface Presenter{
        fun queryMemberByPhone(phoneNum : String)
    }
}