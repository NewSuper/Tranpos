package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.TopBean;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.function.top.adapter.WShopAdapter;
import com.newsuper.t.consumer.widget.ListViewForScrollView;
import com.newsuper.t.consumer.widget.RadioView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/3 0003.
 * 竖版店铺
 */

public class WShopListView extends LinearLayout {
    private Context context;
    private WShopViewHolder holder;
    private WShopAdapter shopAdapter;
    public boolean isLoading;
    private ArrayList<TopBean.ShopList> shopLists = new ArrayList<>();
    public WShopListView(Context context) {
        super(context);
        initView(context);
    }

    public WShopListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WShopListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(final Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_wei_vertical_shop, null);
        holder = new WShopViewHolder(view);
        shopAdapter = new WShopAdapter(context,shopLists,"");
        holder.listview.setAdapter(shopAdapter);
        holder.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, SelectGoodsActivity3.class);
                intent.putExtra("from_page", "Wlink");
                intent.putExtra("shop_id",shopLists.get(position).id);
                context.startActivity(intent);
            }
        });
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

    }

    public void setShopData(TopBean bean){
        if (bean.data != null){
            holder.radioView.setShopTypeList(bean.data.shoptype);
            shopLists.addAll(bean.data.shoplist);
            shopAdapter.is_show_expected_delivery = bean.data.is_show_expected_delivery;
            shopAdapter.notifyDataSetChanged();
            if (shopAdapter.getCount() > 5){
                holder.tvLoadMore.setVisibility(VISIBLE);
            }
        }
    }
    public void setShopDataMore(TopBean bean){
        if (bean.data.shoplist != null && bean.data.shoplist.size() > 0){
            loadingComplete();
            shopLists.addAll(bean.data.shoplist);
            shopAdapter.notifyDataSetChanged();
        }else {
            holder.tvLoadMore.setText("已全部显示");
        }
    }

    public void loadingMore(){
        isLoading = true;
        holder.tvLoadMore.setText("加载中...");
    }
    public void loadingComplete(){
        isLoading = false;
    }

    static class WShopViewHolder {
        @BindView(R.id.radioView)
        RadioView radioView;
        @BindView(R.id.listview)
        ListViewForScrollView listview;
        @BindView(R.id.tv_load_more)
        TextView tvLoadMore;
        WShopViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public void setRadioViewSelectListener(RadioView.RadioViewSelectListener radioViewSelectListener) {
        if (holder.radioView != null){
            holder.radioView.setRadioViewSelectListener(radioViewSelectListener);
        }
    }
    public RadioView getRadioView() {
        return holder.radioView;
    }
}
