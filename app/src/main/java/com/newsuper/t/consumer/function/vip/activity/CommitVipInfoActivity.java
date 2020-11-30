package com.newsuper.t.consumer.function.vip.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.VipCommitInfoBean;
import com.xunjoy.lewaimai.consumer.function.vip.inter.IVipCommitView;
import com.xunjoy.lewaimai.consumer.function.vip.presenter.VipInfoCommitPresenter;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.LoadingDialog2;
import com.xunjoy.lewaimai.consumer.widget.TimePicker.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CommitVipInfoActivity extends BaseActivity implements IVipCommitView {
    @BindView(R.id.iv_male)
    ImageView ivMale;
    @BindView(R.id.ll_male)
    LinearLayout llMale;
    @BindView(R.id.iv_female)
    ImageView ivFemale;
    @BindView(R.id.ll_female)
    LinearLayout llFemale;
    @BindView(R.id.et_birthday)
    TextView etBirthday;
    @BindView(R.id.ll_select_birthday)
    LinearLayout llSelectBirthday;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_phone)
    TextView etPhone;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    private VipInfoCommitPresenter presenter;
    private int sex =0;
    private LoadingDialog2 loadingDialog;
    private TimePickerView pvTime;

    @Override
    public void initData() {
        presenter = new VipInfoCommitPresenter(this);

        //时间选择器
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1970, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(selectedDate.get(Calendar.YEAR), 1, 1);
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                etBirthday.setText(getTime(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setSubmitColor(Color.parseColor("#FB797B"))
                .setCancelColor(Color.parseColor("#FB797B"))
                .setRangDate(startDate, selectedDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_commit_vip_info);
        ButterKnife.bind(this);

        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("会员信息");
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
        etPhone.setText(SharedPreferencesUtil.getLoginPhone());
    }

    @Override
    public void commitVipInfo(VipCommitInfoBean vipCommitInfoBean) {
        startActivity(new Intent(this,InfoOpenSuccessActivity.class));
        sendBroadcast(new Intent("update_custome_info"));//更新顾客信息
        NoOpenVipMainActivity.instance.finish();
        OpenVipConditionActivity.instance.finish();
        this.finish();
    }

    @OnClick({R.id.ll_male, R.id.ll_female, R.id.ll_select_birthday, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_male:
                sex = 0;
                changeSelectView();
                break;
            case R.id.ll_female:
                sex = 1;
                changeSelectView();
                break;
            case R.id.ll_select_birthday:
                //选择生日
                pvTime.show(view);
                break;
            case R.id.tv_commit:
                String name = etName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    UIUtils.showToast("姓名不能为空");
                    return;
                }
                String birthday = etBirthday.getText().toString().trim();
                if (TextUtils.isEmpty(birthday)) {
                    UIUtils.showToast("生日不能为空");
                    return;
                }
                String address = etAddress.getText().toString().trim();
                if (TextUtils.isEmpty(address)) {
                    UIUtils.showToast("地址不能为空");
                    return;
                }
//                String phone = etPhone.getText().toString().trim();
//                if (TextUtils.isEmpty(phone)) {
//                    UIUtils.showToast("电话不能为空");
//                    return;
//                }

                showLoadingDialog();
                presenter.commitVipInfo(SharedPreferencesUtil.getToken(), Const.ADMIN_ID, name, sex + "", birthday,address);
                break;
        }
    }

    private void changeSelectView() {
        switch (sex) {
            case 0:
                ivMale.setImageResource(R.mipmap.vip_icon_select);
                ivFemale.setImageResource(R.mipmap.vip_icon_no_select);
                break;
            case 1:
                ivMale.setImageResource(R.mipmap.vip_icon_no_select);
                ivFemale.setImageResource(R.mipmap.vip_icon_select);
                break;
        }
    }

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog2(this, R.style.transparentDialog);
        }
        loadingDialog.show();
    }

    @Override
    public void dialogDismiss() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public void loadFail() {

    }

}
