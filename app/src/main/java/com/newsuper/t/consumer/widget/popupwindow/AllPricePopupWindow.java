package com.newsuper.t.consumer.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.PriceDetailBean;
import com.newsuper.t.consumer.function.distribution.adapter.PriceDetailAdapter;
import com.newsuper.t.consumer.utils.FormatUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public class AllPricePopupWindow extends PopupWindow implements View.OnClickListener{
    private View view;
    private Context context;
    private AllPriceViewHolder viewHolder;
    private PriceDetailAdapter detailAdapter;
    private ArrayList<PriceDetailBean> beans;
    public AllPricePopupWindow(final Context context) {
        super(context);
        setClippingEnabled(false);
        this.context = context;
//        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置PopupWindow弹出窗体的背景
        // 这样设置才能铺满屏幕，去掉这句话会出现缝隙
        this.setBackgroundDrawable(dw);
        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_price_detail, null);
        viewHolder = new AllPriceViewHolder(view);
        viewHolder.ivClose.setOnClickListener(this);
        beans = new ArrayList<>();
        detailAdapter = new PriceDetailAdapter(context,beans);
        viewHolder.piceListView.setAdapter(detailAdapter);
        setContentView(view);
    }

    public void showWithData(String price ,ArrayList<PriceDetailBean> data){
        viewHolder.tvAllPrice.setText(price);
        if (data != null && data.size() > 0){
            beans.clear();
            for (int i = 0;i < data.size() ; i++){
                if (FormatUtil.numDouble(data.get(i).fee) != 0){
                    beans.add(data.get(i));
                }
            }
            detailAdapter.notifyDataSetChanged();
        }
        show();
    }
    public void show() {
        showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    static class AllPriceViewHolder {
        @BindView(R.id.tv_all_price)
        TextView tvAllPrice;
        @BindView(R.id.iv_close)
        ImageView ivClose;
        @BindView(R.id.price_listview)
        ListView piceListView;

        AllPriceViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
