package com.newsuper.t.consumer.function.distribution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.PaotuiCouponBean;
import com.newsuper.t.consumer.function.distribution.adapter.PaotuiCouponAdapter;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.ListViewForScrollView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaotuiCouponActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.listview_can_use)
    ListViewForScrollView listviewCanUse;
    @BindView(R.id.listview_no_use)
    ListViewForScrollView listviewNoUse;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tv_not_select)
    TextView tvNotSelect;
    @BindView(R.id.ll_no_coupon)
    LinearLayout llNoCoupon;
    PaotuiCouponAdapter useAdapter;
    PaotuiCouponAdapter noAdapter;
    private ArrayList<PaotuiCouponBean.CouponList> nCouponLists;
    private ArrayList<PaotuiCouponBean.CouponList> uCouponLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paotui_coupon);
        ButterKnife.bind(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("优惠券");
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

        tvNotSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (useAdapter != null){
                    useAdapter.setSelectId("");
                    useAdapter.notifyDataSetChanged();
                    useAdapter.notifyDataSetChanged();
                }
                Intent intent = new Intent();
                intent.putExtra("is_select", false);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        nCouponLists = (ArrayList<PaotuiCouponBean.CouponList>) getIntent().getSerializableExtra("coupon_no");
        uCouponLists = (ArrayList<PaotuiCouponBean.CouponList>) getIntent().getSerializableExtra("coupon_use");
        boolean flag1 = false;
        if (nCouponLists != null && nCouponLists.size() > 0) {
            View hView = LayoutInflater.from(this).inflate(R.layout.layout_coupon_header_view,null);
            TextView tvName = (TextView)hView.findViewById(R.id.tv_header_name);
            tvName.setText("不可使用优惠券");
            listviewNoUse.addHeaderView(hView);
            noAdapter = new PaotuiCouponAdapter(this, nCouponLists,true);
            noAdapter.setSelectId(getIntent().getStringExtra(""));
            listviewNoUse.setAdapter(noAdapter);
            flag1 = true;
        }
        if (uCouponLists != null && uCouponLists.size() > 0) {
            View hView = LayoutInflater.from(this).inflate(R.layout.layout_coupon_header_view,null);
            listviewCanUse.addHeaderView(hView);
            useAdapter = new PaotuiCouponAdapter(this, uCouponLists,false);
            useAdapter.setSelectId(getIntent().getStringExtra("coupon_id"));
            listviewCanUse.setAdapter(useAdapter);
            listviewCanUse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PaotuiCouponBean.CouponList couponListBean = uCouponLists.get(position - 1);
                    useAdapter.setSelectId(couponListBean.id);
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

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }
}
