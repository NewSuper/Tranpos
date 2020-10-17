package com.transpos.sale.ui.navi.mvp

import com.trans.network.callback.JsonObjectCallback
import com.transpos.sale.base.mvp.IBaseModel
import com.transpos.sale.base.mvp.IBaseView

interface ScanCodeContract {

    interface Model : IBaseModel{
        fun queryMemberByCode(code : String,callback: JsonObjectCallback<*>)
    }

    interface View : IBaseView{
        fun queryMemberSuccess();
    }

    interface Presenter{
        fun queryMemberByCode(code : String)
    }
}