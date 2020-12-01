package com.newsuper.t.consumer.function.person.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.MsgCenterBean;
import com.newsuper.t.consumer.function.person.internal.IMessageCenterView;
import com.newsuper.t.consumer.function.person.request.MessageCenterRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.Map;

/**
 * Created by Administrator on 2018/7/23 0023.
 */

public class MessageCenterPresenter {
    private IMessageCenterView centerView;
    public MessageCenterPresenter(IMessageCenterView centerView ){
        this.centerView = centerView;
    }

    //获得顾客信息
    public void getMsgList() {
        Map<String, String> map = MessageCenterRequest.msgListRequest(SharedPreferencesUtil.getToken(),Const.ADMIN_ID,"1",Const.ADMIN_APP_ID);
        HttpManager.sendRequest(UrlConst.MSG_LIST, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                centerView.dialogDismiss();
                MsgCenterBean bean = new Gson().fromJson(response.toString(), MsgCenterBean.class);
                centerView.getMessageDataSuccess(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                centerView.dialogDismiss();
                centerView.showToast(result);
                centerView.getMessageDataFail();
            }

            @Override
            public void onCompleted() {
                centerView.dialogDismiss();
            }
        });
    }


}
