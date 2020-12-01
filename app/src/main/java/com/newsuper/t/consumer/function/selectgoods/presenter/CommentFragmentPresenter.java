package com.newsuper.t.consumer.function.selectgoods.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.CommentBean;
import com.newsuper.t.consumer.function.selectgoods.inter.ICommentFragmentView;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import java.util.Map;


public class CommentFragmentPresenter {

    private ICommentFragmentView commentFragmentView;

    public CommentFragmentPresenter(ICommentFragmentView commentFragmentView){
        this.commentFragmentView = commentFragmentView;
    }

    public void loadData(String url,Map<String,String> request){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                commentFragmentView.dialogDismiss();
                CommentBean bean = new Gson().fromJson(response.toString(),CommentBean.class);
                commentFragmentView.showDataToVIew(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                commentFragmentView.dialogDismiss();
                commentFragmentView.showToast(result);
            }

            @Override
            public void onCompleted() {
                commentFragmentView.dialogDismiss();
            }
        });

    }

}