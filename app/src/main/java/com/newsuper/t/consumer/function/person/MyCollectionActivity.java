package com.newsuper.t.consumer.function.person;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.CollectionBean;
import com.newsuper.t.consumer.function.person.adapter.CollectionAdapter;
import com.newsuper.t.consumer.function.person.internal.ICollectionView;
import com.newsuper.t.consumer.function.person.presenter.CollectionPresenter;
import com.newsuper.t.consumer.function.person.request.CollectionRequest;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingAnimatorView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCollectionActivity extends BaseActivity implements ICollectionView {

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.ll_none)
    LinearLayout mLlNone;
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

    @Override
    public void initData() {

    }

    public void initView() {
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        mPresenter = new CollectionPresenter(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("我的收藏");
        mToolbar.setMenuText("");
        mToolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        mCollectionAdapter = new CollectionAdapter(this);
        mRecyclerView.setAdapter(mCollectionAdapter);
        mCollectionAdapter.notifyDataSetChanged();
        loadView.showView("");
        load();
    }

    private String lat = "";//后期是传值
    private String lng = "";

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
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
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
        mLlNone.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

}
