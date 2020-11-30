package com.newsuper.t.consumer.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.newsuper.t.R;

/**
 * Created by Administrator on 2017/6/8 0008.
 * 加载
 */

public class LoadingDialog extends AlertDialog {
    TextView tvLoad;

    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialog);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_consumer_loading);
        tvLoad = (TextView)findViewById(R.id.tv_load);
    }
    public void setTitle(String s){
        if (tvLoad != null){
            tvLoad.setText(s);
            Log.i("LoadingDialog","11111111111");
        }
    }

    public void showWithTitle(String s){
        ((TextView)findViewById(R.id.tv_load)).setText(s);
        Log.i("LoadingDialog","11111111111");
        show();
    }

    @Override
    public void show() {
        Log.i("LoadingDialog","22222");
        super.show();
    }
}
