package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

//import com.igexin.sdk.PushManager;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityDebugBinding;
import com.juejinchain.android.module.home.presenter.impl.PublicPresenterImpl;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.LoginEntity;
import com.ys.network.base.PagerCons;
import com.ys.network.network.RetrofitManager;
import com.ys.network.utils.ActivityCollector;

import butterknife.OnClick;
import io.paperdb.Paper;

public class DebugActivity extends BaseActivity<PublicPresenterImpl , ActivityDebugBinding> {

    private String api;
//    private String res;
    private String web;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_debug;
    }

    @Override
    public void initView() {
    }

    @OnClick({R.id.rl_back,R.id.tv_api_uat, R.id.tv_api_bat,R.id.tv_api_pat,R.id.tv_res_local,R.id.tv_web_uat,R.id.tv_web_bat,R.id.tv_web_pat,R.id.tv_complete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                return;
            case R.id.tv_api_uat:
            case R.id.tv_web_uat:
                api = mViewBinding.tvApiUat.getText().toString().replace("测试：","");
//                res = RetrofitManager.WEB_URL_COMMON0;
                web = mViewBinding.tvWebUat.getText().toString().replace("测试：","");
                break;
            case R.id.tv_api_bat:
            case R.id.tv_web_bat:
                api = mViewBinding.tvApiBat.getText().toString().replace("预发布：","");
//                res = RetrofitManager.WEB_URL_COMMON0;
                web = mViewBinding.tvWebBat.getText().toString().replace("预发布：","");
                break;
            case R.id.tv_api_pat:
            case R.id.tv_web_pat:
                api = mViewBinding.tvApiPat.getText().toString().replace("线上：","");
//                res = RetrofitManager.WEB_URL_COMMON0;
                web = mViewBinding.tvWebPat.getText().toString().replace("线上：","");
                break;
            case R.id.tv_res_local:
//                res = RetrofitManager.WEB_URL_COMMON0;
                break;
            case R.id.tv_complete:
                restartApp();
                return;
        }

        mViewBinding.tvApi.setText(api);
//        mViewBinding.tvRes.setText(res);
        mViewBinding.tvWeb.setText(web);
    }

    private void restartApp() {
        LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (loginEntity != null)
            Paper.book().delete(PagerCons.USER_DATA);

        RetrofitManager.destroySelf();
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        if(intent==null){
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //与正常页面跳转一样可传递序列化数据,在Launch页面内获得
        intent.putExtra("api",api);
//        intent.putExtra("res",res);
        intent.putExtra("web",web);
        startActivity(intent);
        ActivityCollector.removeAllActivity();
    }

    @Override
    public void initData() {
        api = RetrofitManager.APP_URL_DOMAIN;
//        res = RetrofitManager.WEB_URL_COMMON;
        web = RetrofitManager.VIP_JS_URL;
        mViewBinding.tvApi.setText(api);
//        mViewBinding.tvRes.setText(res);
        mViewBinding.tvWeb.setText(web);
    }

    public static void intentMe(Context context) {
        Intent intent = new Intent(context, DebugActivity.class);
        context.startActivity(intent);
    }

}
