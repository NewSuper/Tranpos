package com.newsuper.t.juejinbao.view.rewardAnim;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardEntity;


public class RewardADDialog extends Dialog {


    public RelativeLayout rl;
    public LinearLayout ll_dialog;

    private ImageView img_close;
    private TextView tv_label;
    private TextView tv_reward_coin;



    public RewardADDialog(Context context , Object object, ViewClickListener viewClickListener) {
        super(context ,  R.style.alpha_dialog);
        setContentView(R.layout.dialog_reward_ad);


        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        getWindow().setAttributes(params);

        setCancelable(false);
        setCanceledOnTouchOutside(false);


        ll_dialog = findViewById(R.id.ll_dialog);
        img_close = findViewById(R.id.img_close);
        tv_label = findViewById(R.id.tv_label);
        rl = findViewById(R.id.rl);

        tv_reward_coin = findViewById(R.id.tv_reward_coin);

        ll_dialog.setOnClickListener(v -> viewClickListener.ad());

        img_close.setOnClickListener(v -> viewClickListener.close());

        //文章奖励
        if(object instanceof RewardEntity){
            tv_label.setText(((RewardEntity) object).getRewardType());
            tv_reward_coin.setText( "+" + ((RewardEntity) object).getCoin() + "金币");
        }
        //小视频奖励 & 短视频详情奖励
        else if(object instanceof GetCoinEntity){
            tv_label.setText("金币奖励");
            tv_reward_coin.setText( "+" + ((GetCoinEntity) object).getData().getCoin() + "金币");
        }

    }

    public static interface ViewClickListener{
        public void close();
        public void ad();
    }

    public void destory(){
        try{
            dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
