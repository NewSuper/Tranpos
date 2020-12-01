package com.newsuper.t.consumer.function.person.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.CollectionBean;
import com.newsuper.t.consumer.function.person.internal.ICollectionView;
import com.newsuper.t.consumer.function.person.request.CollectionRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取我收藏店铺列表的业务
 */

public class CollectionPresenter {
    private ICollectionView mCollectionView;

    public CollectionPresenter(ICollectionView mCollectionView) {
        this.mCollectionView = mCollectionView;
    }

    public void loadData(String url, Map<String, String> map) {
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mCollectionView.dialogDismiss();
                CollectionBean bean = new Gson().fromJson(response.toString(), CollectionBean.class);
                mCollectionView.showDataToVIew(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mCollectionView.dialogDismiss();
                mCollectionView.showToast(result);
                mCollectionView.loadFail();
            }

            @Override
            public void onCompleted() {
                mCollectionView.dialogDismiss();
            }
        });

    }

    public void loadData(String url, String token,String admin_id,String latitude,String longitude) {
        HashMap<String, String> map = CollectionRequest.collectionRequest(token,admin_id,latitude,longitude);
        HttpManager.sendRequest(url, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                mCollectionView.dialogDismiss();
                CollectionBean bean = new Gson().fromJson(response.toString(), CollectionBean.class);
                mCollectionView.showDataToVIew(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                mCollectionView.dialogDismiss();
                mCollectionView.showToast(result);
                mCollectionView.loadFail();
            }

            @Override
            public void onCompleted() {
                mCollectionView.dialogDismiss();
            }
        });
    }

}

