package com.newsuper.t.consumer.function.distribution.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.PayBean;
import com.newsuper.t.consumer.function.distribution.PayOrderActivity;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.CustomToolbar;

import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.CALL_PHONE;
import static com.newsuper.t.consumer.function.distribution.PayOrderActivity.WHERE_FROM;
import static com.newsuper.t.consumer.function.distribution.PayOrderActivity.WHERE_FROM_ORDER_PAY;
import static com.newsuper.t.consumer.function.distribution.PayOrderActivity.WHERE_FROM_ORDER_SMALL_FEE;
import static com.newsuper.t.consumer.manager.RetrofitManager.BASE_URL_H5;

/**
 * Created by Administrator on 2018/5/14 0014.
 */
@SuppressLint("SetJavaScriptEnabled")
public class PaotuiOrderFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    private static String url_order = BASE_URL_H5 + "h5/lwm/paotui/order_index?from_type=android";
    private static String url_detail = BASE_URL_H5 + "h5/lwm/paotui/order_detail?from_type=android";
    private static final int REFRESH_CODE = 4555;
    private static final int CALL_PHONE_REQUEST_CODE = 45454;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_paotui_oreder, null);
        ButterKnife.bind(this, view);
        btnLogin.setOnClickListener(this);
        llLogin.setOnClickListener(this);
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }

            @Override
            public void onMenuClick() {

            }
        });
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        }*/
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);// 设置缓存
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);//禁止水平滚动
        webView.setVerticalScrollBarEnabled(true);//允许垂直滚动
        //如果不设置WebViewClient，请求会跳转系统浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                return false;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!StringUtils.isEmpty(title) && !title.contains("http")) {
                    toolbar.setTitleText(title);
                }
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                // 步骤2：根据协议的参数，判断是否是所需要的url
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
                LogUtil.log("androidMethod", "message == " + message);
                LogUtil.log("androidMethod", "url == " + url);
                Uri uri = Uri.parse(message);
                LogUtil.log("androidMethod", "js == " + uri.getScheme());
                LogUtil.log("androidMethod", "lewaimai == " + uri.getAuthority());
                if (uri.getScheme().equals("js") && uri.getAuthority().equals("lewaimai")) {
                    String type = uri.getQueryParameter("type");
                    LogUtil.log("androidMethod", "type == " + type);
                    if (!StringUtils.isEmpty(type)) {
                        if ("1".equals(type)) {
                            //去支付
                            String json = uri.getQueryParameter("json");
                            toPayOrder(json);
                        } else if ("0".equals(type)) {
                            //下单
                            toCreateOrder();
                        } else if ("2".equals(type)) {
                            //加小费
                            String json = uri.getQueryParameter("json");
                            toPaySmallFee(json);
                        } else if ("3".equals(type)) {
                            //加小费
                            String phone = uri.getQueryParameter("phone");
                            toCallPhone(phone);
                        }
                    }
                    result.cancel();
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
        if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
            llLogin.setVisibility(View.VISIBLE);
        }else {
            loadWeb();
        }
        return view;
    }

    private void loadWeb() {
        webView.clearHistory();
        webView.clearFormData();
        webView.clearCache(true);
        String where_from = getArguments().getString("where_from");
        LogUtil.log("loadWeb", "where_form = " + where_from);
        if ("order_list".equals(where_from)) {
            toolbar.setTitleText("我的订单");
            String url = url_order + "&admin_id=" + SharedPreferencesUtil.getAdminId();
            String cookie = "lwm_sess_token=" + SharedPreferencesUtil.getToken();
            LogUtil.log("loadWeb", "url = " + url);
            //链接加入coookie ，值为token
            syncCookie(url, cookie);
            LogUtil.log("WeiWebView", url);
            webView.loadUrl(url);
        } else if ("order_detail".equals(where_from)) {
            toolbar.setTitleText("订单详情");
            String cookie = "lwm_sess_token=" + SharedPreferencesUtil.getToken();
            String order_id = getArguments().getString("order_id");
            String url = url_detail + "&admin_id=" + SharedPreferencesUtil.getAdminId() + "&order_id=" + order_id;
            LogUtil.log("loadWeb", "url = " + url);
            //链接加入coookie ，值为token
            syncCookie(url, cookie);
            LogUtil.log("WeiWebView", url);
            webView.loadUrl(url);
        }
    }
    public void refresh(){
        if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
            llLogin.setVisibility(View.VISIBLE);
        }else {
            llLogin.setVisibility(View.GONE);
            loadWeb();
        }
    }

    /**
     * 将cookie同步到WebView
     *
     * @param url    WebView要加载的url
     * @param cookie 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public void syncCookie(String url, String cookie) {
       /* if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookie);//如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可*/

        CookieSyncManager.createInstance(getContext());
        CookieManager cm = CookieManager.getInstance();
        cm.setAcceptCookie(true);
        cm.removeAllCookie();
        cm.setCookie(url, cookie);
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }
    }

    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        getActivity().finish();
    }

    public void toPayOrder(String json) {
        LogUtil.log("androidMethod", "toPayOrder == " + json);
        if (!StringUtils.isEmpty(json)) {
            PayBean bean = new Gson().fromJson(json, PayBean.class);
            Intent pIntent = new Intent(getContext(), PayOrderActivity.class);
            pIntent.putExtra(WHERE_FROM, WHERE_FROM_ORDER_PAY);
            pIntent.putStringArrayListExtra("pay_type", bean.paytype);
            pIntent.putExtra("title", bean.title);
            pIntent.putExtra("order_id", bean.order_id);
            pIntent.putExtra("pay_money", bean.total_price);
            pIntent.putExtra("time", bean.init_time);
            startActivityForResult(pIntent, REFRESH_CODE);
        }
    }

    public void toCreateOrder() {
        LogUtil.log("androidMethod", "toCreateOrder");
        getActivity().finish();
    }

    public void toCallPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return;
        }
        if ((ContextCompat.checkSelfPermission(getContext(), CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{CALL_PHONE},
                    CALL_PHONE_REQUEST_CODE);
        } else {
            Uri uri = Uri.parse("tel:" + phone);
            Intent callIntent = new Intent(Intent.ACTION_CALL, uri);
            startActivity(callIntent);
        }
    }

    public void toPaySmallFee(String json) {
        LogUtil.log("androidMethod", "toPaySmallFee == " + json);
        if (!StringUtils.isEmpty(json)) {
            PayBean bean = new Gson().fromJson(json, PayBean.class);
            Intent pIntent = new Intent(getContext(), PayOrderActivity.class);
            pIntent.putExtra(WHERE_FROM, WHERE_FROM_ORDER_SMALL_FEE);
            pIntent.putStringArrayListExtra("pay_type", bean.paytype);
            pIntent.putExtra("title", bean.title);
            pIntent.putExtra("order_id", bean.order_id);
            pIntent.putExtra("pay_money", bean.tip_price);
            pIntent.putExtra("time", "");
            startActivityForResult(pIntent, REFRESH_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == REFRESH_CODE) {
                LogUtil.log("androidMethod", "refresh");
                webView.reload();
            }
        }
        if (requestCode == Const.GO_TO_LOGIN && resultCode == getActivity().RESULT_OK){
            llLogin.setVisibility(View.GONE);
            loadWeb();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == CALL_PHONE_REQUEST_CODE) {
            if (paramArrayOfInt[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                UIUtils.showToast("请允许使用电话权限！");
            }
        }
    }

    @Override
    public void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent1, Const.GO_TO_LOGIN);
                break;
        }
    }
}
