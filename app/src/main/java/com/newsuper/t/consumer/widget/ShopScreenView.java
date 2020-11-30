package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.TopBean;
import com.newsuper.t.consumer.function.top.adapter.ShopFastScreenAdapter;
import com.newsuper.t.consumer.function.top.adapter.ShopScreenAdapter;
import com.newsuper.t.consumer.function.top.adapter.ShopSortAdapter;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

//店铺筛选
public class ShopScreenView extends LinearLayout implements View.OnClickListener{
    private static String SORT_ALL = "tag";
    private static String SORT_SALE = "xiaoliang";
    private static String SORT_DISTANCE = "juli";
    private Context mContext;
    private View mView;
    private ShopScreenViewHolder viewHolder;
    private int select;
    private ArrayList<TopBean.ShopType> classifyList;
    private ShopScreenAdapter shopScreenAdapter1,shopScreenAdapter2,shopScreenAdapter3;
    private ShopFastScreenAdapter shopScreenAdapter4;
    private ShopSortAdapter shopSortAdapter;
    public boolean isShowSingle;
    public int selectCount;
    private Set<String> setFast;
    public ShopScreenView(Context context) {
        super(context);
        init(context);
    }
    public ShopScreenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShopScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        this.mContext  = context;
        if (mView == null) {
            mView = LayoutInflater.from(context).inflate(R.layout.layout_shop_screen_title, null);
            viewHolder = new ShopScreenViewHolder(mView);
            ButterKnife.bind(this, mView);
            viewHolder.tvShopDis.setOnClickListener(this);
            viewHolder.tvShopSale.setOnClickListener(this);
            viewHolder.tvClear.setOnClickListener(this);
            viewHolder.tvOk.setOnClickListener(this);
            viewHolder.vv_bottom.setOnClickListener(this);
            viewHolder.ll_shop_screen.setOnClickListener(this);
            viewHolder.ll_shop_sort.setOnClickListener(this);
//            viewHolder.tv_reset.setOnClickListener(this);
        }
        initData();
        addView(mView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setShowSingle(boolean showSingle) {
        isShowSingle = showSingle;
        if (isShowSingle){
            viewHolder.gv_filter.setVisibility(VISIBLE);
        }else{
            viewHolder.gv_filter.setVisibility(GONE);
        }
    }

    private void initData(){
        shopSortAdapter = new ShopSortAdapter(mContext);
        viewHolder.lvSort.setAdapter(shopSortAdapter);
        viewHolder.lvSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shopSortAdapter.setCurrentType(position);
                viewHolder.lvSort.setVisibility(GONE);
                viewHolder.vv_bottom.setVisibility(GONE);
                setEmptyHeight(false);
                if (isShowSingle){
                    viewHolder.gv_filter.setVisibility(VISIBLE);
                }else {
                    viewHolder.gv_filter.setVisibility(GONE);
                }
                sortOnSelect(true);
                String s = shopSortAdapter.getSelectValue(position).id;
                String n = shopSortAdapter.getSelectValue(position).name;
                viewHolder.tvShopSort.setText(n);
                setSortValue(s);
                if (shopScreenListener != null){
                    shopScreenListener.onSort(s,n);
                    shopScreenListener.onSelect(select);
                }
            }
        });
        setFast = new HashSet<>();
        classifyList = new ArrayList<>();

        //单选 活动
        shopScreenAdapter1 = new ShopScreenAdapter(mContext,1);
        viewHolder.gvDiscount.setAdapter(shopScreenAdapter1);

        //多选 商家服务
        shopScreenAdapter2 = new ShopScreenAdapter(mContext,2);
        viewHolder.gvService.setAdapter(shopScreenAdapter2);

        //店铺分类
        shopScreenAdapter3 = new ShopScreenAdapter(mContext,4);
        viewHolder.gvClassify.setAdapter(shopScreenAdapter3);

        //快捷单选
        shopScreenAdapter4 = new ShopFastScreenAdapter(mContext);
        viewHolder.gv_filter.setAdapter(shopScreenAdapter4);
        shopScreenAdapter4.setSingleFastSelectListener(new ShopFastScreenAdapter.SingleFastSelectListener() {
            @Override
            public void onSelect(int position, Set<String> selectValue ) {
//                viewHolder.ll_none.setVisibility(GONE);
                LogUtil.log("ShopScreenView",position + " 快捷 000 " + selectValue);
                if (selectValue.size() == 0){
                    shopScreenAdapter2.removeValue("1");
                    shopScreenAdapter1.removeValue("11");
                    shopScreenAdapter1.removeValue("2");
                    shopScreenAdapter1.removeValue("3");
                }else {
                    if (selectValue.contains("1")){
                        shopScreenAdapter2.addValue("1");
                    }else {
                        shopScreenAdapter2.removeValue("1");
                    }
                    if (selectValue.contains("11") || selectValue.contains("2") || selectValue.contains("3")){
                        for (String v : selectValue){
                            shopScreenAdapter1.addValue(v);
                        }
                    }else {
                        shopScreenAdapter1.removeValue("11");
                        shopScreenAdapter1.removeValue("2");
                        shopScreenAdapter1.removeValue("3");
                    }

                }
                countSelect();
                showRedCountPoint();
                if (shopScreenListener != null){
                    String type  = shopScreenAdapter3.getSelectValue();
                    String service  = shopScreenAdapter2.getSelectValue();
                    String filter  = shopScreenAdapter1.getSelectValue();
                    setFilterValue(filter);
                    setServiceValue(service);
                    setTypeValue(type);
                    shopScreenListener.onScreen(filter,service,type);
                    shopScreenListener.onSingleSelect(position,selectValue);
                }
                screenOnSelect(true);

            }
        });
        shopScreenAdapter3.setSingleSelectListener(new ShopScreenAdapter.SingleSelectListener() {
            @Override
            public void onSelect(int position, String v) {
                countSelect();
            }
        });
        shopScreenAdapter2.setSingleSelectListener(new ShopScreenAdapter.SingleSelectListener() {
            @Override
            public void onSelect(int position, String v) {
                countSelect();
            }
        });
        shopScreenAdapter1.setSingleSelectListener(new ShopScreenAdapter.SingleSelectListener() {
            @Override
            public void onSelect(int position, String v) {
                countSelect();
            }
        });
    }

    public void setSelect(int select) {
        changeView(select);
    }
    //改变排序字体
    private void sortOnSelect(boolean b){
        if (b){
            viewHolder.tvShopSort.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.tvShopSort.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_33));
            viewHolder.ivShopSort.setImageResource(R.mipmap.icon_xiala_blark);
            saleOnSelect(false);
            distanceOnSelect(false);
