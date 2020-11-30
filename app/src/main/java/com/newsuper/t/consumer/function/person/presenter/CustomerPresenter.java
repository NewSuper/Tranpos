package com.newsuper.t.consumer.function.person.presenter;


import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.CustomerInfoBean;
import com.newsuper.t.consumer.bean.MsgCountBean;
import com.newsuper.t.consumer.bean.PersonCenterBean;
import com.newsuper.t.consumer.function.person.internal.ICustomerView;
import com.newsuper.t.consumer.function.person.internal.IPersonStyleView;
import com.newsuper.t.consumer.function.person.request.CustomerInfoRequest;
import com.newsuper.t.consumer.function.person.request.MessageCenterRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;
import java.util.Map;

public class CustomerPresenter {
    private ICustomerView mCustomerView;
    private IPersonStyleView styleView;

    public CustomerPresenter(ICustomerView mCustomerView) {
        this.mCustomerView = mCustomerView;
    }
    public CustomerPresenter(ICustomerView mCustomerView,IPersonStyleView styleView) {
        this.mCustomerView = mCustomerView;
        this.styleView = styleView;
    }
    //获得顾客信息
    public void loadata(String url, Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mCustomerView.dialogDismiss();
                CustomerInfoBean bean = new Gson().fromJson(response.toString(), CustomerInfoBean.class);
              //  mCustomerView.loadUserCenter(bean);
                mCustomerView.showUserCenter(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mCustomerView.dialogDismiss();
//                mCustomerView.showToast(result);
            }

            @Override
            public void onCompleted() {
                mCustomerView.dialogDismiss();
            }
        });
    }

    public void loadData(){
        HashMap<String,String>map = CustomerInfoRequest.customerRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID);
        HttpManager.sendRequest(UrlConst.CUSTOMERINFO, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mCustomerView.dialogDismiss();
                CustomerInfoBean bean = new Gson().fromJson(response.toString(), CustomerInfoBean.class);
                mCustomerView.showUserCenter(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mCustomerView.dialogDismiss();
                mCustomerView.showToast(result);
            }

            @Override
            public void onCompleted() {
                mCustomerView.dialogDismiss();
            }
        });
    }
    //获得消息数量
    public void getMsgCount() {
        Map<String, String> map = MessageCenterRequest.msgReadRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID,"1",Const.ADMIN_APP_ID);
        HttpManager.sendRequest(UrlConst.MSG_READ, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mCustomerView.dialogDismiss();
                MsgCountBean bean = new Gson().fromJson(response.toString(), MsgCountBean.class);
                mCustomerView.getMsgCount(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mCustomerView.dialogDismiss();
                mCustomerView.showToast(result);
            }

            @Override
            public void onCompleted() {
                mCustomerView.dialogDismiss();
            }
        });
    }

    //获得消息数量
    public void getStyleData() {
        Map<String, String> map = CustomerInfoRequest.styleRequest(SharedPreferencesUtil.getAreaId(), Const.ADMIN_ID,"app");
        HttpManager.sendRequest(UrlConst.PERSON_STYLE, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                styleView.dialogDismiss();
                PersonCenterBean bean = new Gson().fromJson(response.toString(), PersonCenterBean.class);
                styleView.getStyleData(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                styleView.dialogDismiss();
                styleView.showToast(result);
                styleView.getStyleFail();
            }

            @Override
            public void onCompleted() {
                styleView.dialogDismiss();
            }
        });
    }
}
