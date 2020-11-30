package com.newsuper.t.consumer.function.vip.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.multiviewpager.MultiViewPager;
import com.rd.PageIndicatorView;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.VipLevelBean;
import com.xunjoy.lewaimai.consumer.function.vip.adapter.VipLevelPageFragment;
import com.xunjoy.lewaimai.consumer.function.vip.inter.IVipLevelView;
import com.xunjoy.lewaimai.consumer.function.vip.presenter.VipLevelPresenter;
import com.xunjoy.lewaimai.consumer.function.vip.request.VipLevelRequest;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create by Administrator on 2019/7/1 0001
 */
public class VipLevelActivity extends BaseActivity implements IVipLevelView {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tv_activity_vip_level_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_activity_vip_level_up_btn)
    TextView tvUpBtn;
    @BindView(R.id.wv_activity_vip_level_dec)
    WebView wvDec;
    @BindView(R.id.vp_activity_vip_level)
    MultiViewPager vpMulti;
    @BindView(R.id.cl_layout)
    ConstraintLayout clLayout;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;
    private String token;
    private String adminId;
    private int vipLevel = 0;
    private String upVipMoney;
    private String grade_id;
    private boolean isChange = false;
    private ArrayList<VipLevelBean.DataBean.GradelistBean> mList = new ArrayList<>();
    private VipLevelPresenter mPresenter;
    private FragmentStatePagerAdapter adapter;
    private SparseArray<VipLevelPageFragment> fragments = new SparseArray<>();
    private boolean isShowChange = false;

    @Override
    public void initData() {
        token = SharedPreferencesUtil.getToken();
        adminId = SharedPreferencesUtil.getAdminId();
        isShowChange = getIntent().getBooleanExtra("isShowCharge",true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (isChange)
                setResult(RESULT_OK);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_vip_level);
        ButterKnife.bind(this);
        mPresenter = new VipLevelPresenter(this);
        toolbar.setBackImageViewVisibility(View.VISIBLE);
        toolbar.setTitleText("会员等级");
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                if (isChange)
                    setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onMenuClick() {
            }
        });
        adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                VipLevelPageFragment fragment = VipLevelPageFragment.create(mList.get(position).nickname);
                fragments.put(position, fragment);
                return fragment;
            }

            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//                super.destroyItem(container, position, object);
            }
        };
        vpMulti.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragments.get(position).setIvSelect(true);
                for (int i = 0; i < fragments.size(); i++) {
                    if (i != position)
                        fragments.get(i).setIvSelect(false);
                }
                selectVipLevel(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpMulti.setAdapter(adapter);
        vpMulti.setOffscreenPageLimit(20);
        initCL();
        load();
    }

    private void initCL() {
        int height = UIUtils.getWindowHeight();
        int barHeight = UIUtils.getStatusBarHeight(this);
        int clMinHeight = height - UIUtils.dip2px(225) - barHeight;
        clLayout.setMinHeight(clMinHeight);
    }

    private void load() {
        HashMap<String, String> map = VipLevelRequest.vipLevelRequest(token, adminId);
        mPresenter.loadData(UrlConst.VIP_LEVEL_INFO, map);
    }

    @Override
    public void showDataToView(VipLevelBean bean) {
        if (null != bean && null != bean.data && null != bean.data.gradelist) {
            if (bean.data.gradelist.size() > 0)
                mList.clear();
            mList.addAll(bean.data.gradelist);
            for (int i = 0; i<mList.size();i++) {
                if(!TextUtils.isEmpty(bean.data.grade_id) && bean.data.grade_id.equals(mList.get(i).id)) {
                    vipLevel = i;
                    break;
                }
            }
            adapter.notifyDataSetChanged();
            vpMulti.setCurrentItem(vipLevel, true);//默认选中
            fragments.get(vipLevel).setIvSelect(true);
            selectVipLevel(vipLevel);
        }
    }

    private void selectVipLevel(int index) {
        StringBuilder strB = new StringBuilder();
        if (index < vipLevel) {
            strB.append("您已是").append(mList.get(vipLevel).nickname).append("，请继续保持哦！");
            tvNickname.setText(strB.toString());
            tvUpBtn.setVisibility(View.GONE);
        } else {
            if (index > vipLevel) {
                strB.append("开通更高级会员享受更多权益");
                tvUpBtn.setVisibility("1".equals(mList.get(index).is_upbyrecharge) && isShowChange ? View.VISIBLE : View.GONE);
            } else {
                strB.append("您当前是").append(mList.get(index).nickname);
                tvUpBtn.setVisibility(View.GONE);
            }
            tvNickname.setText(strB.toString());
        }
        wvDec.loadData(getHtmlData(mList.get(index).privilege), "text/html; charset=UTF-8", null);

        if ("2".equals(mList.get(index).status)) {
            tvUpBtn.setVisibility(View.GONE);
            tvNickname.setText("当前等级会员系统在维护中");
        }
        upVipMoney = mList.get(index).recharge_money;
        grade_id = mList.get(index).id;
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

    @OnClick(R.id.tv_activity_vip_level_up_btn)
    public void onViewClicked() {
        Intent upVipIntent = new Intent(this, VipChargeActivity.class);
        upVipIntent.putExtra("recharge_money", upVipMoney);
        upVipIntent.putExtra("grade_id", grade_id);
        startActivity(upVipIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isChange = true;
        load();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragments.clear();
        fragments = null;
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
