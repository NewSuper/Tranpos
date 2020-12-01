package com.newsuper.t.consumer.function.person.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.bean.DelAddressBean;
import com.newsuper.t.consumer.function.person.internal.IAddressView;
import com.newsuper.t.consumer.function.person.request.CollectionRequest;
import com.newsuper.t.consumer.function.person.request.DelAddressRequest;
import com.newsuper.t.consumer.function.person.request.NewddressRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 新建地址业务
 */

public class NewAddressPresenter {
    private IAddressView mAddressView;

    public NewAddressPresenter (IAddressView mAddressView){
        this.mAddressView = mAddressView;
    }

    public void delAddress(String url,Map<String,String>map){
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
             mAddressView.dialogDismiss();
                DelAddressBean bean = new Gson().fromJson(response.toString(),DelAddressBean.class);
                mAddressView.showDelDataView(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mAddressView.dialogDismiss();
                mAddressView.showToast(result);
            }

            @Override
            public void onCompleted() {
                mAddressView.dialogDismiss();
            }
        });
    }
    public void addAddress(String url, Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mAddressView.dialogDismiss();
                AddressBean bean = new Gson().fromJson(response.toString(), AddressBean.class);
                mAddressView.showDataToVIew(bean);
                mAddressView.refresh(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mAddressView.dialogDismiss();
                mAddressView.showToast(result);
            }

            @Override
            public void onCompleted() {
                mAddressView.dialogDismiss();
            }
        });
    }

}
