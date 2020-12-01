package com.newsuper.t.consumer.function.top.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.CollectionBean;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.function.person.adapter.CollectionAdapter;
import com.newsuper.t.consumer.function.person.internal.ICollectionView;
import com.newsuper.t.consumer.function.person.presenter.CollectionPresenter;
import com.newsuper.t.consumer.function.person.request.CollectionRequest;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingAnimatorView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/6/2 0002.
 */

public class ShopCollectFragment extends Fragment implements ICollectionView, View.OnClickListener {


    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.ll_none)
    LinearLayout llNone;
    Unbinder unbinder;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    private String lat = "";//后期是传值
    private String lng = "";
    private CollectionPresenter mPresenter;
    private CollectionAdapter mCollectionAdapter;
    private String token;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    loadView.dismissView();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_collection, null);
        unbinder = ButterKnife.bind(this, view);
        btnLogin.setOnClickListener(this);
        llLogin.setOnClickListener(this);
        mPresenter = new CollectionPresenter(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("我的收藏");
        mToolbar.setMenuText("");
        mToolbar.setBackImageViewVisibility(View.INVISIBLE);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // 设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        mCollectionAdapter = new CollectionAdapter(getContext());
        mRecyclerView.setAdapter(mCollectionAdapter);
        mCollectionAdapter.notifyDataSetChanged();
        if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
            llLogin.setVisibility(View.VISIBLE);
        }else {
            loadView.showView("");
            load();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void load() {
        token = SharedPreferencesUtil.getToken();
        lat = SharedPreferencesUtil.getLatitude();
        lng = SharedPreferencesUtil.getLongitude();
        String admin_id = SharedPreferencesUtil.getAdminId();
        HashMap<String, String> map = CollectionRequest.collectionRequest(token, admin_id, lat, lng);
        mPresenter.loadData(UrlConst.GET_COLLECTION_LIST, map);
    }

    @Override
    public void dialogDismiss() {
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showDataToVIew(CollectionBean bean) {
        handler.sendEmptyMessageDelayed(1, 1000);
        if (bean != null) {
            if (bean.data.shoplist != null && bean.data.shoplist.size() > 0) {
                mCollectionAdapter.setShopCollectionList(bean.data.shoplist);
            } else {
                loadFail();
            }
            mCollectionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadFail() {
        handler.sendEmptyMessageDelayed(1, 1000);
        llNone.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.GO_TO_LOGIN && resultCode == getActivity().RESULT_OK){
            llLogin.setVisibility(View.GONE);
            loadView.showView();
            load();
        }
    }
}
