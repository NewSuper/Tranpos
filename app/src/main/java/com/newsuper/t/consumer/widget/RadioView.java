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
import com.newsuper.t.consumer.bean.TopBean;
import com.newsuper.t.consumer.widget.popupwindow.ShopSelectPopupWindow;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/5 0005.
 */

public class RadioView extends LinearLayout implements View.OnClickListener{
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
    @BindView(R.id.ll_radio_group)
    LinearLayout llRadioGroup;
    private Context context;
    private View mView;
    private ShopSelectPopupWindow selectPopupWindow;
    private RadioViewSelectListener radioViewSelectListener;
    private RadioViewClickListener clickListener;
    private boolean isTop;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    isSelectType(true);
                    isSelectSort(false);
                    isSelectStatus(false);
                    break;
                case 2:
                    isSelectType(false);
                    isSelectSort(true);
                    isSelectStatus(false);
                    break;
                case 3:
                    isSelectType(false);
                    isSelectSort(false);
                    isSelectStatus(true);
                    break;
            }
        }
    };
    public RadioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @SuppressLint("NewApi")
    public RadioView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public RadioView(Context context) {
        super(context);
        initView(context);
    }

    public void isTop(boolean b) {
        this.isTop = b;
    }

    private void initView(Context context) {
        this.context = context;
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
        tvShopSort.setTag("juli");
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null){
            clickListener.onClick();
            return;

        }
        Message msg = Message.obtain();
        switch (v.getId()){
            case R.id.ll_shop_type:
               msg.what = 1;
                break;
            case R.id.ll_shop_sort:
                msg.what = 2;
                break;
            case R.id.ll_shop_status:
                msg.what = 3;
                break;
        }
        handler.sendMessageDelayed(msg,500);
    }

    public void selectShopType(int i){
        Message msg = Message.obtain();
        switch (i){
            case 1:
                msg.what = 1;
                break;
            case 2:
                msg.what = 2;
                break;
            case 3:
                msg.what = 3;
                break;
        }
        handler.sendMessageDelayed(msg,200);
    }

    public void isSelectType(boolean b){
        if (b){
            tvShopType.setTextColor(Color.parseColor("#FB797B"));
            ivShopType.setImageResource(R.mipmap.pull_up_red);
            if (selectPopupWindow == null){
                selectPopupWindow = new ShopSelectPopupWindow(context,this.getWidth());
                selectPopupWindow.setRadioView(this);
            }
            selectPopupWindow.show(this,SHOP_TYPE,tvShopType);
        }else {
            tvShopType.setTextColor(Color.parseColor("#000000"));
            ivShopType.setImageResource(R.mipmap.store_list_black);
        }
    }
    public void isSelectStatus(boolean b){
        if (b){
            tvShopStatus.setTextColor(Color.parseColor("#FB797B"));
            ivShopStatus.setImageResource(R.mipmap.pull_up_red);
            if (selectPopupWindow == null){
                selectPopupWindow = new ShopSelectPopupWindow(context,this.getWidth());
                selectPopupWindow.setRadioView(this);
            }
            selectPopupWindow.show(this,SHOP_STATUS,tvShopStatus);
        }else {
            tvShopStatus.setTextColor(Color.parseColor("#000000"));
            ivShopStatus.setImageResource(R.mipmap.store_list_black);
        }
    }
    public void isSelectSort(boolean b){

        if (b){
            tvShopSort.setTextColor(Color.parseColor("#FB797B"));
            ivShopSort.setImageResource(R.mipmap.pull_up_red);
            if (selectPopupWindow == null){
                selectPopupWindow = new ShopSelectPopupWindow(context,this.getWidth());
                selectPopupWindow.setRadioView(this);
            }
            selectPopupWindow.show(this,SHOP_SORT,tvShopSort);
        }else {
            tvShopSort.setTextColor(Color.parseColor("#000000"));
            ivShopSort.setImageResource(R.mipmap.store_list_black);
        }
    }

    public void setTypeValue(String id,String name) {
        tvShopType.setText(name);
        tvShopType.setTag(id);
    }
    public void setSortValue(String id,String name) {
        tvShopSort.setText(name);
        tvShopSort.setTag(id);
    }
    public void setStatusValue(String id,String name) {
        tvShopStatus.setText(name);
        tvShopStatus.setTag(id);
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
    public void setShopTypeList(ArrayList<TopBean.ShopType> list){
        if (list != null && list.size() >0 ){
          selectPopupWindow.setTypeList(list);
        }
    }

    public static final String SHOP_TYPE = "shop_type" ;
    public static final String SHOP_SORT = "shop_sort" ;
    public static final String SHOP_STATUS = "shop_status" ;
    public interface RadioViewSelectListener{
        void onSelect(String type, String value);
    }
    public interface RadioViewClickListener{
        void onClick();
    }

    public void setClickListener(RadioViewClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setRadioViewSelectListener(RadioViewSelectListener radioViewSelectListener) {
        this.radioViewSelectListener = radioViewSelectListener;
        if (selectPopupWindow == null){
            selectPopupWindow = new ShopSelectPopupWindow(context,this.getWidth());
            selectPopupWindow.setRadioView(this);
        }
        selectPopupWindow.setSelectListener(radioViewSelectListener);
    }
}
