package com.newsuper.t.consumer.function.order.presenter;

import com.xunjoy.lewaimai.consumer.function.inter.IEditOrderFragmentView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;

import java.util.Map;


public class EditListPresenter {

    private IEditOrderFragmentView editOrderFragmentView;

    public EditListPresenter(IEditOrderFragmentView editOrderFragmentView){
        this.editOrderFragmentView = editOrderFragmentView;
    }

    public void loadData(String url, Map<String,String> request, final String flag, final int position){
        HttpManager.sendRequest(url, request, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                editOrderFragmentView.dialogDismiss();
                editOrderFragmentView.notifyOrderList(flag,position);
            }

            @Override
            public void onRequestFail(String result, String code) {
                editOrderFragmentView.dialogDismiss();
                editOrderFragmentView.showToast(result);
                UIUtils.showToast(result);
            }

            @Override
            public void onCompleted() {
                editOrderFragmentView.dialogDismiss();
            }
        });
    }

}