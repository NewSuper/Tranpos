package com.newsuper.t.juejinbao.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.entity.BigGiftEntity;
import com.newsuper.t.juejinbao.ui.home.interf.CustomClickListener;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NewGiftBagSuccessDialog extends Dialog {

    private boolean alDismiss;
    private Context context;
    private Object object;
    private CustomClickListener clickListener;

    public void setClickListener(CustomClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public NewGiftBagSuccessDialog(@NonNull Context context, Object object) {
        super(context);
        this.context = context;
        this.object = object;
        setContentView(R.layout.dialog_receive_gift_success);
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
        if(object!=null){
            BigGiftEntity giftCarEntity = (BigGiftEntity) object;
            TextView tvNum = findViewById(R.id.tv_jueJinBao);
            tvNum.setText(String.format("%s个掘金宝", Utils.FormatGold(giftCarEntity.getData().getVcoin())));
            TextView tvMoneyNum = findViewById(R.id.tv_money);
            TextView tvPeopleNum = findViewById(R.id.tv_num);
            tvMoneyNum.setText(String.format("(￥%s元现金)", BigDecimal.valueOf(giftCarEntity.getData().getVcoin_to_rmb()).setScale(2, RoundingMode.HALF_EVEN).toString()));
            tvPeopleNum.setText(String.format("%s", giftCarEntity.getData().getUser_count()));
        }
        findViewById(R.id.btn_seeGift).setOnClickListener(view -> {
            //跳转到钱包
            BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_WALLET);
            clickListener.onItemClick(0,null);
        });
        findViewById(R.id.iv_close).setOnClickListener(view -> {
            clickListener.onItemClick(0,null);
            dismiss();
        });
        findViewById(R.id.btn_receive_more).setOnClickListener(view -> {
            clickListener.onItemClick(1,null);
            dismiss();
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
}
