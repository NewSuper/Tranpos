package com.newsuper.t.consumer.function.order.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.ShopCommentInfo;
import com.newsuper.t.consumer.function.inter.ICommitComments;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.Map;


public class CommitCommentsPresenter {

    private ICommitComments commitComments;

    public CommitCommentsPresenter(ICommitComments commitComments){
        this.commitComments = commitComments;
    }

    public void loadData(String url,Map<String,String> request){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                commitComments.dialogDismiss();
                commitComments.completeComments();
            }

            @Override
            public void onRequestFail(String result, String code) {
                commitComments.dialogDismiss();
                commitComments.showToast(result);
            }

            @Override
            public void onCompleted() {
                commitComments.dialogDismiss();
            }
        });

    }
    public void getCommentInfo(Map<String,String> request){
        HttpManager.sendRequest(UrlConst.SHOP_COMMENT_INFO, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                ShopCommentInfo info = new Gson().fromJson(response,ShopCommentInfo.class);
                commitComments.getCommentInfo(info);
            }

            @Override
            public void onRequestFail(String result, String code) {
                commitComments.dialogDismiss();
                commitComments.showToast(result);
                commitComments.getFail();
            }

            @Override
            public void onCompleted() {
                commitComments.dialogDismiss();
            }
        });

    }

}