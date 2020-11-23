package com.newsuper.t.juejinbao.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.HideShowGiftCarButtonEvent;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.home.entity.BigGiftEntity;
import com.newsuper.t.juejinbao.ui.home.entity.WelfareEntity;
import com.newsuper.t.juejinbao.ui.home.interf.CustomClickListener;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;


import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;
import rx.Subscriber;

/**
 * 领鸡蛋入口弹窗
 */
public class NewGiftBagForEggsDialog extends Dialog {

    private boolean alDismiss;
    private Context context;
    private Object object;
    private CustomClickListener clickListener;

    public void setClickListener(CustomClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public NewGiftBagForEggsDialog(Context context,Object object) {
        super(context);
        this.context = context;
        this.object = object;
        setContentView(R.layout.dialog_gift_bag_for_eggs);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.dimAmount =0f;
        getWindow().setAttributes(params);
        initView();
    }

    private void initView() {
        TextView tvReward = findViewById(R.id.tv_reward);
        TextView tvNum = findViewById(R.id.tv_num);
        ImageView ivGif = findViewById(R.id.iv_gif);
        ImageView ivClose = findViewById(R.id.iv_close);
        Button btReceive = findViewById(R.id.bt_receive);

        WelfareEntity.DataBean data = (WelfareEntity.DataBean) object;
        tvReward.setText(String.format("恭喜你获得%s枚鸡蛋", data.getReward_num()));
        tvNum.setText(String.format("%s", data.getGot_total_num()));
        Glide.with(context).load(data.getGif_url()).into(ivGif);

        ivClose.setOnClickListener(v -> {
            clickListener.onItemClick(0,null);
            dismiss();
        });

        btReceive.setOnClickListener(view -> {
           loadGiftApi();
        });
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void show() {
        super.show();
        alDismiss = false;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        alDismiss = true;
    }

    /**
     * 加载领取大礼包接口
     */
    private void loadGiftApi() {
        Map<String, String> map = new HashMap<>();
        rx.Observable<BigGiftEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                getBigGif(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<>()));
        RetrofitManager.getInstance(context).toSubscribe(observable, new Subscriber<BigGiftEntity>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                clickListener.onItemClick(0,null);
                dismiss();
            }

            @Override
            public void onNext(BigGiftEntity giftCarEntity) {
                if (giftCarEntity.getCode() == 0) {
                    LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                    loginEntity.getData().setAchieved_gift_bag(1);
                    Paper.book().write(PagerCons.USER_DATA, loginEntity);
                    EventBus.getDefault().post(new HideShowGiftCarButtonEvent(false));
                    Paper.book().write(PagerCons.ISSHOWBIGGIFT, false);

                    clickListener.onItemClick(1,giftCarEntity);
                    dismiss();
                } else {
                    ToastUtils.getInstance().show(getContext(), giftCarEntity.getMsg());

                    clickListener.onItemClick(1,null);
                    dismiss();
                }
            }
        });
    }
}
