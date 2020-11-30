package com.newsuper.t.consumer.function.cityinfo.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.AgreementBean;
import com.newsuper.t.consumer.bean.CategoryBean;
import com.newsuper.t.consumer.bean.PublishAreabean;
import com.newsuper.t.consumer.bean.PublishListBean;
import com.newsuper.t.consumer.function.cityinfo.internal.IPublishView;
import com.newsuper.t.consumer.function.cityinfo.request.PublishRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/6/19 0019.
 */

public class PublishPresenter {
    private IPublishView iPublishView;

    public PublishPresenter(IPublishView iPublishView) {
        this.iPublishView = iPublishView;
    }

    public void getCategory(String type_id) {
        HashMap<String, String> map = PublishRequest.getCategoryRequest(Const.ADMIN_ID,SharedPreferencesUtil.getToken() ,type_id);
        HttpManager.sendRequest(UrlConst.PUBLISH_SECOND_CATEGORY, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                CategoryBean bean = new Gson().fromJson(response, CategoryBean.class);
                iPublishView.getSecondCategory(bean);
                iPublishView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                iPublishView.getSecondCategoryFail();
                iPublishView.showToast(result);
            }

            @Override
            public void onCompleted() {
                iPublishView.dialogDismiss();
            }
        });
    }

    public void getCategoryImg(String type_id) {
        HashMap<String, String> map = PublishRequest.getCategoryImgRequest(Const.ADMIN_ID,SharedPreferencesUtil.getToken(),type_id);
        HttpManager.sendRequest(UrlConst.PUBLISH_CATEGORY_ITEM, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                CategoryBean bean = new Gson().fromJson(response, CategoryBean.class);
                iPublishView.getSecondCategoryImg(bean);
                iPublishView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                iPublishView.getSecondCategoryFail();
                iPublishView.showToast(result);
            }

            @Override
            public void onCompleted() {
                iPublishView.dialogDismiss();
            }
        });
    }

    public void getPublishList(String token,String area_id,String first_category, String second_category, String sort, final int page) {
        HashMap<String, String> map = PublishRequest.getPublishListRequest(Const.ADMIN_ID,token,area_id ,first_category, second_category, sort, page + "");
        HttpManager.sendRequest(UrlConst.PUBLISH_LIST, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                PublishListBean bean = new Gson().fromJson(response, PublishListBean.class);
                if (page > 1) {
                    iPublishView.getMorePublishList(bean);
                } else {
                    iPublishView.getPublishList(bean);
                }

                iPublishView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                iPublishView.getPublishfail();
                iPublishView.showToast(result);
            }

            @Override
            public void onCompleted() {
                iPublishView.dialogDismiss();
            }
        });
    }

    public void reportPublish(String info_id, String report_type, String report_reason, String report_tel) {
        HashMap<String, String> map = PublishRequest.getReportRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(), info_id,report_type,report_reason,report_tel);
        HttpManager.sendRequest(UrlConst.PUBLISH_REPORT, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                iPublishView.onRepotSuccess();
                iPublishView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                iPublishView.showToast(result);
                iPublishView.onRepotFail();
            }

            @Override
            public void onCompleted() {
                iPublishView.dialogDismiss();
            }
        });
    }
    public void collectPublish(String info_id,String is_collect,String collect_id) {
        HashMap<String, String> map = PublishRequest.getCollectRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(), info_id,is_collect,collect_id);
        HttpManager.sendRequest(UrlConst.PUBLISH_COLLECT, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                iPublishView.onCollectSuccess();
                iPublishView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                iPublishView.showToast(result);
                iPublishView.onCollectFail();
            }

            @Override
            public void onCompleted() {
                iPublishView.dialogDismiss();
            }
        });
    }
    public void getAreaData(String lwm_sess_token, String lat, String lnt) {
        HashMap<String, String> map = PublishRequest.getPublishAreaRequest(Const.ADMIN_ID, lwm_sess_token,lat,lnt);
        HttpManager.sendRequest(UrlConst.PUBLISH_AREA, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                iPublishView.dialogDismiss();
                PublishAreabean bean = new Gson().fromJson(response, PublishAreabean.class);
                iPublishView.getPublishArea(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                iPublishView.showToast(result);
                iPublishView.getPublishAreaFail();
            }

            @Override
            public void onCompleted() {
                iPublishView.dialogDismiss();
            }
        });
    }
    public void deletePublish(String id){
        HashMap<String,String> map = PublishRequest.getMyPublishDelRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(),id);
        HttpManager.sendRequest(UrlConst.PUBLISH_MY_DEL, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                iPublishView.onDeleteSuccess();
            }
            @Override
            public void onRequestFail(String result, String code) {
                iPublishView.onDeleteFail();
                iPublishView.showToast(result);
            }

            @Override
            public void onCompleted() {
                iPublishView.dialogDismiss();
            }
        });
    }
}