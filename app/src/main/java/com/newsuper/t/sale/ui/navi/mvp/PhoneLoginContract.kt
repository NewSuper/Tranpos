package com.newsuper.t.sale.ui.navi.mvp

import com.trans.network.callback.JsonObjectCallback
import com.newsuper.t.sale.base.mvp.IBaseModel
import com.newsuper.t.sale.base.mvp.IBaseView

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