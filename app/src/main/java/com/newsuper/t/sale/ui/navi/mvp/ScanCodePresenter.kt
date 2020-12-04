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

class ScanCodePresenter : BasePresenter<ScanCodeContract.Model, ScanCodeContract.View>(),
    ScanCodeContract.Presenter {
    override fun createModule(): ScanCodeContract.Model {
        return ScanCodeModel()
    }

    override fun queryMemberByCode(code: String) {
        module.queryMemberByCode(code,object :JsonObjectCallback<EntityResponse<Member>>(){
            override fun onSuccess(response: Response<EntityResponse<Member>>?) {
                super.onSuccess(response)
                response?.let {
                    if(it.body().code == BaseResponse.SUCCESS){
                        var member = it.body().data
                        val intent : Intent = Intent(context,PromptScanCodeActivity::class.java)
                        intent.putExtra(KeyConstrant.KEY_MEMBER,GsonHelper.toJson(member))
                        context.startActivity(intent)
                    } else{
                        UiUtils.showToastLong(it.body().msg)
                    }
                }


            }

            override fun onError(response: Response<EntityResponse<Member>>?) {
                super.onError(response)
                UiUtils.showToastLong(response?.message())
            }
        })
    }
}