package com.newsuper.t.markert.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.newsuper.t.markert.service.KeyboardHanlder;
import com.newsuper.t.markert.utils.ToolScanner;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BaseActivity extends AppCompatActivity implements CustomAdapt {

    private Unbinder bind;
    private ToolScanner scanner = getScanner();

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 384;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApp.getmAllActivitys().add(this);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        if(isSetSystemTransparent()){
            ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init();
        }
        initView();
        initData();
    }

    protected void initData() {

    }

    protected void initView() {

    }

    /**
     * 沉浸式
     * @return
     */
    protected boolean isSetSystemTransparent(){
        return true;
    }

    public void startActivity(Class<?> clazz){
        startActivity(new Intent(this,clazz));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApp.getmAllActivitys().remove(this);
        bind.unbind();
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if(scanner != null){
//            scanner.analysisKeyEvent(event);
//            return true;
//        }
//        return super.dispatchKeyEvent(event);
//    }

    private KeyboardHanlder keyboardHanlder;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (scanner != null){
          //  boolean isHandle = scanner.analysisKeyEvent(event);
            keyboardHanlder.handle(event);
        }
        return super.dispatchKeyEvent(event);
    }

    protected ToolScanner getScanner(){
        return null;
    }

    protected abstract int getLayoutId();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
