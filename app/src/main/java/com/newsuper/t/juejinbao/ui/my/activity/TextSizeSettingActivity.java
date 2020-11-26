package com.newsuper.t.juejinbao.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.RadioGroup;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityTextSizeSettingBinding;
import com.newsuper.t.juejinbao.base.ActivityCollector;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.TextSettingEvent;
import com.newsuper.t.juejinbao.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;
import io.paperdb.Paper;

import static io.paperdb.Paper.book;

public class TextSizeSettingActivity extends BaseActivity<BasePresenter, ActivityTextSizeSettingBinding> {


    private int clickCount = 0;
    private long clickTime = 0;

    public static void start(Context mActivity) {
        mActivity.startActivity(new Intent(mActivity,TextSizeSettingActivity.class));
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_text_size_setting;
    }

    @Override
    public void initView() {
        mViewBinding.activityModelSettingBar.moduleIncludeTitleBarTitle.setText("字体演示");
        mViewBinding.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickTime == 0) {
                    clickTime = System.currentTimeMillis();
                }
                if (System.currentTimeMillis() - clickTime > 500) {
                    clickCount = 0;
                } else {
                    clickCount++;
                }
                clickTime = System.currentTimeMillis();
                if (clickCount > 7) {
                    ToastUtils.getInstance().show(TextSizeSettingActivity.this,"app防护已关闭，杀进程，重启app");
                    Paper.book().write(PagerCons.KEY_CLOSE_APP_PROTECT,1);

                    ActivityCollector.removeAllActivity();
                }

            }
        });
        initTextSize();
    }

    private void initTextSize() {
        String textSize = book().read(PagerCons.KEY_TEXTSET_SIZE, "middle");
        setTextSize(textSize);
        switch (textSize) {
            case "small":
                mViewBinding.rgTextSetting.check(R.id.rb_small);
                break;
            case "middle":
                mViewBinding.rgTextSetting.check(R.id.rb_middle);
                break;
            case "big":
                mViewBinding.rgTextSetting.check(R.id.rb_big);
                break;
            case "large":
                mViewBinding.rgTextSetting.check(R.id.rb_large);
                break;
        }
        mViewBinding.rgTextSetting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_small:
                        setTextSize("small");
                        EventBus.getDefault().post(new TextSettingEvent("small"));
                        book().write(PagerCons.KEY_TEXTSET_SIZE, "small");
                        break;
                    case R.id.rb_middle:
                        setTextSize("middle");
                        EventBus.getDefault().post(new TextSettingEvent("middle"));
                        book().write(PagerCons.KEY_TEXTSET_SIZE, "middle");
                        break;
                    case R.id.rb_big:
                        setTextSize("big");
                        EventBus.getDefault().post(new TextSettingEvent("big"));
                        book().write(PagerCons.KEY_TEXTSET_SIZE, "big");
                        break;
                    case R.id.rb_large:
                        setTextSize("large");
                        EventBus.getDefault().post(new TextSettingEvent("large"));
                        book().write(PagerCons.KEY_TEXTSET_SIZE, "large");
                        break;
                }
            }
        });
    }

    private void setTextSize(String level) {
        switch (level) {
            case "small":
                mViewBinding.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.content_small));
                break;
            case "middle":
                mViewBinding.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.content_middle));
                break;
            case "big":
                mViewBinding.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.content_big));
                break;
            case "large":
                mViewBinding.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.content_lager));
                break;
        }
    }

    @Override
    public void initData() {


    }


    @OnClick({R.id.module_include_title_bar_return,R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.module_include_title_bar_return:
            case R.id.cancel:
                finish();
                break;
        }
    }
}
