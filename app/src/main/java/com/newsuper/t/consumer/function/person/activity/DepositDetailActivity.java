package com.newsuper.t.consumer.function.person.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.DepositDetailBean;
import com.newsuper.t.consumer.function.person.internal.IDepositDetailView;
import com.newsuper.t.consumer.function.person.presenter.DepositDetailPresenter;
import com.newsuper.t.consumer.function.person.presenter.DepositPresenter;
import com.newsuper.t.consumer.function.person.request.DepositDetailRequest;
import com.newsuper.t.consumer.function.person.request.DepositRequest;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public class DepositDetailActivity extends BaseActivity implements IDepositDetailView {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tv_activity_deposit_detail_type)
    TextView tvType;
    @BindView(R.id.tv_activity_deposit_detail_amount)
    TextView tvAmount;
    @BindView(R.id.tv_activity_deposit_detail_current_amount)
    TextView tvCurrentAmount;
    @BindView(R.id.tv_activity_deposit_detail_type2)
    TextView tvType2;
    @BindView(R.id.tv_activity_deposit_detail_area)
    TextView tvArea;
    @BindView(R.id.tv_activity_deposit_detail_time)
    TextView tvTime;
    @BindView(R.id.tv_activity_deposit_detail_dec)
    TextView tvDec;
    String token;
    String adminId;
    String id;
    DepositDetailPresenter mPresenter;

    @Override
    public void initData() {
        token = SharedPreferencesUtil.getToken();
        adminId = SharedPreferencesUtil.getAdminId();
        id = getIntent().getStringExtra("id");
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_deposit_detail);
        ButterKnife.bind(this);
        mPresenter = new DepositDetailPresenter(this);
        toolbar.setBackImageViewVisibility(View.VISIBLE);
        toolbar.setTitleText("押金明细详情");
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
        load();
    }

    private void load() {
        HashMap<String, String> map = DepositDetailRequest.depositDetailRequest(token, adminId, id);
        mPresenter.loadData(UrlConst.GET_DEPOSIT_DETAIL, map);
    }

    @Override
    public void showDataToView(DepositDetailBean bean) {
        if (null!=bean&&null!=bean.data) {
            tvType.setText("1".equals(bean.data.change_type)?"支出金额":"退还金额");
            tvAmount.setText(bean.data.money);
            tvCurrentAmount.setText(bean.data.deposit);
            tvType2.setText("1".equals(bean.data.change_type)?"支出":"退还");
            tvArea.setText(bean.data.area_name);
            tvTime.setText(bean.data.change_date);
            tvDec.setText(bean.data.remark);
        }
    }

    @Override
    public void loadFail() {

    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
