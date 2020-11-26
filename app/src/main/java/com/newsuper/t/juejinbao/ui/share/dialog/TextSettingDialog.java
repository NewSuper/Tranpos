package com.newsuper.t.juejinbao.ui.share.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.TextSettingEvent;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import static io.paperdb.Paper.book;


/**
 * 文字大小设置 弹窗
 */
public class TextSettingDialog extends Dialog {

    Activity mActivity;


    public TextSettingDialog(Context context) {
        super(context, 0);
        mActivity = (Activity) context;
        setContentView(R.layout.dialog_text_setting);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = Utils.getScreenWidth(context) - Utils.dip2px(context, 20);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;

        getWindow().setAttributes(params);
        initView();
    }

    private void initView() {
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        RadioGroup radioGroup = findViewById(R.id.rg_text_setting);
        String textSize = book().read(PagerCons.KEY_TEXTSET_SIZE, "middle");
        switch (textSize) {
            case "small":
                radioGroup.check(R.id.rb_small);
                break;
            case "middle":
                radioGroup.check(R.id.rb_middle);
                break;
            case "big":
                radioGroup.check(R.id.rb_big);
                break;
            case "large":
                radioGroup.check(R.id.rb_large);
                break;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_small:
                        EventBus.getDefault().post(new TextSettingEvent("small"));
                        book().write(PagerCons.KEY_TEXTSET_SIZE, "small");
                        break;
                    case R.id.rb_middle:
                        EventBus.getDefault().post(new TextSettingEvent("middle"));
                        book().write(PagerCons.KEY_TEXTSET_SIZE, "middle");
                        break;
                    case R.id.rb_big:
                        EventBus.getDefault().post(new TextSettingEvent("big"));
                        book().write(PagerCons.KEY_TEXTSET_SIZE, "big");
                        break;
                    case R.id.rb_large:
                        EventBus.getDefault().post(new TextSettingEvent("large"));
                        book().write(PagerCons.KEY_TEXTSET_SIZE, "large");
                        break;
                }
            }
        });
    }

}
