package com.newsuper.t.consumer.function.person.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.AddressBean;
import com.xunjoy.lewaimai.consumer.function.person.internal.IAddressView;
import com.xunjoy.lewaimai.consumer.function.person.request.AddressRequest;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取我的地址业务处理
 */

public class AddressPresenter {
    private IAddressView mAddressView;

    public AddressPresenter(IAddressView mAddressView) {
        this.mAddressView = mAddressView;
    }

    public void loadData(String url, final Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mAddressView.dialogDismiss();
                AddressBean bean = new Gson().fromJson(response.toString(), AddressBean.class);
                mAddressView.showDataToVIew(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mAddressView.dialogDismiss();
                mAddressView.showToast(result);
                mAddressView.loadFail();
            }

            @Override
            public void onCompleted() {
                mAddressView.dialogDismiss();
            }
        });
    }

    public void loadData(String url, String token) {
        HashMap<String, String> map = AddressRequest.addreessRequest(token, SharedPreferencesUtil.getAdminId());
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mAddressView.dialogDismiss();
                AddressBean bean = new Gson().fromJson(response.toString(), AddressBean.class);
                mAddressView.showDataToVIew(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mAddressView.dialogDismiss();
                mAddressView.showToast(result);
                mAddressView.loadFail();
            }

            @Override
            public void onCompleted() {
                mAddressView.dialogDismiss();
            }
        });
    }
}
