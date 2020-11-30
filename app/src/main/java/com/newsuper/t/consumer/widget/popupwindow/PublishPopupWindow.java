package com.newsuper.t.consumer.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;


import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.CategoryBean;
import com.newsuper.t.consumer.function.cityinfo.activity.PublishInfoActivity;
import com.newsuper.t.consumer.function.cityinfo.adapter.PublishTypeAdapter;
import com.newsuper.t.consumer.widget.defineTopView.WGridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/26 0026.
 */

public class PublishPopupWindow extends PopupWindow {
    private View view;
    private Context context;
    private PublishViewHolder holder;
    public PublishPopupWindow(Context context) {
        super(context);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_publish_type, null);
        holder = new PublishViewHolder(view);
        this.setContentView(view);
//        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation_publish);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80ffffff);
        // 设置PopupWindow弹出窗体的背景
        // 这样设置才能铺满屏幕，去掉这句话会出现缝隙
        this.setBackgroundDrawable(dw);
        holder.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void showWithData(final ArrayList<CategoryBean.CategoryList> categoryLists){
        if (categoryLists == null || categoryLists.size() == 0){
            return;
        }
        PublishTypeAdapter typeAdapter = new PublishTypeAdapter(context, categoryLists);
        holder.typeGridview.setAdapter(typeAdapter);
        holder.typeGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                Intent intent = new Intent(context, PublishInfoActivity.class);
                intent.putExtra("category_id", categoryLists.get(position).id);
                intent.putExtra("type", categoryLists.get(position).id);
                context.startActivity(intent);
            }
        });
        show();
    }
    private void show(){
        showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
    static class PublishViewHolder {
        @BindView(R.id.type_gridview)
        WGridView typeGridview;
        @BindView(R.id.iv_close)
        ImageView ivClose;

        PublishViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
