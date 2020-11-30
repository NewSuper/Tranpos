package com.newsuper.t.consumer.function.selectgoods.presenter;

import android.util.Log;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.AddressBean;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.ISelectAddressView;
import com.xunjoy.lewaimai.consumer.function.top.request.TopRequest;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class SelectAddressPresenter {
    private ISelectAddressView addressView;
    public SelectAddressPresenter(ISelectAddressView addressView){
        this.addressView = addressView;
    }
    /**
     * 获取收货地址
     * @param token
     */
    public void loadAddress(String token,String admin_id,String shop_id) {
        Map<String, String> map = TopRequest.getShopAddressRequest(token,admin_id,shop_id);
        HttpManager.sendRequest(UrlConst.GET_ADDRESS_LIST, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                AddressBean bean = new Gson().fromJson(response.toString(), AddressBean.class);
                addressView.loadAddress(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                addressView.showToast(result);
                addressView.loadFail();

            }

            @Override
            public void onCompleted() {
                addressView.dialogDismiss();
            }
        });
    }
    //获取可送范围的收货地址
    public void getInRangeAddress(LatLng lp, ArrayList<AddressBean.AddressList> lists,ArrayList<AddressBean.AddressList> in,ArrayList<AddressBean.AddressList> out){

        if (lp != null && lists != null){
            for (AddressBean.AddressList l : lists){
                /*float lat = StringUtils.isEmpty(l.lat)? 0 : Float.parseFloat(l.lat);
                float lng = StringUtils.isEmpty(l.lng)? 0 : Float.parseFloat(l.lng);
                Log.i("getInRangeAddress","111111111111   "+lp.latitude+"   "+lp.longitude);
                Log.i("getInRangeAddress","222222222222   "+l.lat+"   "+l.lng);
                LatLng ll = new LatLng(lat,lng);
                float dis = AMapUtils.calculateLineDistance(lp,ll);
                Log.i("getInRangeAddress",l.name+"  dis ="+dis);
                if (dis <= 5*1000){
                    Log.i("getInRangeAddress"," dasdad");
                    in.add(l);
                }else {
                    out.add(l);
                }*/
                if ("1".equals(l.isinarea)){
                    Log.i("getInRangeAddress"," dasdad");
                    in.add(l);
                }else {
                    out.add(l);
                }
            }
        }
    }
}
