package com.newsuper.t.consumer.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.function.top.adapter.ShopTypeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShopRadioView extends LinearLayout implements View.OnClickListener{
    @BindView(R.id.tv_shop_type)
    TextView tvShopType;
    @BindView(R.id.iv_shop_type)
    ImageView ivShopType;
    @BindView(R.id.ll_shop_type)
    LinearLayout llShopType;
    @BindView(R.id.tv_shop_sort)
    TextView tvShopSort;
    @BindView(R.id.iv_shop_sort)
    ImageView ivShopSort;
    @BindView(R.id.ll_shop_sort)
    LinearLayout llShopSort;
    @BindView(R.id.tv_shop_status)
    TextView tvShopStatus;
    @BindView(R.id.iv_shop_status_select)
    ImageView ivShopStatus;
    @BindView(R.id.ll_shop_status)
    LinearLayout llShopStatus;
    private View mView;
    private int currentType = ShopTypeAdapter.TYPE_SELECT;
    public ShopRadioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @SuppressLint("NewApi")
    public ShopRadioView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public ShopRadioView(Context context) {
        super(context);
        initView(context);
    }


    private void initView(Context context) {
        if (mView == null){
            mView = LayoutInflater.from(context).inflate(R.layout.layout_radio, null);
            ButterKnife.bind(this,mView);
        }
        addView(mView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        llShopSort.setOnClickListener(this);
        llShopStatus.setOnClickListener(this);
        llShopType.setOnClickListener(this);
        tvShopType.setTag("0");
        tvShopStatus.setTag("0");
        tvShopSort.setTag("tag");
    }
    public void setDefaultValue(){
        tvShopType.setTag("0");
        tvShopStatus.setTag("0");
        tvShopSort.setTag("tag");
        tvShopType.setText("全部分类");
        tvShopSort.setText("综合排序");
        tvShopStatus.setText("筛选");
    }
    public void setSortDefaultValue(){
        tvShopSort.setTag("tag");
        tvShopSort.setText("综合排序");
    }
    public void setSortDisValue(){
        tvShopSort.setTag("juli");
        tvShopSort.setText("距离最近");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_shop_type:
                isSelectType(true);
                isSelectSort(false);
                isSelectStatus(false);
                if (shopTypeSelectListener != null){
                    shopTypeSelectListener.onSelected(ShopTypeAdapter.TYPE_SELECT,tvShopType);
                    currentType = ShopTypeAdapter.TYPE_SELECT;
                }
                break;
            case R.id.ll_shop_sort:
                isSelectType(false);
                isSelectSort(true);
                isSelectStatus(false);
                if (shopTypeSelectListener != null){
                    shopTypeSelectListener.onSelected(ShopTypeAdapter.SORT_SELECT,tvShopSort);
                    currentType = ShopTypeAdapter.SORT_SELECT;
                }
                break;
            case R.id.ll_shop_status:
                isSelectType(false);
                isSelectSort(false);
                isSelectStatus(true);
                if (shopTypeSelectListener != null){
                    shopTypeSelectListener.onSelected(ShopTypeAdapter.STATUS_SELECT,tvShopStatus);
                    currentType = ShopTypeAdapter.STATUS_SELECT;
                }
                break;
        }
    }
    public int getCurrentType(){
        return currentType;
    }
    public void isSelectType(boolean b){
        if (b){
            tvShopType.setTextColor(Color.parseColor("#FB797B"));
            ivShopType.setImageResource(R.mipmap.pull_up_red);
        }else {
            tvShopType.setTextColor(Color.parseColor("#000000"));
            ivShopType.setImageResource(R.mipmap.store_list_black);
        }
    }
    public void isSelectStatus(boolean b){
        if (b){
            tvShopStatus.setTextColor(Color.parseColor("#FB797B"));
            ivShopStatus.setImageResource(R.mipmap.pull_up_red);
        }else {
            tvShopStatus.setTextColor(Color.parseColor("#000000"));
            ivShopStatus.setImageResource(R.mipmap.store_list_black);
        }
    }
    public void isSelectSort(boolean b){
        if (b){
            tvShopSort.setTextColor(Color.parseColor("#FB797B"));
            ivShopSort.setImageResource(R.mipmap.pull_up_red);
        }else {
            tvShopSort.setTextColor(Color.parseColor("#000000"));
            ivShopSort.setImageResource(R.mipmap.store_list_black);
        }
    }

    public void setTypeValue(String id,String name) {
        tvShopType.setText(name);
        tvShopType.setTag(id);
        isSelectType(false);
        isSelectSort(false);
        isSelectStatus(false);
    }
    public void setSortValue(String id,String name) {
        tvShopSort.setText(name);
        tvShopSort.setTag(id);
        isSelectType(false);
        isSelectSort(false);
        isSelectStatus(false);
    }
    public void setStatusValue(String id,String name) {
        tvShopStatus.setText(name);
        tvShopStatus.setTag(id);
        isSelectType(false);
        isSelectSort(false);
        isSelectStatus(false);
    }

    public void closeView(){
        isSelectType(false);
        isSelectSort(false);
        isSelectStatus(false);
    }

    public String getTypeValue(){
        return tvShopType.getText().toString();
    }
    public String getTypeId(){
        return (String)tvShopType.getTag();
    }
    public String getSortValue(){
        return tvShopSort.getText().toString();
    }
    public String getSortId(){
        return (String)tvShopSort.getTag();
    }
    public String getStatusValue(){
        return tvShopStatus.getText().toString();
    }
    public String getStatusId(){
        return (String)tvShopStatus.getTag();
    }
    public interface ShopTypeSelectListener{
        void onSelected(int type, TextView view);
    }
    private ShopTypeSelectListener shopTypeSelectListener;

    public void setShopTypeSelectListener(ShopTypeSelectListener shopTypeSelectListener) {
        this.shopTypeSelectListener = shopTypeSelectListener;
    }

    public TextView getTvShopSort() {
        return tvShopSort;
    }

    public TextView getTvShopStatus() {
        return tvShopStatus;
    }

    public TextView getTvShopType() {
        return tvShopType;
    }
}
