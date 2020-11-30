package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//
import com.squareup.picasso.Picasso;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.TopBean;
import com.xunjoy.lewaimai.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.ActivityLabelLayout;
import com.xunjoy.lewaimai.consumer.widget.NoticeTextView;
import com.xunjoy.lewaimai.consumer.widget.RadioView;
import com.xunjoy.lewaimai.consumer.widget.RatingBar;
import com.xunjoy.lewaimai.consumer.widget.ShopRadioView;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.WGridView;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import static com.xunjoy.lewaimai.consumer.utils.Const.STRING_DIVER;

/**
 * Created by Administrator on 2017/9/1 0001.
 * 微页面店铺列表
 */

public class WShopListAdapter extends BaseAdapter implements View.OnClickListener {
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_STATUS = 0;
    private Context context;
    private ArrayList<TopBean.ShopList> list;
    private ListView listView;
    private boolean isShowShoplist = true;
    private int select,selectCount;
    private String title = "综合排序";
    private  ShopFastScreenAdapter shopScreenAdapter;
    private String is_show_expected_delivery;
    public WShopListAdapter(Context context, ArrayList<TopBean.ShopList> list) {
        this.context = context;
        this.list = list;
        shopScreenAdapter = new ShopFastScreenAdapter(context);
        shopScreenAdapter.setSingleFastSelectListener(new ShopFastScreenAdapter.SingleFastSelectListener() {
            @Override
            public void onSelect(int i, Set<String> setValue) {
                if (listView.getChildCount() > 1){
                    listView.setSelection(1);
                }
                if (shopTypeSelectListener != null){
                    shopTypeSelectListener.onFastSelected(i,setValue);
                }
            }
        });
    }

    public void setIs_show_expected_delivery(String is_show_expected_delivery) {
        this.is_show_expected_delivery = is_show_expected_delivery;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }
    public void clearValue(){
        LogUtil.log("ShopScreenView","clearValue 0000");
        shopScreenAdapter.clearSelect();
        selectCount = 0;
        notifyDataSetChanged();
    }
    public void setSelect(int select) {
        this.select = select;
        notifyDataSetChanged();
    }
    public void setSortTitle(String title) {
        this.title = title;
//        notifyDataSetChanged();
    }
    public void setSelect(int select,int selectCount) {
        this.selectCount = selectCount;
        this.select = select;
        notifyDataSetChanged();
    }
    public void setSelectCount(int selectCount) {
        LogUtil.log("ShopScreenView","setSelectCount ==== "+selectCount);
        this.selectCount = selectCount;
        notifyDataSetChanged();
    }
    public void setSelectCount2(int selectCount) {
        LogUtil.log("ShopScreenView","setSelectCount ==== "+selectCount);
        this.selectCount = selectCount;
    }
    public void setSingleSelect(Set<String> selectValue) {
       shopScreenAdapter.addValue(selectValue);
    }

