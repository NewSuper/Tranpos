package com.newsuper.t.consumer.function.vip.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.VipCommitInfoBean;
import com.newsuper.t.consumer.bean.VipInfoBean;
import com.newsuper.t.consumer.bean.VipTopInfoBean;
import com.newsuper.t.consumer.function.vip.inter.IVipCommitView;
import com.newsuper.t.consumer.function.vip.inter.IVipInfoView;
import com.newsuper.t.consumer.function.vip.presenter.VipInfoCommitPresenter;
import com.newsuper.t.consumer.function.vip.presenter.VipInfoPresenter;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.widget.RefreshThirdStepView;
import com.newsuper.t.consumer.widget.TimePicker.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EditVipInfoActivity extends BaseActivity implements IVipCommitView, IVipInfoView {
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
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.ll_fail)
    LinearLayout llFail;
    @BindView(R.id.loading_view)
    RefreshThirdStepView loadingView;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
//    @BindView(R.id.tv_change_phone)
//    TextView tvChangePhone;
    private VipInfoCommitPresenter presenter;
    private int sex = 0;
    private final static int MODIFY_PHONE=11;
    private LoadingDialog2 loadingDialog;
    private TimePickerView pvTime;
    private VipInfoPresenter infoPresenter;
    private String from;//上一个页面

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    closeLoadingView();
                    break;
            }
        }
    };


    //加载动画
    private AnimationDrawable animationDrawable;
    Runnable anim = new Runnable() {
        @Override
        public void run() {
            animationDrawable.start();
        }
    };

    @Override
    public void initData() {
        from=getIntent().getStringExtra("flag_from");
        infoPresenter = new VipInfoPresenter(this);
        presenter = new VipInfoCommitPresenter(this);
        //时间选择器
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1970, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2017, 1, 1);
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
        setContentView(R.layout.activity_edit_vip_info);
        ButterKnife.bind(this);

        animationDrawable = (AnimationDrawable) loadingView.getBackground();
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
        String loginPhone=SharedPreferencesUtil.getLoginPhone();
        if(!TextUtils.isEmpty(loginPhone)){
            etPhone.setText(loginPhone);
        }
        if("vip".equals(from)){
            //获取会员信息
            getVipInfo();
        }

    }


    private void getVipInfo() {
        showLoadingView();
        infoPresenter.showVipInfo(SharedPreferencesUtil.getToken(), Const.ADMIN_ID);
    }

    @Override
    public void loadVipInfo(VipInfoBean vipInfo) {
        if (null != vipInfo) {
            if (!TextUtils.isEmpty(vipInfo.data.name)) {
                etName.setText(vipInfo.data.name);
            }
            if (!TextUtils.isEmpty(vipInfo.data.sex)) {
                if ("0".equals(vipInfo.data.sex)) {
                    sex = 0;
                } else {
                    sex = 1;
                }
                changeSelectView();
            }
            if (!TextUtils.isEmpty(vipInfo.data.birthday)) {
                etBirthday.setText(vipInfo.data.birthday);
            }
            if (!TextUtils.isEmpty(vipInfo.data.address)) {
                etAddress.setText(vipInfo.data.address);
            }
            if (!TextUtils.isEmpty(vipInfo.data.tel)) {
                etPhone.setText(vipInfo.data.tel);
            }
        }
        //关闭动画
        closeLoadingView();
    }

    @Override
    public void commitVipInfo(VipCommitInfoBean vipCommitInfoBean) {
         UIUtils.showToast("保存成功");
         if("no_vip".equals(from)){
             ChargeOpenSuccessActivity.instance.finish();
         }
         this.finish();
    }

    //正在加载
    private void showLoadingView() {
        if (null != llFail) {
            llFail.setVisibility(View.GONE);
        }
        llLoading.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        handler.post(anim);
    }

    //关闭加载动画
    private void closeLoadingView() {
        if (null != animationDrawable) {
            animationDrawable.stop();
        }
        if (null != llLoading) {
            llLoading.setVisibility(View.GONE);
        }
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
//            case R.id.tv_change_phone:
//                //换绑手机号
//                Intent phoneIntent=new Intent(this,ChangePhoneActivity.class);
//                startActivityForResult(phoneIntent,MODIFY_PHONE);
//                break;
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
                String phone = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    UIUtils.showToast("电话不能为空");
                    return;
                }

                showLoadingDialog();
                presenter.modifyVipInfo(SharedPreferencesUtil.getToken(), Const.ADMIN_ID, name, sex + "", birthday, address);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MODIFY_PHONE){
            if(resultCode==RESULT_OK){
                String phone=data.getStringExtra("phone");
                etPhone.setText(phone);
            }
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
    public void loadVipTopInfo(VipTopInfoBean vipInfo) {

    }


    @Override
    public void loadFail() {

    }
    public boolean isShouldHideKeyBoard(View v, MotionEvent event) {
        // 如果获取焦点的View存在并且是EditText
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            // 获取输入框在屏幕中的位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();

            // 判断点击区域是否在EditText内部,是则保留EditText的事件
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;// 在EditText内部，不消耗该事件
            } else {
                return true;// 隐藏键盘
            }
        }
        return false;// 如果获取焦点的View不是EditText，此处用就是原流程该怎么分发事件就怎么做
    }

    /**
     * 事件分发，是否显示键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // return super.dispatchTouchEvent(ev);
        // 如果是点击按下动作
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyBoard(v, ev)) {
                // 隐藏键盘
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (im != null) {
                    im.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

}
