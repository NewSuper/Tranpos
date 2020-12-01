package com.newsuper.t.consumer.function.person.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.MsgDetailBean;
import com.newsuper.t.consumer.function.person.internal.IMessageDetailView;
import com.newsuper.t.consumer.function.person.request.MessageCenterRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.Map;

/**
 * Created by Administrator on 2018/7/24 0024.
 */

public class MessageDetailPresenter {
    private IMessageDetailView detailView;
    public MessageDetailPresenter(IMessageDetailView detailView){
        this.detailView = detailView;
    }
    //获得顾客信息
    public void getMsgDetail(String id) {
        Map<String, String> map = MessageCenterRequest.msgDetailRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID,id);
        HttpManager.sendRequest(UrlConst.MSG_DETAIL, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                detailView.dialogDismiss();
                MsgDetailBean bean = new Gson().fromJson(response.toString(), MsgDetailBean.class);
                detailView.getMsgDetailSuccess(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                detailView.dialogDismiss();
                detailView.showToast(result);
                detailView.getMsgDetailFail();
            }

            @Override
            public void onCompleted() {
                detailView.dialogDismiss();
            }
        });
    }
}
