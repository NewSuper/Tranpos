package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.WShopCart2;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.newsuper.t.consumer.function.selectgoods.inter.IWShopCart;
import com.newsuper.t.consumer.function.top.adapter.GoodsTypeAdapter;
import com.newsuper.t.consumer.function.top.adapter.GridItemDecoration;
import com.newsuper.t.consumer.function.top.adapter.WGoodsGroupScrollAdapter;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.MemberUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;

import java.util.ArrayList;

public class GoodsGroupView extends LinearLayout {
    private Context mContext;
    private RecyclerView rl_goods_type;
    private RecyclerView rl_goods;
    private GoodsTypeAdapter typeAdapter;
    private View rootView;
    private ArrayList<WGoodsGroupScrollAdapter> goodsViews;
    //2.列表类型
    private int list_type;
    private int pageSpace;
    private int imageSpace;
    private String radioVal;
    private WShopCart2 wShopCart;
    private IWShopCart iWShopCart;
    private IGoodsToDetailPage iToGoodsDetailPage;
    //1.菜单类型
    private int select_bg_type;
    private String show_all_group;
    public GoodsGroupView(Context context, WTopBean.GoodsGroupData goodsGroupData, WShopCart2 wShopCart, IWShopCart iWShopCart, IGoodsToDetailPage iToGoodsDetailPage) {
        super(context);
        this.pageSpace = FormatUtil.numInteger(goodsGroupData.pageSpace);
        this.imageSpace = FormatUtil.numInteger(goodsGroupData.imageSpace);
        this.wShopCart = wShopCart;
        this.radioVal = goodsGroupData.radioVal;
        this.list_type = FormatUtil.numInteger(goodsGroupData.list_type);
        this.select_bg_type = FormatUtil.numInteger(goodsGroupData.menu_type);
        this.show_all_group = goodsGroupData.show_all_group;
        this.iWShopCart = iWShopCart;
        this.iToGoodsDetailPage = iToGoodsDetailPage;
        initView(context);
    }

    public GoodsGroupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GoodsGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        this.mContext = context;
        if (pageSpace == 0){
            pageSpace = 15;
        }
        if (imageSpace == 0){
            imageSpace = 10;
        }
        rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_goods_group,null);
        rl_goods_type = (RecyclerView)rootView.findViewById(R.id.rl_goods_type);
        rl_goods = (RecyclerView)rootView.findViewById(R.id.rl_goods);
        rl_goods.setPadding(pageSpace,0,pageSpace,0);
        LinearLayoutManager manager =  new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rl_goods_type.setLayoutManager(manager);

        GridLayoutManager gridLayoutManager = null;
        //大图
        if (list_type == 1){
            gridLayoutManager =  new GridLayoutManager(context,1);
            rl_goods.setLayoutManager(gridLayoutManager);
        }
        //中图
        else  if (list_type == 2){
            GridItemDecoration divider = new GridItemDecoration.Builder(context)
                    .setHorizontalDivider(imageSpace)
                    .setVerticalDivider(imageSpace)
                    .setColorResource(R.color.white_f5)
                    .setShowLastLine(true)
                    .build();
            rl_goods.addItemDecoration(divider);
            gridLayoutManager =  new GridLayoutManager(context,2);
            rl_goods.setLayoutManager(gridLayoutManager);
        }
        //小图
        else  if (list_type == 3){
            GridItemDecoration divider = new GridItemDecoration.Builder(context)
                    .setHorizontalDivider(imageSpace)
                    .setVerticalDivider(imageSpace)
                    .setColorResource(R.color.white_f5)
                    .setShowLastLine(true)
                    .build();
            rl_goods.addItemDecoration(divider);
            gridLayoutManager =  new GridLayoutManager(context,3);
            rl_goods.setLayoutManager(gridLayoutManager);
        }
        //列表
        else  if (list_type == 4){
            LinearLayoutManager manager1 =  new LinearLayoutManager(context);
            manager1.setOrientation(LinearLayoutManager.VERTICAL);
            rl_goods.setLayoutManager(manager1);
        }
        //一大两小
       /* else  if (list_type == 4){
            gridLayoutManager =  new GridLayoutManager(context,2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int num = 1;
                    if (position % 3 == 0){
                        num = 2;
                    }
                    return num;
                }
            });
            rl_goods.setLayoutManager(gridLayoutManager);
        }
        //横向滑动
        else  if (list_type == 5){
            LinearLayoutManager manager1 =  new LinearLayoutManager(context);
            manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            rl_goods.setLayoutManager(manager1);
        }*/

        rl_goods.setNestedScrollingEnabled(false);

        typeAdapter = new GoodsTypeAdapter(context,2);
        typeAdapter.setTypeListener(new GoodsTypeAdapter.GoodsTypeListener() {
            @Override
            public void onSelect(int position) {
                rl_goods.setAdapter(goodsViews.get(position));
            }
        });
        rl_goods_type.setAdapter(typeAdapter);

        if (goodsViews == null){
            goodsViews = new ArrayList<>();
        }
    }


    public GoodsTypeAdapter getTypeAdapter() {
        return typeAdapter;
    }

    public void setGoodsData(WTopBean.GoodsGroupData goodsGroupData){
        LogUtil.log("GoodsGroupView","goodsGroupView  setGoodsData");
        if (goodsGroupData.group_list == null || goodsGroupData.group_list.size() == 0){
            return;
        }
        if (goodsGroupData.group_list.size() == 1){
            for (int i = 0 ; i <goodsGroupData.group_list.size() ; i++){
                WGoodsGroupScrollAdapter scrollAdapter = new WGoodsGroupScrollAdapter(mContext,list_type,imageSpace,radioVal,goodsGroupData,wShopCart,iWShopCart,iToGoodsDetailPage);
                scrollAdapter.setGoodsInfos(goodsGroupData.group_list.get(i).foodlist);
                goodsViews.add(scrollAdapter);
            }
        }else {
            WTopBean.GoodsGroupListData  goodsGroupListData = null;
            if ("1".equals(show_all_group)){
                goodsGroupListData  = new WTopBean.GoodsGroupListData();
                goodsGroupListData.name = "全部";
                goodsGroupListData.foodlist = new ArrayList<>();
                typeAdapter.setGroupList(goodsGroupListData);
                WGoodsGroupScrollAdapter scrollAdapter = new WGoodsGroupScrollAdapter(mContext,list_type,imageSpace,radioVal,goodsGroupData,wShopCart,iWShopCart,iToGoodsDetailPage);
                goodsViews.add(scrollAdapter);
            }
            typeAdapter.setGroupList(goodsGroupData.group_list);
            for (int i = 0 ; i <goodsGroupData.group_list.size() ; i++){
                if (goodsGroupListData != null){
                    goodsGroupListData.foodlist.addAll(goodsGroupData.group_list.get(i).foodlist);
                }
                WGoodsGroupScrollAdapter scrollAdapter = new WGoodsGroupScrollAdapter(mContext,list_type,imageSpace,radioVal,goodsGroupData,wShopCart,iWShopCart,iToGoodsDetailPage);
                scrollAdapter.setGoodsInfos(goodsGroupData.group_list.get(i).foodlist);
                goodsViews.add(scrollAdapter);
            }
            if (goodsGroupListData != null){
                goodsViews.get(0).setGoodsInfos(goodsGroupListData.foodlist);
            }
        }
        rl_goods.setAdapter(goodsViews.get(0));
        addView(rootView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }
    public interface ScrollListener{
        void onScrolled(int y, int position);
    }
}
