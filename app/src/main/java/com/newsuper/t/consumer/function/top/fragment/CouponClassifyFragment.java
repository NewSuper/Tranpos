package com.newsuper.t.consumer.function.top.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseFragment;
import com.xunjoy.lewaimai.consumer.bean.BaseCouponData;
import com.xunjoy.lewaimai.consumer.bean.CouponBean;
import com.xunjoy.lewaimai.consumer.bean.PaotuiCouponBean;
import com.xunjoy.lewaimai.consumer.function.login.LoginActivity;
import com.xunjoy.lewaimai.consumer.function.person.activity.InvalidCouponAty;
import com.xunjoy.lewaimai.consumer.function.person.adapter.CouponListAdapter;
import com.xunjoy.lewaimai.consumer.function.person.internal.ICouponView;
import com.xunjoy.lewaimai.consumer.function.person.presenter.CouponPresenter;
import com.xunjoy.lewaimai.consumer.function.person.request.CouponRequest;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.LoadingAnimatorView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class CouponClassifyFragment extends BaseFragment implements ICouponView, View.OnClickListener {
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
    Unbinder unbinder;
    CouponListAdapter listAdapter;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    private CouponPresenter mPresenter;
    private ArrayList<BaseCouponData> mCouponLists = new ArrayList<>();
    private View mFooter;
    private String admin_id;
    private String token;
    private int count;

    @Override
    public void load() {
        admin_id = SharedPreferencesUtil.getAdminId();
        token = SharedPreferencesUtil.getToken();
        HashMap<String, String> map = CouponRequest.couponRequest(token, admin_id, "1");
        mPresenter.loadData(UrlConst.GET_COUPON_LIST, map);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon_classify, null);
        unbinder = ButterKnife.bind(this, view);
        mToolbar.setBackImageViewVisibility(View.GONE);
        mToolbar.setTitleText("优惠券");
        mToolbar.setMenuText("");
        mPresenter = new CouponPresenter(this);
        listAdapter = new CouponListAdapter(getContext(), true, mCouponLists);
        couponListview.setAdapter(listAdapter);
        mFooter = LayoutInflater.from(getContext()).inflate(R.layout.layout_my_coupon_footer, null);
        mFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), InvalidCouponAty.class));
            }
        });
        btnLogin.setOnClickListener(this);
        llLogin.setOnClickListener(this);
        if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
            llLogin.setVisibility(View.VISIBLE);
        }else {
            loadView.showView();
            load();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tips:
                startActivity(new Intent(getContext(), InvalidCouponAty.class));
                break;
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


    @Override
    public void dialogDismiss() {
        loadView.dismissView();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
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
        }
        if (mCouponLists.size() < count){
            tvTips.setText("抱歉，您还没有优惠券哦!");
            tvTips.setOnClickListener(null);
        }else {
            String s = "没有更多有效优惠券啦｜查看无效券>>";
            SpannableString ss = StringUtils.matcherSearchWord(ContextCompat.getColor(getContext(),R.color.theme_red),s,"查看无效券");
            tvTips.setText(ss);
            tvTips.setOnClickListener(this);
        }
        listAdapter.notifyDataSetChanged();
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
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
