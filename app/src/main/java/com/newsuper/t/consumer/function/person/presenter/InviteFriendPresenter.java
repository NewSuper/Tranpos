package com.newsuper.t.consumer.function.person.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.bean.InviteFriendBean;
import com.newsuper.t.consumer.function.person.internal.IInviteFriendView;
import com.newsuper.t.consumer.function.person.request.AddressRequest;
import com.newsuper.t.consumer.function.person.request.InviteFriendRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

public class InviteFriendPresenter {
    private IInviteFriendView  friendView;
    public InviteFriendPresenter(IInviteFriendView  friendView){
        this.friendView = friendView;
    }
    public void loadData(String token,String type,String lat,String lnt) {
        HashMap<String, String> map = InviteFriendRequest.inviteRequest(token, SharedPreferencesUtil.getAdminId(),type,lat,lnt);
        HttpManager.sendRequest(UrlConst.INVITE_FRIEND, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                friendView.dialogDismiss();
                InviteFriendBean bean = new Gson().fromJson(response.toString(), InviteFriendBean.class);
                friendView.loadInviteData(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                friendView.dialogDismiss();
                friendView.showToast(result);
                friendView.loadInviteFail();
            }

            @Override
            public void onCompleted() {
                friendView.dialogDismiss();
            }
        });
    }



}
