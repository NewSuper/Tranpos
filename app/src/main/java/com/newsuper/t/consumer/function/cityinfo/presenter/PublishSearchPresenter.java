package com.newsuper.t.consumer.function.cityinfo.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.PublishListBean;
import com.newsuper.t.consumer.bean.PublishSearchBean;
import com.newsuper.t.consumer.function.cityinfo.internal.IPublishSearchView;
import com.newsuper.t.consumer.function.cityinfo.request.PublishRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/6/20 0020.
 */

public class PublishSearchPresenter {
    IPublishSearchView iPublishSearchView;
    public PublishSearchPresenter (IPublishSearchView iPublishSearchView){
        this.iPublishSearchView = iPublishSearchView;
    }
    public void getPublishSearch(String search, final int page){
        HashMap<String,String> map = PublishRequest.getPublishSearchRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(),search,page+"");
        HttpManager.sendRequest(UrlConst.PUBLISH_SEARCH, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                iPublishSearchView.dialogDismiss();
                PublishSearchBean bean = new Gson().fromJson(response,PublishSearchBean.class);
                if (page > 1){
                    iPublishSearchView.getMoreSearchData(bean);
                }else {
                    iPublishSearchView.getSearchData(bean);
                }

            }
            @Override
            public void onRequestFail(String result, String code) {
                iPublishSearchView.searchFail();
                iPublishSearchView.dialogDismiss();
            }

            @Override
            public void onCompleted() {
                iPublishSearchView.dialogDismiss();
            }
        });
    }
}
