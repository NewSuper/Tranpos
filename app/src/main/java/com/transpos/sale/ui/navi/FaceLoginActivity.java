package com.transpos.sale.ui.navi;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.transpos.market.R;
import com.transpos.sale.base.BaseActivity;
import com.transpos.sale.ui.food.FoodActivity;
import com.transpos.sale.utils.CountTimer;
import com.transpos.sale.utils.UiUtils;
import com.transpos.sale.view.CustomGradient;
import com.transpos.sale.view.GlideCircleTransform;

import butterknife.BindView;
import butterknife.OnClick;

public class FaceLoginActivity extends BaseActivity {
    private CountTimer mTimeCount;
    @BindView(R.id.tv_deal)
    TextView tv_deal;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.iv_circle)
    ImageView iv_circle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navi_face_login;
    }


    @Override
    protected void initView() {
        super.initView();
        tv_deal.setVisibility(View.VISIBLE);
        ll_back.setBackground(new CustomGradient(GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{Color.parseColor("#FFB200"),Color.parseColor("#FF7B00")},
                UiUtils.dp2px(4,this)));
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .priority(Priority.HIGH)
                .transform(new GlideCircleTransform());
        Glide.with(this).load(R.mipmap.ic_vip_scan_login).apply(options).into(iv_circle);
    }

    @Override
    protected void initData() {
        super.initData();
        String reslut = getString(R.string.txt_actionbar_content);
        tv_deal.setText(String.format(reslut,"300"));
        mTimeCount = new CountTimer(300*1000L,1000L,this,tv_deal,false);
        mTimeCount.start();
    }

    @OnClick({R.id.ll_back,R.id.tv_deal})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.ll_back:
            case R.id.tv_deal:
                if(mTimeCount != null){
                    mTimeCount.cancel();
                    mTimeCount = null;
                }
                startActivity(FoodActivity.class);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mTimeCount != null){
            mTimeCount.cancel();
            mTimeCount = null;
        }
    }
}
