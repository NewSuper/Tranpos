package com.newsuper.t.sale.ui.navi.mvp

import android.content.Intent
import com.trans.network.callback.JsonObjectCallback
import com.trans.network.model.Response
import com.trans.network.utils.GsonHelper
import com.newsuper.t.sale.base.mvp.BasePresenter
import com.newsuper.t.sale.entity.BaseResponse
import com.newsuper.t.sale.entity.EntityResponse
import com.newsuper.t.sale.entity.Member
import com.newsuper.t.sale.ui.scan.PromptScanCodeActivity
import com.newsuper.t.sale.utils.KeyConstrant
import com.newsuper.t.sale.utils.UiUtils

class PhoneLoginPresenter : BasePresenter<PhoneLoginContract.Model, PhoneLoginContract.View>(),
    PhoneLoginContract.Presenter {


    override fun createModule(): PhoneLoginContract.Model {
        return PhoneLoginModel()
    }

    override fun queryMemberByPhone(phoneNum: String) {
        module.queryMemberByPhone(phoneNum,object : JsonObjectCallback<EntityResponse<Member>>() {

            override fun onSuccess(response: Response<EntityResponse<Member>>?) {
                super.onSuccess(response)
                response?.let {
                   if(it.body().code == BaseResponse.SUCCESS){
                       var member = it.body().data
                       if(isViewAttached){
                           val intent : Intent = Intent(context,PromptScanCodeActivity::class.java)
                           intent.putExtra(KeyConstrant.KEY_MEMBER,GsonHelper.toJson(member))
                           context.startActivity(intent)
                           view.queryMemberSuccess()
                       }

                   } else{
                       UiUtils.showToastLong(it.body().msg)
                   }
                }

            }

            override fun onError(response: Response<EntityResponse<Member>>?) {
                super.onError(response)
            }
        })
    }
}