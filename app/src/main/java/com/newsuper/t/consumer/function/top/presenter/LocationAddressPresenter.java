package com.newsuper.t.consumer.function.top.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.function.top.internal.ITopLocationView;
import com.newsuper.t.consumer.function.top.request.TopRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class LocationAddressPresenter {
    public ITopLocationView locationView;
    public LocationAddressPresenter(ITopLocationView locationView){
        this.locationView = locationView;
    }
    /**
     * 获取收货地址
     * @param token
     */
    public void loadAddress(String token,String admin_id) {
        Map<String, String> map = TopRequest.getAddressRequest(token,admin_id);
        HttpManager.sendRequest(UrlConst.GET_ADDRESS_LIST, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                AddressBean bean = new Gson().fromJson(response.toString(), AddressBean.class);
                locationView.showAddress(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                locationView.showToast(result);
                locationView.loadFail();

            }

            @Override
            public void onCompleted() {
                locationView.dialogDismiss();
            }
        });
    }
}
