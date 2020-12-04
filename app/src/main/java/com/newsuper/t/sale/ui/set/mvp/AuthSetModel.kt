package com.newsuper.t.sale.ui.set.mvp

import com.newsuper.t.sale.base.BaseApp
import com.newsuper.t.sale.utils.*
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