package com.newsuper.t.juejinbao.ui.share.base;

import android.os.Handler;
import android.os.Message;

public class ShareBasePresenter {
    protected Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dealWith(msg);
        }
    };

    protected void dealWith(Message msg) {

    }
}
