package com.newsuper.t.consumer.function.person.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.BaseCouponData;
import com.newsuper.t.consumer.bean.CouponBean;
import com.newsuper.t.consumer.bean.PaotuiCouponBean;
import com.newsuper.t.consumer.function.person.adapter.CouponAdapter;
import com.newsuper.t.consumer.function.person.adapter.CouponListAdapter;
import com.newsuper.t.consumer.function.person.adapter.SpaceItemDecoration;
import com.newsuper.t.consumer.function.person.internal.ICouponView;
import com.newsuper.t.consumer.function.person.presenter.CouponPresenter;
import com.newsuper.t.consumer.function.person.request.CouponRequest;
import com.newsuper.t.consumer.utils.RecycleViewDivider;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingAnimatorView;
import com.newsuper.t.consumer.widget.RefreshThirdStepView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvalidCouponAty extends BaseActivity implements ICouponView {

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.ll_none)
    LinearLayout mLlNone;
    @BindView(R.id.coupon_listview)
    ListView couponListview;
    CouponListAdapter listAdapter;
    private ArrayList<BaseCouponData> mCouponLists = new ArrayList<>();
    private CouponPresenter mPresenter;
    private String admin_id;
    private String token;

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_invalid_coupon_aty);
        ButterKnife.bind(this);
        mPresenter = new CouponPresenter(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("无效优惠券");
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

        listAdapter = new CouponListAdapter(this,false,mCouponLists);
        couponListview.setAdapter(listAdapter);
        loadView.showView();
        load();
    }

    //获取无效劵
    private void load() {
        admin_id = SharedPreferencesUtil.getAdminId();
        token = SharedPreferencesUtil.getToken();
        HashMap<String, String> map = CouponRequest.couponRequest(token,admin_id, "0");
        mPresenter.loadData(UrlConst.GET_COUPON_LIST, map);
    }



    @Override
    public void dialogDismiss() {
        loadView.dismissView();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDataToVIew(CouponBean bean) {
        loadView.dismissView();
        if (bean != null) {
            if (bean.data.list != null && bean.data.list.size() > 0) {
                mLlNone.setVisibility(View.GONE);
                mCouponLists.addAll(bean.data.list);
            }
        }
        mPresenter.loadCounpon("0");

    }

    @Override
    public void loadFail() {
        mPresenter.loadCounpon("0");
    }

    @Override
    public void loadCoupon(PaotuiCouponBean bean) {
        loadView.dismissView();
        if (bean != null) {
            if (bean.data.list != null && bean.data.list.size() > 0) {
                mCouponLists.addAll(bean.data.list);
            }
        }
        listAdapter.notifyDataSetChanged();

    }

    @Override
    public void loadCouponFail() {
        loadView.dismissView();
        if (mCouponLists.size() > 0){
            listAdapter.notifyDataSetChanged();
        }else {
            showFailView();
        }

    }
    private void showFailView(){
        mLlNone.setVisibility(View.VISIBLE);
        loadView.dismissView();
        couponListview.setVisibility(View.GONE);
    }

}
