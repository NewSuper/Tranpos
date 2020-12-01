package com.newsuper.t.consumer.function.vip.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.VipCommitInfoBean;
import com.newsuper.t.consumer.function.vip.inter.IVipCommitView;
import com.newsuper.t.consumer.function.vip.request.VipInfoCommitRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;


public class VipInfoCommitPresenter {
    private IVipCommitView vipCommitView;
    public VipInfoCommitPresenter(IVipCommitView vipCommitView){
        this.vipCommitView = vipCommitView;
    }
    public void commitVipInfo(String token, String admin_id,String name,String sex,String birthday,String address){
        HashMap<String,String> map =VipInfoCommitRequest.vipInfoCommitRequest(token, admin_id,name,sex,birthday,address);
        HttpManager.sendRequest(UrlConst.VIP_COMMIT_INFO, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                vipCommitView.dialogDismiss();
                VipCommitInfoBean bean = new Gson().fromJson(response,VipCommitInfoBean.class);
                vipCommitView.commitVipInfo(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                vipCommitView.dialogDismiss();
                vipCommitView.showToast(result);
                vipCommitView.loadFail();
            }

            @Override
            public void onCompleted() {
                vipCommitView.dialogDismiss();
            }

        });
    }
    public void modifyVipInfo(String token, String admin_id,String name,String sex,String birthday,String address){
        HashMap<String,String> map =VipInfoCommitRequest.vipInfoCommitRequest(token, admin_id,name,sex,birthday,address);
        HttpManager.sendRequest(UrlConst.VIP_MODIFY_INFO, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                vipCommitView.dialogDismiss();
                VipCommitInfoBean bean = new Gson().fromJson(response,VipCommitInfoBean.class);
                vipCommitView.commitVipInfo(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                vipCommitView.dialogDismiss();
                vipCommitView.showToast(result);
                vipCommitView.loadFail();
            }

            @Override
            public void onCompleted() {
                vipCommitView.dialogDismiss();
            }

        });
    }
}
