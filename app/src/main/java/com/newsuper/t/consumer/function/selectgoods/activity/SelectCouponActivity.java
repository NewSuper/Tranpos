package com.newsuper.t.consumer.function.selectgoods.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.ShoppingCartBean;
import com.newsuper.t.consumer.function.selectgoods.adapter.CouponCanNoAdapter;
import com.newsuper.t.consumer.function.selectgoods.adapter.CouponCanUseAdapter;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.ListViewForScrollView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 优惠券
 */
public class SelectCouponActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.listview_can_use)
    ListViewForScrollView listviewCanUse;
    @BindView(R.id.listview_no_use)
    ListViewForScrollView listviewNoUse;
    @BindView(R.id.ll_no_coupon)
    LinearLayout llNoCoupon;
    @BindView(R.id.tv_not_select)
    TextView tvNotSelect;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private CouponCanNoAdapter noAdapter;
    private CouponCanUseAdapter useAdapter;
    private ArrayList<ShoppingCartBean.DataBean.CouponListBean> nCouponLists;
    private ArrayList<ShoppingCartBean.DataBean.CouponListBean> uCouponLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {
        nCouponLists = (ArrayList<ShoppingCartBean.DataBean.CouponListBean>) getIntent().getSerializableExtra("coupon_no");
        uCouponLists = (ArrayList<ShoppingCartBean.DataBean.CouponListBean>) getIntent().getSerializableExtra("coupon_use");
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_select_coupon);
        ButterKnife.bind(this);
        toolbar.setTitleText("优惠券");
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
              finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        tvNotSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CouponCanUseAdapter.select_id = "-1";
                if (useAdapter != null){
                    useAdapter.notifyDataSetChanged();
                }
                Intent intent = new Intent();
                intent.putExtra("is_select", false);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        boolean flag1 = false;
        if (nCouponLists != null && nCouponLists.size() > 0) {
            View hView = LayoutInflater.from(this).inflate(R.layout.layout_coupon_header_view,null);
            TextView tvName = (TextView)hView.findViewById(R.id.tv_header_name);
            tvName.setText("不可使用优惠券");
            listviewNoUse.addHeaderView(hView);
            noAdapter = new CouponCanNoAdapter(this, nCouponLists);
            listviewNoUse.setAdapter(noAdapter);
            flag1 = true;
        }
        if (uCouponLists != null && uCouponLists.size() > 0) {
            View hView = LayoutInflater.from(this).inflate(R.layout.layout_coupon_header_view,null);
            listviewCanUse.addHeaderView(hView);
            useAdapter = new CouponCanUseAdapter(this, uCouponLists);
            CouponCanUseAdapter.select_id = getIntent().getStringExtra("coupon_id");
            listviewCanUse.setAdapter(useAdapter);
            listviewCanUse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ShoppingCartBean.DataBean.CouponListBean couponListBean = uCouponLists.get(position - 1);
                    CouponCanUseAdapter.select_id = couponListBean.id;
                    useAdapter.notifyDataSetChanged();
                    Intent intent = new Intent();
                    intent.putExtra("is_select", true);
                    intent.putExtra("coupon", couponListBean);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            flag1 = true;
            //滚动到顶部
            scrollView.fullScroll(ScrollView.FOCUS_UP);
        }

        if (!flag1) {
            llNoCoupon.setVisibility(View.VISIBLE);
        }else {
            llNoCoupon.setVisibility(View.GONE);
        }
    }
}
