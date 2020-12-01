package com.newsuper.t.consumer.function.cityinfo.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.PublishDetailBean;
import com.newsuper.t.consumer.function.cityinfo.internal.IPublishDetailView;
import com.newsuper.t.consumer.function.cityinfo.request.PublishRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class PublishDetailPresenter  {
    private IPublishDetailView detailView;
    public PublishDetailPresenter(IPublishDetailView detailView){
        this.detailView = detailView;
    }
    public void getPublishDetail(String lwm_sess_token,String info_id,
                                String sign_type,String lat,String lnt){
        HashMap<String,String> map = PublishRequest.getPublishDetailRequest(Const.ADMIN_ID,lwm_sess_token,info_id,sign_type,lat,lnt,"app", RetrofitUtil.ADMIN_APP_ID);
        HttpManager.sendRequest(UrlConst.PUBLISH_DETAIL, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                PublishDetailBean bean = new Gson().fromJson(response,PublishDetailBean.class);
                detailView.getPublishData(bean);
                detailView.dialogDismiss();
            }
            @Override
            public void onRequestFail(String result, String code) {
                detailView.getPublishDataFail();
                detailView.showToast(result);
                detailView.dialogDismiss();
            }

            @Override
            public void onCompleted() {
                detailView.dialogDismiss();
            }
        });
    }
    public void reportPublish(String info_id, String report_type, String report_reason, String report_tel) {
        HashMap<String, String> map = PublishRequest.getReportRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(), info_id,report_type,report_reason,report_tel);
        HttpManager.sendRequest(UrlConst.PUBLISH_REPORT, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                detailView.onReportSuccess();
                detailView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                detailView.showToast(result);
                detailView.onReportFail();
            }

            @Override
            public void onCompleted() {
                detailView.dialogDismiss();
            }
        });
    }
    public void collectPublish(String info_id,String is_collect,String collect_id) {
        if ("2".equals(is_collect)){
            info_id = "";
        }else {
            collect_id = "";
        }
        HashMap<String, String> map = PublishRequest.getCollectRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(), info_id,is_collect,collect_id);
        HttpManager.sendRequest(UrlConst.PUBLISH_COLLECT, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                detailView.onCollectSuccess();
                detailView.dialogDismiss();
            }

            @Override
            public void onRequestFail(String result, String code) {
                detailView.showToast(result);
                detailView.onCollectFail();
            }

            @Override
            public void onCompleted() {
                detailView.dialogDismiss();
            }
        });
    }
}