//            screenOnSelect(false);
        }else {
            viewHolder.tvShopSort.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_66));
            viewHolder.tvShopSort.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            viewHolder.ivShopSort.setImageResource(R.mipmap.icon_xiala_gray);
        }

    }
    //改变销量字体
    private void saleOnSelect(boolean b){
        if (b){
            viewHolder.tvShopSale.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.tvShopSale.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_33));
            sortOnSelect(false);
            distanceOnSelect(false);
//            screenOnSelect(false);
        }else {
            viewHolder.tvShopSale.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_66));
            viewHolder.tvShopSale.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }

    }
    //改变距离字体
    private void distanceOnSelect(boolean b){
        if (b){
            viewHolder.tvShopDis.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.tvShopDis.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_33));
            sortOnSelect(false);
            saleOnSelect(false);
//            screenOnSelect(false);
        }else {
            viewHolder.tvShopDis.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_66));
            viewHolder.tvShopDis.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }
    //改变筛选字体
    private void screenOnSelect(boolean b){
        if (b){
            viewHolder.tvShopScreen.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            viewHolder.tvShopScreen.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_33));
            viewHolder.ivShopScreen.setImageResource(R.mipmap.icon_xiala_blark);
//            sortOnSelect(false);
//            saleOnSelect(false);
//            distanceOnSelect(false);
        }else {
            viewHolder.tvShopScreen.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_66));
            viewHolder.tvShopScreen.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            viewHolder.ivShopScreen.setImageResource(R.mipmap.icon_xiala_gray);
        }
    }
    private void changeView(int position){
        this.select = position;
        switch (position){
            case 0:
                if (getSortValue().equals(SORT_DISTANCE) || getSortValue().equals(SORT_SALE)){
                    viewHolder.tvShopSort.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_66));
                    viewHolder.tvShopSort.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }else {
                   sortOnSelect(true);
                }
                viewHolder.vv_bottom.setVisibility(GONE);
                viewHolder.llScreen.setVisibility(GONE);
                if (viewHolder.lvSort.getVisibility() == View.VISIBLE ){
                    setEmptyHeight(false);
                    viewHolder.lvSort.setVisibility(GONE);
                    viewHolder.vv_bottom.setVisibility(GONE);
                    viewHolder.ivShopSort.setImageResource(R.mipmap.icon_xiala_blark);
                    if (isShowSingle){
                        viewHolder.gv_filter.setVisibility(VISIBLE);
                    }
                }else {
                    setEmptyHeight(true);
                    viewHolder.ivShopSort.setImageResource(R.mipmap.icon_shangla_blark);
                    viewHolder.lvSort.setVisibility(VISIBLE);
                    viewHolder.vv_bottom.setVisibility(VISIBLE);
                    viewHolder.gv_filter.setVisibility(GONE);
                }
                break;
            case 1:
                saleOnSelect(true);
                viewHolder.lvSort.setVisibility(GONE);
                viewHolder.llScreen.setVisibility(GONE);
                viewHolder.vv_bottom.setVisibility(GONE);
                if (shopScreenListener != null){
                    setSortValue(SORT_SALE);
                    shopSortAdapter.setCurrentType(-1);
                    shopScreenListener.onSale(SORT_SALE);
                }
                setEmptyHeight(false);
                break;
            case 2:
                distanceOnSelect(true);
                viewHolder.lvSort.setVisibility(GONE);
                viewHolder.llScreen.setVisibility(GONE);
                viewHolder.vv_bottom.setVisibility(GONE);
                if (shopScreenListener != null){
                    setSortValue(SORT_DISTANCE);
                    shopSortAdapter.setCurrentType(-1);
                    shopScreenListener.onDistance(SORT_DISTANCE);
                }
                setEmptyHeight(false);
                break;
            case 3:
                screenOnSelect(true);
                viewHolder.lvSort.setVisibility(GONE);
                if (viewHolder.llScreen.getVisibility() == View.VISIBLE ){
                    setEmptyHeight(false);
                    viewHolder.llScreen.setVisibility(GONE);
                    viewHolder.vv_bottom.setVisibility(GONE);
                    if (selectCount == 0){
                        screenOnSelect(false);
                    }else {
                        viewHolder.ivShopScreen.setImageResource(R.mipmap.icon_xiala_blark);
                    }
                }else {
                    setEmptyHeight(true);
                    viewHolder.ivShopScreen.setImageResource(R.mipmap.icon_shangla_blark);
                    viewHolder.llScreen.setVisibility(VISIBLE);
                    viewHolder.vv_bottom.setVisibility(VISIBLE);
                }
                if (isShowSingle){
                    if (viewHolder.llScreen.getVisibility() != View.VISIBLE){
                        viewHolder.gv_filter.setVisibility(VISIBLE);
                    }else{
                        viewHolder.gv_filter.setVisibility(GONE);
                    }
                }
                break;
        }
    }

    private void setEmptyHeight(boolean isShow) {
        int size = SharedPreferencesUtil.getShopListSize();
        WFooterView instance = WFooterView.getInstance();
        if (null != instance) {
            if (size > 0 && size < 5) {
                instance.setVwEmptyHight(isShow ? (4 - size) * 120 + UIUtils.dip2px(200) : UIUtils.dip2px(200));
            } else if (size == 0) {
                instance.setLLFilterEmptyMarginTop(isShow ? UIUtils.dip2px(240) : 0);
            }
        }
    }

    public void addValue(Set<String> selectValue, int position){
        LogUtil.log("ShopScreenView",position + " 快捷 111 " + selectValue);
        if (selectValue.size() == 0){
            shopScreenAdapter2.removeValue("1");
            shopScreenAdapter1.removeValue("11");
            shopScreenAdapter1.removeValue("2");
            shopScreenAdapter1.removeValue("3");
        }else {
            if (selectValue.contains("1")){
                shopScreenAdapter2.addValue("1");
            }else {
                shopScreenAdapter2.removeValue("1");
            }
            if (selectValue.contains("11") || selectValue.contains("2") || selectValue.contains("3")){
                for (String v : selectValue){
                    shopScreenAdapter1.addValue(v);
                }
            }else {
                shopScreenAdapter1.removeValue("11");
                shopScreenAdapter1.removeValue("2");
                shopScreenAdapter1.removeValue("3");
            }

        }
        shopScreenAdapter4.addValue(selectValue);
        countSelect();
        showRedCountPoint();
        screenOnSelect(true);
    }
    // 多选 店铺分类
    public void setShopClassifyData(ArrayList<TopBean.ShopType> list){
        if (list != null && list.size() > 0 ){
            classifyList.clear();
            classifyList.addAll(list);
            shopScreenAdapter3.setScreenList(classifyList);
        }
    }

    public int getSelectCount() {
        selectCount = shopScreenAdapter1.getSelectCount() + shopScreenAdapter2.getSelectCount()
                + shopScreenAdapter3.getSelectCount();
        return selectCount;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_shop_sort:
                changeView(0);
                break;
            case R.id.tv_shop_sale:
                changeView(1);
                break;
            case R.id.tv_shop_dis:
                changeView(2);
                break;
            case R.id.ll_shop_screen:
                changeView(3);
                break;
            case R.id.tv_ok:
                viewHolder.llScreen.setVisibility(GONE);
                viewHolder.vv_bottom.setVisibility(GONE);
                setEmptyHeight(false);
                viewHolder.ivShopScreen.setImageResource(R.mipmap.icon_xiala_blark);
                if (isShowSingle){
                    viewHolder.gv_filter.setVisibility(VISIBLE);
                }else{
                    viewHolder.gv_filter.setVisibility(GONE);
                }
                countSelect();
                showRedCountPoint();
                if (selectCount == 0){
                    screenOnSelect(false);
                }else {
                    screenOnSelect(true);
                }
                if (shopScreenListener != null){
                    String type  = shopScreenAdapter3.getSelectValue();
                    String service  = shopScreenAdapter2.getSelectValue();
                    String filter  = shopScreenAdapter1.getSelectValue();
                    setFilterValue(filter);
                    setServiceValue(service);
                    setTypeValue(type);
                    shopScreenListener.onScreen(filter,service,type);
                    if (isShowSingle){
                        setFast.clear();
                        setFast.addAll(shopScreenAdapter1.getSingleSet());
                        if (shopScreenAdapter2.getSingleSet().contains("1")){
                            setFast.add("1");
                        }
                        shopScreenAdapter4.addValue(setFast);
                        shopScreenListener.onSingleSelect(0,setFast);
                    }
                }
                break;
            case R.id.tv_clear:
                reset();
                break;
            case R.id.vv_bottom:
                viewHolder.vv_bottom.setVisibility(GONE);
                viewHolder.llScreen.setVisibility(GONE);
                viewHolder.lvSort.setVisibility(GONE);
                setEmptyHeight(false);
                if (select == 0){
                    viewHolder.ivShopSort.setImageResource(R.mipmap.icon_xiala_blark);
                }else if (select == 3){
                    viewHolder.ivShopScreen.setImageResource(R.mipmap.icon_xiala_blark);
                }
                if (shopScreenListener != null){
                    shopScreenListener.onSelect(select);
                }
                if (isShowSingle){
                    viewHolder.gv_filter.setVisibility(VISIBLE);
                }else {
                    viewHolder.gv_filter.setVisibility(GONE);
                }

                break;
            case R.id.tv_reset:
                changeView(3);
                break;
        }
    }


    public void countSelect(){
        LogUtil.log("ShopScreenView"," 活动 " + shopScreenAdapter1.getSelectCount());
        LogUtil.log("ShopScreenView"," 服务 " + shopScreenAdapter2.getSelectCount());
        LogUtil.log("ShopScreenView"," 分类 " + shopScreenAdapter3.getSelectCount());
        selectCount = shopScreenAdapter1.getSelectCount() + shopScreenAdapter2.getSelectCount()
                + shopScreenAdapter3.getSelectCount();
        if (selectCount > 0){
            viewHolder.tvOk.setText("完成（"+selectCount+"）");
        }else {
            viewHolder.tvOk.setText("完成");
        }

    }
    public void showRedCountPoint(){
        if (selectCount > 0){
            viewHolder.tv_count.setText(selectCount+"");
            viewHolder.tv_count.setVisibility(VISIBLE);
        }else {
            viewHolder.tv_count.setText("");
            viewHolder.tv_count.setVisibility(INVISIBLE);
        }
    }

    static class ShopScreenViewHolder {
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
        @BindView(R.id.lv_sort)
        ListView lvSort;
        @BindView(R.id.gv_discount)
        WGridView gvDiscount;
        @BindView(R.id.gv_service)
        WGridView gvService;
        @BindView(R.id.gv_classify)
        WGridView gvClassify;
        @BindView(R.id.tv_clear)
        TextView tvClear;
        @BindView(R.id.tv_ok)
        TextView tvOk;
        @BindView(R.id.ll_screen)
        LinearLayout llScreen;
        @BindView(R.id.vv_bottom)
        View vv_bottom;
        @BindView(R.id.ll_shop_screen)
        LinearLayout ll_shop_screen;
        @BindView(R.id.ll_shop_sort)
        LinearLayout ll_shop_sort;
       /* @BindView(R.id.tv_reset)
        TextView tv_reset;
        @BindView(R.id.ll_none)
        LinearLayout ll_none;*/
        @BindView(R.id.gv_filter)
        WGridView gv_filter;
        @BindView(R.id.tv_count)
        TextView tv_count;
        ShopScreenViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    private ShopScreenListener shopScreenListener;

    public void setShopScreenListener(ShopScreenListener shopScreenListener) {
        this.shopScreenListener = shopScreenListener;
    }

    public interface ShopScreenListener{
        void onSort(String value, String valueName);
        void onSale(String value);
        void onDistance(String value);
        void onScreen(String filter, String service, String type);
        void onSelect(int i);
        void onSingleSelect(int i, Set<String> selectValue);
        void onClear();
    }

    private String sortValue = "tag";
    private String typeValue = "";
    private String serviceValue = "";
    private String filterValue = "0";

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public String getFilterValue() {
        return filterValue;

    }

    public void setServiceValue(String serviceValue) {
        this.serviceValue = serviceValue;
    }

    public String getServiceValue() {
        return serviceValue;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
        if (sortValue.equals(SORT_SALE) || sortValue.equals(SORT_DISTANCE)){
            viewHolder.tvShopSort.setText("综合排序");
        }
    }

    public String getSortValue() {
        return sortValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }
    public void setTypeSelectValue(String typeValue) {
        this.typeValue = typeValue;
        this.shopScreenAdapter3.addValue(typeValue);
        countSelect();
        showRedCountPoint();
        screenOnSelect(true);
    }

    public String getTypeValue() {
        return typeValue;
    }


    public void setDefaultValue2(){
        setFilterValue("0");
        setServiceValue("");
        setTypeValue("");
        shopScreenAdapter1.clearSelect();
        shopScreenAdapter2.clearSelect();
        shopScreenAdapter3.clearSelect();
        shopScreenAdapter4.clearSelect();
        selectCount = 0;
        viewHolder.tv_count.setText("");
        viewHolder.tv_count.setVisibility(INVISIBLE);
        viewHolder.tvOk.setText("完成");
        select = 0;
        //显示排序
        setSortValue("tag");
        shopSortAdapter.setCurrentType(0);
        viewHolder.tvShopSort.setText("综合排序");
        sortOnSelect(true);
        screenOnSelect(false);
        viewHolder.vv_bottom.setVisibility(GONE);
        viewHolder.llScreen.setVisibility(GONE);
    }


    public void reset(){
        setFilterValue("0");
        setServiceValue("");
        setTypeValue("");
        shopScreenAdapter1.clearSelect();
        shopScreenAdapter2.clearSelect();
        shopScreenAdapter3.clearSelect();
        shopScreenAdapter4.clearSelect();
        selectCount = 0;
        viewHolder.tv_count.setText("");
        viewHolder.tv_count.setVisibility(INVISIBLE);
        viewHolder.tvOk.setText("完成");
        if (shopScreenListener != null){
            shopScreenListener.onClear();
        }
    }
}
