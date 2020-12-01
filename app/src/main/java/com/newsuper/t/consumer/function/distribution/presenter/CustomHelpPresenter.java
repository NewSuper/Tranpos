package com.newsuper.t.consumer.function.distribution.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.CustomAddressBean;
import com.newsuper.t.consumer.function.distribution.internal.ICustomeHelpView;
import com.newsuper.t.consumer.function.distribution.internal.IHelpView;
import com.newsuper.t.consumer.function.distribution.request.DistributionRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class CustomHelpPresenter extends HelpPresenter {
    public IHelpView helpView;
    public CustomHelpPresenter(IHelpView helpView) {
        super(helpView);
        this.helpView = helpView;
    }
    /**
     * 获取数据
     */
    public void loadAddress(String lat,String lng){
        Map<String,String> map = DistributionRequest.addressRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID,lat,lng);
        HttpManager.sendRequest(UrlConst.PAO_ADDRESS, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                CustomAddressBean bean = new Gson().fromJson(response,CustomAddressBean.class);
                ((ICustomeHelpView)helpView).getAddress(bean);
                helpView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                helpView.dialogDismiss();
                helpView.showToast(result);
            }

            @Override
            public void onCompleted() {
                helpView.dialogDismiss();
            }
        });
    }
}