    @Override
    public int getCount() {
        if (isShowShoplist){
            return list.size() == 0? 1 : list.size() + 1;
        }
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowShoplist){
            if (position == 0){
                return TYPE_STATUS;
            }
            return TYPE_NORMAL;
        }
        return super.getItemViewType(position);
    }
    public void setShowShoplist(boolean showShoplist) {
        isShowShoplist = showShoplist;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == TYPE_STATUS){
            RadioViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_shop_screen_item, null);
                holder = new RadioViewHolder(convertView);
                convertView.setTag(holder);
                holder.tvShopDis.setOnClickListener(this);
                holder.tvShopSale.setOnClickListener(this);
                holder.tvShopScreen.setOnClickListener(this);
                holder.tvShopSort.setOnClickListener(this);
                holder.gv_filter.setAdapter(shopScreenAdapter);
            } else {
                holder = (RadioViewHolder) convertView.getTag();
            }
            holder.tvShopSort.setText(title);
            changeView(select,holder);
        }else if (getItemViewType(position) == TYPE_NORMAL){
            ShopViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_shop_list_wei, null);
                holder = new ShopViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ShopViewHolder) convertView.getTag();
            }
            final ShopViewHolder mHolder = holder;
            final TopBean.ShopList shop = list.get( position - 1);
            loadImageView(shop.shopimage, mHolder.ivStoreLogo);
            if (!StringUtils.isEmpty(shop.shop_label)){
                mHolder.tvLabel.setVisibility(View.VISIBLE);
                mHolder.tvLabel.setText(shop.shop_label);
                mHolder.tvLabel.setBackgroundColor(ContextCompat.getColor(context,R.color.label_green));
            }else {
                mHolder.tvLabel.setVisibility(View.GONE);
            }
            //店铺描述
            if (StringUtils.isEmpty(shop.shopdes)){
                mHolder.ll_gonggao.setVisibility(View.GONE);
            }else {
                mHolder.ll_gonggao.setVisibility(View.VISIBLE);
                mHolder.tv_gonggao.setText("“"+shop.shopdes+"”");
            }
            mHolder.tvStoreName.setText(shop.shopname);
            UIUtils.setTextViewFakeBold(mHolder.tvStoreName,true);
            if ("1".equals(is_show_expected_delivery) && !StringUtils.isEmpty(shop.expected_delivery_times)){
                String s = shop.expected_delivery_times +"分钟";
                if (!StringUtils.isEmpty(shop.dis) && "1".equals(shop.is_show_distance)){
                    s = s +" | "+shop.dis;
                }
                mHolder.tvDistance.setText(s);
            }else {
                if (!StringUtils.isEmpty(shop.dis) && "1".equals(shop.is_show_distance)){
                    mHolder.tvDistance.setText(shop.dis);
                }else {
                    mHolder.tvDistance.setText("");
                }

            }
            mHolder.tvStarCount.setText(shop.commentgrade);
            if ("1".equals(shop.is_show_sales_volume)){
                mHolder.tvSalesCount.setText("已售" + shop.xiaoliang);
                mHolder.tvSalesCount.setVisibility(View.VISIBLE);
            }else {
                mHolder.tvSalesCount.setVisibility(View.GONE);
            }
            if (StringUtils.isEmpty(shop.delivery_service)) {
                mHolder.tvSpecialDelivery.setVisibility(View.GONE);
            } else {
                mHolder.tvSpecialDelivery.setVisibility(View.VISIBLE);
                mHolder.tvSpecialDelivery.setText(shop.delivery_service);
            }
            String delivery_fee = shop.delivery_fee;
            if (StringUtils.isEmpty(delivery_fee)){
                delivery_fee = "0";
            }
            String price = "起送￥" +  FormatUtil.numFormat(shop.basicprice) + "  配送￥" +  FormatUtil.numFormat(delivery_fee);
            mHolder.tvPrice.setText(price);
            mHolder.tvPrice.setText(price);

            if (!StringUtils.isEmpty(shop.commentgrade)) {
                mHolder.ratingBar.setStar(Float.parseFloat(shop.commentgrade));
            }
            mHolder.llActivity.removeAllViews();
            String first_order = shop.activity_info.first_order;
            String shop_first_order = shop.activity_info.shop_first_discount;
            String consume = shop.activity_info.consume;
            String coupon = shop.activity_info.coupon;
            String shop_discount = shop.activity_info.shop_discount;
            String deliveryFee = shop.activity_info.delivery_fee;
            final ArrayList<String> act = new ArrayList<>();
            if (!StringUtils.isEmpty(consume)){
                act.addAll(shop.activity_info.consume_arr);
            }

            if (!StringUtils.isEmpty(shop_first_order)){
                act.add(shop_first_order);
            }

            if (!StringUtils.isEmpty(first_order)){
                act.add(first_order);
            }

            if (!StringUtils.isEmpty(coupon)){
                act.add(coupon);
            }
            if (!StringUtils.isEmpty(deliveryFee)){
                act.add(deliveryFee);
            }
            String member = shop.activity_info.member;
            if (!StringUtils.isEmpty(member)){
                act.add(member);
            }
            if (!StringUtils.isEmpty(shop_discount)){
                act.add(shop_discount);
            }
            String manzeng = shop.activity_info.manzeng;
            if ( !StringUtils.isEmpty(manzeng)){
                act.add(manzeng);
            }
            String open_selftake = shop.open_selftake;
            if ("1".equals(open_selftake)){
                act.add("到店自取");
            }
            mHolder.llActivity.setGuideView(mHolder.ivMore);
            mHolder.ivMore.setVisibility(View.GONE);
            mHolder.ivMore.setVisibility(View.GONE);
            mHolder.llActivity.setRowListener(new ActivityLabelLayout.RowListener() {
                @Override
                public void onRow(int r) {
                    if (r > 0){
                        mHolder.ivMore.setVisibility(View.VISIBLE);
                    }else {
                        mHolder.ivMore.setVisibility(View.GONE);
                    }
                }
            });
            mHolder.llActivity.setActivityLabelView(act,false);
            mHolder.isMore = false;
            mHolder.ivMore.setImageResource(R.mipmap.push);
            mHolder.ll_act.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mHolder.isMore){
                        mHolder.isMore = true;
                        mHolder.ivMore.setImageResource(R.mipmap.pull);
                        mHolder.llActivity.setActivityLabelView(act,true);
                    }else {
                        mHolder.isMore = false;
                        mHolder.ivMore.setImageResource(R.mipmap.push);
                        mHolder.llActivity.setActivityLabelView(act,false);
                    }
                }
            });

            mHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SelectGoodsActivity3.class);
                    intent.putExtra("from_page", "WShopList");
                    intent.putExtra("shop_info", shop);
                    context.startActivity(intent);
                }
            });
            boolean isTop = false;
            //已打烊
            if (shop.worktime.equals("-1")){
                isTop = true;
                mHolder.ivShopStatus.setVisibility(View.VISIBLE);
                mHolder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_out_time);
            }
            //休息中
            else if (shop.worktime.equals("0")){
                mHolder.ivShopStatus.setVisibility(View.VISIBLE);
                mHolder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_rest);
                isTop = true;
            }
            //营业中
            else if (shop.worktime.equals("1")){
                mHolder.ivShopStatus.setVisibility(View.INVISIBLE);
            }
            //暂停营业
            else if (shop.worktime.equals("-2")){
                mHolder.ivShopStatus.setVisibility(View.VISIBLE);
                mHolder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_stop);
                isTop = true;
            }
            //停止营业
            else if (shop.worktime.equals("2")){
                mHolder.ivShopStatus.setVisibility(View.INVISIBLE);
                isTop = true;
            }else {
                mHolder.ivShopStatus.setVisibility(View.INVISIBLE);
            }
            if (isTop){
                holder.ll_main.setAlpha(0.5f);
            }else {
                holder.ll_main.setAlpha(1);
            }
            //预订单
            if (!"1".equals(shop.worktime)){
                if (StringUtils.isEmpty(shop.outtime_info)){
                    mHolder.llTip.setVisibility(View.GONE);
                }else {
                    holder.ll_main.setAlpha(1);
                    mHolder.ivShopStatus.setVisibility(View.INVISIBLE);
                    mHolder.llTip.setVisibility(View.VISIBLE);
                    if (shop.outtime_info.contains(",")){
                        String s[] = shop.outtime_info.split(",");
                        mHolder.tvYu.setText(s[0]);
                        mHolder.tvTip.setText(s[1]);
                    }else {
                        if (shop.outtime_info.contains("，")){
                            String s[] = shop.outtime_info.split("，");
                            mHolder.tvYu.setText(s[0]);
                            mHolder.tvTip.setText(s[1]);
                        }else {
                            mHolder.tvYu.setText("现在预定");
                            mHolder.tvTip.setText(shop.outtime_info);
                        }
                    }
                }
            }else {
                mHolder.llTip.setVisibility(View.GONE);
            }
        }

        return convertView;
    }
    //改变排序字体
    private void sortOnSelect(RadioViewHolder viewHolder,boolean b){
        if (b){
            viewHolder.tvShopSort.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.tvShopSort.setTextColor(ContextCompat.getColor(context,R.color.text_color_33));
            viewHolder.ivShopSort.setImageResource(R.mipmap.icon_xiala_blark);
            saleOnSelect(viewHolder,false);
            distanceOnSelect(viewHolder,false);
        }else {
            viewHolder.tvShopSort.setTextColor(ContextCompat.getColor(context,R.color.text_color_66));
            viewHolder.tvShopSort.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            viewHolder.ivShopSort.setImageResource(R.mipmap.icon_xiala_gray);
        }

    }
    //改变销量字体
    private void saleOnSelect(RadioViewHolder viewHolder,boolean b){
        if (b){
            viewHolder.tvShopSale.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.tvShopSale.setTextColor(ContextCompat.getColor(context,R.color.text_color_33));
            viewHolder.tvShopSort.setText("综合排序");
            setSortTitle("综合排序");
            sortOnSelect(viewHolder,false);
            distanceOnSelect(viewHolder,false);
        }else {
            viewHolder.tvShopSale.setTextColor(ContextCompat.getColor(context,R.color.text_color_66));
            viewHolder.tvShopSale.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }

    }
    //改变距离字体
    private void distanceOnSelect(RadioViewHolder viewHolder,boolean b){
        if (b){
            viewHolder.tvShopDis.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.tvShopDis.setTextColor(ContextCompat.getColor(context,R.color.text_color_33));
            viewHolder.tvShopSort.setText("综合排序");
            setSortTitle("综合排序");
            sortOnSelect(viewHolder,false);
            saleOnSelect(viewHolder,false);
        }else {
            viewHolder.tvShopDis.setTextColor(ContextCompat.getColor(context,R.color.text_color_66));
            viewHolder.tvShopDis.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }
    //改变筛选字体
    private void screenOnSelect(RadioViewHolder viewHolder ,boolean b){
        if (b){
            viewHolder.tvShopScreen.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.tvShopScreen.setTextColor(ContextCompat.getColor(context,R.color.text_color_33));
            viewHolder.ivShopScreen.setImageResource(R.mipmap.icon_xiala_blark);
        }else {
            viewHolder.tvShopScreen.setTextColor(ContextCompat.getColor(context,R.color.text_color_66));
            viewHolder.tvShopScreen.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            viewHolder.ivShopScreen.setImageResource(R.mipmap.icon_xiala_gray);
        }
    }
    private void changeView(int position,RadioViewHolder viewHolder){
        switch (position){
            case 0:
                sortOnSelect(viewHolder,true);
                break;
            case 1:
                saleOnSelect(viewHolder,true);
                break;
            case 2:
                distanceOnSelect(viewHolder,true);
                break;
            case 3:
                screenOnSelect(viewHolder,true);
                break;
        }
        if (selectCount > 0){
            viewHolder.tv_count.setText(""+selectCount);
            viewHolder.tv_count.setVisibility(View.VISIBLE);
            screenOnSelect(viewHolder,true);
        }else {
            screenOnSelect(viewHolder,false);
            viewHolder.tv_count.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_shop_sort://综合排序
                select = 0;
                if (shopTypeSelectListener != null) {
                    shopTypeSelectListener.onSelected(0);
                }
                break;
            case R.id.tv_shop_sale://销量
                select = 1;
                if (shopTypeSelectListener != null) {
                    shopTypeSelectListener.onSelected(1);
                }
                break;
            case R.id.tv_shop_dis://距离排序
                select = 2;
                if (shopTypeSelectListener != null) {
                    shopTypeSelectListener.onSelected(2);
                }
                break;
            case R.id.tv_shop_screen://筛选
                select = 3;
                if (shopTypeSelectListener != null) {
                    shopTypeSelectListener.onSelected(3);
                }
                break;
        }
    }

    static class RadioViewHolder{
        @BindView(R.id.tv_shop_sort)
        TextView tvShopSort;
        @BindView(R.id.iv_shop_sort)
        ImageView ivShopSort;
        @BindView(R.id.tv_shop_sale)
        TextView tvShopSale;
        @BindView(R.id.tv_shop_dis)
        TextView tvShopDis;
        @BindView(R.id.tv_shop_screen)
        TextView tvShopScreen;
        @BindView(R.id.iv_shop_screen)
        ImageView ivShopScreen;
        @BindView(R.id.gv_filter)
        WGridView gv_filter;
        @BindView(R.id.tv_count)
        TextView tv_count;
        View mView;
        RadioViewHolder(View view) {
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
    static class ShopViewHolder {
        @BindView(R.id.iv_store_logo)
        ImageView ivStoreLogo;
        @BindView(R.id.iv_shop_status)
        ImageView ivShopStatus;
        @BindView(R.id.tv_store_name)
        TextView tvStoreName;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.tv_star_count)
        TextView tvStarCount;
        @BindView(R.id.tv_sales_count)
        TextView tvSalesCount;
        @BindView(R.id.tv_special_delivery)
        TextView tvSpecialDelivery;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.ll_activity)
        ActivityLabelLayout llActivity;
        @BindView(R.id.ll_act)
        LinearLayout ll_act;
        @BindView(R.id.iv_more)
        ImageView ivMore;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;
        @BindView(R.id.tv_label)
        TextView tvLabel;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_tip)
        TextView tvTip;
        @BindView(R.id.ll_yu)
        TextView tvYu;
        @BindView(R.id.ll_tip)
        LinearLayout llTip;
        @BindView(R.id.ll_gonggao)
        LinearLayout ll_gonggao;
        @BindView(R.id.tv_gonggao)
        NoticeTextView tv_gonggao;
        boolean isMore;
        View mView;

        ShopViewHolder(View view) {
            mView = view;
            ButterKnife.bind(this, view);
        }
    }

    private void loadImageView(String url, final ImageView view) {
        if (StringUtils.isEmpty(url)){
            view.setImageResource(R.mipmap.store_logo_default);
        }else {
            if (!url.startsWith("http")) {
                url = RetrofitManager.BASE_IMG_URL_SMALL + url;
            }
            //加载网络图片
            UIUtils.glideAppLoadShopImg(context,url,R.mipmap.store_logo_default,view);
        }

    }

    public interface ShopTypeSelectListener{
        void onSelected(int i);
        void onFastSelected(int i, Set<String> value);
    }
    private ShopTypeSelectListener shopTypeSelectListener;


    public void setShopTypeSelectListener(ShopTypeSelectListener shopTypeSelectListener) {
        this.shopTypeSelectListener = shopTypeSelectListener;
    }
}
