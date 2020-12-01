package com.newsuper.t.consumer.function.distribution.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.PaoTuiBean;
import com.newsuper.t.consumer.function.distribution.internal.IDistributionView;
import com.newsuper.t.consumer.function.distribution.request.DistributionRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/19 0019.
 */

public class DistributionPresenter {
    private IDistributionView distributionView;
    public DistributionPresenter(IDistributionView locationView){
        this.distributionView = locationView;
    }
    public void loadData(String lat,String lng){
        Map<String, String> map = DistributionRequest.paotuiTopRequest(SharedPreferencesUtil.getToken(),Const.ADMIN_ID,lat,lng);
        HttpManager.sendRequest(UrlConst.PAO_TUI_TOP, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                PaoTuiBean bean = new Gson().fromJson(response,PaoTuiBean.class);
                distributionView.onLoadData(bean);
                distributionView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                distributionView.dialogDismiss();
                distributionView.onLoadFail();
            }

            @Override
            public void onCompleted() {
                distributionView.dialogDismiss();
            }
        });
    }
}
