package com.newsuper.t.sale.ui.navi.mvp;

import android.content.Intent;

import com.trans.network.callback.JsonObjectCallback;
import com.trans.network.model.Response;
import com.trans.network.utils.GsonHelper;
import com.newsuper.t.sale.base.BaseApp;
import com.newsuper.t.sale.base.mvp.BasePresenter;
import com.newsuper.t.sale.callback.WxFaceCallback;
import com.newsuper.t.sale.entity.BaseResponse;
import com.newsuper.t.sale.entity.EntityResponse;
import com.newsuper.t.sale.entity.Member;
import com.newsuper.t.sale.ui.scan.PromptScanCodeActivity;
import com.newsuper.t.sale.utils.KeyConstrant;
import com.newsuper.t.sale.utils.UiUtils;

import java.util.Map;

public class FacePresenter extends BasePresenter<FaceContract.Model, FaceContract.View> implements FaceContract.Presenter {

    @Override
    protected FaceContract.Model createModule() {
        return new FaceModel();
    }

    @Override
    public void getFaceInfo() {
        getModule().getAuthInfo(getContext(), new WxFaceCallback() {
            @Override
            public void onAuthInfoSuccess(Map info) {
                if(isViewAttached()){
                    getView().onFaceAuthSuccess(info);
                }
            }

            @Override
            public void onAuthInfoError() {

            }
        });

    }

    @Override
    public void queryMemberInfo(Map info) {
        String openid = info.get("sub_openid").toString();
        getModule().queryMemberInfo(openid,new JsonObjectCallback<EntityResponse<Member>>(){
            @Override
            public void onSuccess(Response<EntityResponse<Member>> response) {
                EntityResponse<Member> body = response.body();
                if(body.getCode() == BaseResponse.SUCCESS){
                    Member member = body.getData();
                    Intent intent = new Intent(getContext(), PromptScanCodeActivity.class);
                    intent.putExtra(KeyConstrant.KEY_MEMBER, GsonHelper.toJson(member));
                    getContext().startActivity(intent);
                    getView().onQueryMemberSuccess();
                } else {
                    UiUtils.showToastShort(body.getMsg());
                }
            }

            @Override
            public void onError(Response<EntityResponse<Member>> response) {
                super.onError(response);
                UiUtils.showToastShort(response.message());
            }
        });
    }


}
