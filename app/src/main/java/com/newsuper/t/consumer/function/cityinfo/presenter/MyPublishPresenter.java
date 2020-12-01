package com.newsuper.t.consumer.function.cityinfo.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.MyPublishListBean;
import com.newsuper.t.consumer.bean.SetTopInfoBean;
import com.newsuper.t.consumer.bean.SetTopPayBean;
import com.newsuper.t.consumer.function.cityinfo.internal.IMyPublishView;
import com.newsuper.t.consumer.function.cityinfo.request.PublishRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/6/20 0020.
 */

public class MyPublishPresenter {
    private IMyPublishView publishView;
    public MyPublishPresenter(IMyPublishView publishView){
        this.publishView = publishView;
    }
    public void getPublishList(final int page){
        HashMap<String,String> map = PublishRequest.getMyPublishRequest(Const.ADMIN_ID,SharedPreferencesUtil.getToken(),page+"");
        HttpManager.sendRequest(UrlConst.PUBLISH_MY, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                MyPublishListBean bean = new Gson().fromJson(response,MyPublishListBean.class);
                if (page > 1){
                    publishView.getMorePublishList(bean);
                }else {
                    publishView.getPublishList(bean);
                }

                publishView.dialogDismiss();
            }
            @Override
            public void onRequestFail(String result, String code) {
                publishView.showToast(result);
                publishView.getPublishFail();
            }

            @Override
            public void onCompleted() {
                publishView.dialogDismiss();
            }
        });
    }
    public void getPublishListDel(String id){
        HashMap<String,String> map = PublishRequest.getMyPublishDelRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(),id);
        HttpManager.sendRequest(UrlConst.PUBLISH_MY_DEL, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                publishView.onDeleteSuccess();
            }
            @Override
            public void onRequestFail(String result, String code) {
                publishView.onDeleteFail();
                publishView.showToast(result);
            }

            @Override
            public void onCompleted() {
                publishView.dialogDismiss();
            }
        });
    }
    //置顶
    public void setToTop(String info_id,String top_num,String create_fee,String pay_type,String online_pay_type){
        HashMap<String,String> map = PublishRequest.setToTopRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(),info_id,top_num,create_fee,"7",pay_type,online_pay_type, RetrofitUtil.ADMIN_APP_ID);
        HttpManager.sendRequest(UrlConst.PUBLISH_SET_TOP, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                SetTopPayBean bean = new Gson().fromJson(response,SetTopPayBean.class);
                publishView.onSetTopSuccess(bean);
                publishView.dialogDismiss();
            }
            @Override
            public void onRequestFail(String result, String code) {
                publishView.onSetTopFail();
                publishView.showToast(result);
                publishView.dialogDismiss();
            }

            @Override
            public void onCompleted() {
                publishView.dialogDismiss();
            }
        });
    }
    public void getSetTopInfo(String info_id){
        HashMap<String,String> map = PublishRequest.getSetTopInfoRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(),info_id,"app",Const.ADMIN_APP_ID);
        HttpManager.sendRequest(UrlConst.PUBLISH_SET_TOP_INFO, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                SetTopInfoBean bean = new Gson().fromJson(response,SetTopInfoBean.class);
                publishView.getTopInfoSuccess(bean);
            }
            @Override
            public void onRequestFail(String result, String code) {
                publishView.getTopInfoFail();
                publishView.showToast(result);
            }

            @Override
            public void onCompleted() {
                publishView.dialogDismiss();
            }
        });
    }
    public void editPublish(String info_id, String first_category,String second_category, String contact_tel,String contact_name,String images,
                                                            String area_id,String business_id,String labs,String content){
        HashMap<String,String> map = PublishRequest.editPublishRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(),info_id,first_category,
                second_category,contact_tel,contact_name,images,area_id,business_id,labs,content);

        HttpManager.sendRequest(UrlConst.PUBLISH_EDIT, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                publishView.onEditSuccess();
            }
            @Override
            public void onRequestFail(String result, String code) {
                publishView.onEditFail();
                publishView.showToast(result);
            }

            @Override
            public void onCompleted() {
                publishView.dialogDismiss();
            }
        });
    }
}
