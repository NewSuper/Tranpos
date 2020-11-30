package com.newsuper.t.consumer.function.cityinfo.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.CategoryBean;
import com.newsuper.t.consumer.function.cityinfo.internal.ICityInfoView;
import com.newsuper.t.consumer.function.cityinfo.request.PublishRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/7/26 0026.
 */

public class CityInfoPresenter {
    private ICityInfoView infoView;
    public CityInfoPresenter (ICityInfoView infoView){
        this.infoView = infoView;
    }
    public void getCategoryImg(String type_id) {
        HashMap<String, String> map = PublishRequest.getCategoryImgRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(),type_id);
        HttpManager.sendRequest(UrlConst.PUBLISH_CATEGORY_ITEM, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                CategoryBean bean = new Gson().fromJson(response, CategoryBean.class);
                infoView.getSecondCategoryImg(bean);
                infoView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                infoView.getSecondCategoryImgFail();
                infoView.showToast(result);
            }

            @Override
            public void onCompleted() {
                infoView.dialogDismiss();
            }
        });
    }

}

