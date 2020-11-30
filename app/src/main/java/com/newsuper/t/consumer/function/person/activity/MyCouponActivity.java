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
import android.widget.TextView;
import android.widget.Toast;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.BaseCouponData;
import com.xunjoy.lewaimai.consumer.bean.CouponBean;
import com.xunjoy.lewaimai.consumer.bean.PaotuiCouponBean;
import com.xunjoy.lewaimai.consumer.function.person.adapter.CouponAdapter;
import com.xunjoy.lewaimai.consumer.function.person.adapter.CouponListAdapter;
import com.xunjoy.lewaimai.consumer.function.person.adapter.SpaceItemDecoration;
import com.xunjoy.lewaimai.consumer.function.person.internal.ICouponView;
import com.xunjoy.lewaimai.consumer.function.person.presenter.CouponPresenter;
import com.xunjoy.lewaimai.consumer.function.person.request.CouponRequest;
import com.xunjoy.lewaimai.consumer.utils.RecycleViewDivider;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.LoadingAnimatorView;
import com.xunjoy.lewaimai.consumer.widget.RefreshThirdStepView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCouponActivity extends BaseActivity implements ICouponView ,View.OnClickListener{

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.ll_none)
    LinearLayout mLlNone;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.coupon_listview)
    ListView couponListview;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    private CouponListAdapter listAdapter;
    private CouponPresenter mPresenter;
    private ArrayList<BaseCouponData> mCouponLists = new ArrayList<>();
    private View mFooter;
    private String admin_id;
    private String token;
    private int count;




    @Override
    public void initData() {

    }

    public void initView() {
        setContentView(R.layout.activity_my_coupon);
        ButterKnife.bind(this);
        mPresenter = new CouponPresenter(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("我的优惠券");
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
        listAdapter = new CouponListAdapter(this,true,mCouponLists);
        couponListview.setAdapter(listAdapter);
        mFooter = LayoutInflater.from(this).inflate(R.layout.layout_my_coupon_footer,null);
        mFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCouponActivity.this,InvalidCouponAty.class));
            }
        });
        loadView.showView();
        load();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_tips:
                startActivity(new Intent(MyCouponActivity.this,InvalidCouponAty.class));
                break;
        }
    }

    private void load() {
        admin_id = SharedPreferencesUtil.getAdminId();
        token = SharedPreferencesUtil.getToken();
        HashMap<String, String> map = CouponRequest.couponRequest(token,admin_id, "1");
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
        if (bean != null) {
            if (bean.data.list != null && bean.data.list.size() > 0) {
                mCouponLists.addAll(bean.data.list);
                count += bean.data.num;
            }
        }
        mPresenter.loadCounpon("1");
    }

    @Override
    public void loadCoupon(PaotuiCouponBean bean) {
        loadView.dismissView();
        if (bean != null) {
            if (bean.data.list != null && bean.data.list.size() > 0) {
                mCouponLists.addAll(bean.data.list);
                count += bean.data.num;
            }
        }
        showData();
    }

    @Override
    public void loadCouponFail() {
        loadView.dismissView();
        if (mCouponLists.size() > 0){
            showData();
        }else {
            showFailView();
        }

    }
    @Override
    public void loadFail() {
        mPresenter.loadCounpon("1");
    }
    private void showFailView(){
        mLlNone.setVisibility(View.VISIBLE);
        loadView.dismissView();
        couponListview.setVisibility(View.GONE);
    }
    private void showData(){
        if (mCouponLists.size() > 0){
            couponListview.removeFooterView(mFooter);
            couponListview.addFooterView(mFooter);
            listAdapter.notifyDataSetChanged();
        }else {
            if (mCouponLists.size() >= count){
                tvTips.setText("抱歉，您还没有优惠券哦!");
                tvTips.setOnClickListener(null);
            }else {
                String s = "没有更多有效优惠券啦｜查看无效券>>";
                SpannableString ss = StringUtils.matcherSearchWord(ContextCompat.getColor(this,R.color.theme_red),s,"查看无效券");
                tvTips.setText(ss);
                tvTips.setOnClickListener(this);
            }
            mLlNone.setVisibility(View.VISIBLE);
        }

    }
}
