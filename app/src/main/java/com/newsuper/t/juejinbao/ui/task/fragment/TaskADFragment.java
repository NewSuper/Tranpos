package com.newsuper.t.juejinbao.ui.task.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentTaskAdBinding;
import com.juejinchain.android.jsbridge.BridgeWebView;
import com.juejinchain.android.module.home.presenter.impl.PublicPresenterImpl;
import com.juejinchain.android.module.movie.utils.Utils;
import com.ys.network.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.TELEPHONY_SERVICE;

public class TaskADFragment extends BaseFragment<PublicPresenterImpl, FragmentTaskAdBinding> {

    //加载地址
    private String url;
    private int id;
    protected BridgeWebView webView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_ad, container, false);
        webView = view.findViewById(R.id.webView);
        return view;
    }

    public static TaskADFragment newInstance(Bundle data) {
        TaskADFragment fragment = new TaskADFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getArguments()!=null) {
            url = getArguments().getString("url");
            id = getArguments().getInt("id",0);
        }
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        if(!TextUtils.isEmpty(url)){
            webView.loadUrl(url);
            try {
                TelephonyManager TelephonyMgr = (TelephonyManager) getActivity().getSystemService(TELEPHONY_SERVICE);
                @SuppressLint("MissingPermission") String szImei = TelephonyMgr.getDeviceId();
                //调用广告浏览接口
                Map<String, String> map1 = new HashMap<>();
                map1.put("type", "0");
                map1.put("uuid", szImei);
                map1.put("ad_id", id + "");
                Utils.scanOrClickWebAD(getActivity(), map1);

                Map<String, String> map2 = new HashMap<>();
                map2.put("type", "1");
                map2.put("uuid", szImei);
                map2.put("ad_id", id + "");
                Utils.scanOrClickWebAD(getActivity(), map2);
            }catch (Exception e){

            }
        }
    }
}
