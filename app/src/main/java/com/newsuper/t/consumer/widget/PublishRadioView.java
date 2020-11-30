package com.newsuper.t.consumer.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.function.cityinfo.adapter.PublishCategoryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/18 0018.
 */

public class PublishRadioView extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.tv_classify_status)
    TextView tvClassifyStatus;
    @BindView(R.id.iv_classify_status_select)
    ImageView ivClassifyStatusSelect;
    @BindView(R.id.ll_classify_status)
    LinearLayout llClassifyStatus;
    @BindView(R.id.tv_shop_sort)
    TextView tvShopSort;
    @BindView(R.id.iv_shop_sort)
    ImageView ivShopSort;
    @BindView(R.id.ll_shop_sort)
    LinearLayout llShopSort;
    @BindView(R.id.ll_radio_group)
    LinearLayout llRadioGroup;
    private View mView;
    private int currentType = PublishCategoryAdapter.TYPE_SELECT;

    public PublishRadioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @SuppressLint("NewApi")
    public PublishRadioView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public PublishRadioView(Context context) {
        super(context);
        initView(context);
    }


    private void initView(Context context) {
        if (mView == null) {
            mView = LayoutInflater.from(context).inflate(R.layout.layout_radio_publish, null);
            ButterKnife.bind(this, mView);
        }
        addView(mView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        llShopSort.setOnClickListener(this);
        llClassifyStatus.setOnClickListener(this);
        tvClassifyStatus.setTag("0");
        tvShopSort.setTag("0");
    }

    public void setDefaultValue() {
        tvClassifyStatus.setTag("0");
        tvShopSort.setTag("0");
        tvShopSort.setText("默认排序");
        tvClassifyStatus.setText("全部分类");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_shop_sort:
                isSelectSort(true);
                isSelectClassify(false);
                if (shopTypeSelectListener != null) {
                    shopTypeSelectListener.onSelected(PublishCategoryAdapter.SORT_SELECT, tvShopSort);
                    currentType = PublishCategoryAdapter.SORT_SELECT;
                }
                break;
            case R.id.ll_classify_status:
                isSelectSort(false);
                isSelectClassify(true);
                if (shopTypeSelectListener != null) {
                    shopTypeSelectListener.onSelected(PublishCategoryAdapter.TYPE_SELECT, tvClassifyStatus);
                    currentType = PublishCategoryAdapter.TYPE_SELECT;
                }
                break;
        }
    }

    public int getCurrentType() {
        return currentType;
    }

    public void isSelectClassify(boolean b) {
        if (b) {
            tvClassifyStatus.setTextColor(Color.parseColor("#FB797B"));
            ivClassifyStatusSelect.setImageResource(R.mipmap.pull_up_red);
        } else {
            tvClassifyStatus.setTextColor(Color.parseColor("#000000"));
            ivClassifyStatusSelect.setImageResource(R.mipmap.store_list_black);
        }
    }

    public void isSelectSort(boolean b) {
        if (b) {
            tvShopSort.setTextColor(Color.parseColor("#FB797B"));
            ivShopSort.setImageResource(R.mipmap.pull_up_red);
        } else {
            tvShopSort.setTextColor(Color.parseColor("#000000"));
            ivShopSort.setImageResource(R.mipmap.store_list_black);
        }
    }
    public void setSortValue(String id, String name) {
        tvShopSort.setText(name);
        tvShopSort.setTag(id);
        closeView();
    }

    public void setClassifyValue(String id, String name) {
        tvClassifyStatus.setText(name);
        tvClassifyStatus.setTag(id);
        closeView();
    }

    public void closeView() {
        isSelectSort(false);
        isSelectClassify(false);
    }
    public String getSortValue() {
        return tvShopSort.getText().toString();
    }

    public String getSortId() {
        return (String) tvShopSort.getTag();
    }

    public String getClasssifyValue() {
        return tvClassifyStatus.getText().toString();
    }

    public String getClasssifyId() {
        return (String) tvClassifyStatus.getTag();
    }

    public interface CategorySelectListener {
        void onSelected(int type, TextView view);
    }

    private CategorySelectListener shopTypeSelectListener;

    public void setCategorySelectListener(CategorySelectListener shopTypeSelectListener) {
        this.shopTypeSelectListener = shopTypeSelectListener;
    }

    public TextView getSortView() {
        return tvShopSort;
    }

    public TextView getClassifyView() {
        return tvClassifyStatus;
    }

}