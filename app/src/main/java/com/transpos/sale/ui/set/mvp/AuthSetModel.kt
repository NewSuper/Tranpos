package com.transpos.sale.ui.set.mvp

import android.util.Log
import com.trans.network.callback.StringCallback
import com.transpos.sale.base.BaseApp
import com.transpos.sale.db.manger.WorkerDbManger
import com.transpos.sale.utils.*
import com.transpos.tools.tputils.TPUtils

class AuthSetModel {

    companion object{
        fun logout(user : String,pwd : String) : Boolean{
            var isExits = false
            if(TPUtils.get(BaseApp.getApplication(),KeyConstrant.KEY_USER,"").equals(user)
                && TPUtils.get(BaseApp.getApplication(),KeyConstrant.KEY_PWD,"").equals(pwd)){
                isExits = true
            }
            return isExits
        }
    }

}