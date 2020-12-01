package com.newsuper.t.consumer.function.person.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.TraceBean;
import com.newsuper.t.consumer.bean.TraceChildBean;
import com.newsuper.t.consumer.bean.TraceGroupBean;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.ActivityLabelLayout;
import com.newsuper.t.consumer.widget.NoticeTextView;
import com.newsuper.t.consumer.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by Administrator on 2019/4/26 0026
 */
public class TraceAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<TraceGroupBean> groupBeans;
    private List<List<TraceChildBean>> childBeans;
    private boolean showCb;
    private CheckBox cbAll;

    public TraceAdapter(Context context, List<TraceGroupBean> groupBeans, List<List<TraceChildBean>> childBeans, CheckBox cbAll) {
        this.mContext = context;
        this.groupBeans = groupBeans;
        this.childBeans = childBeans;
        this.cbAll = cbAll;
    }

    @Override
    public int getGroupCount() {
        return groupBeans.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childBeans.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupBeans.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childBeans.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (null == convertView) {
            holder = new GroupViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_activity_foot_group, null);
            holder.groupCb = convertView.findViewById(R.id.cb_item_activity_foot_group);
            holder.groupName = convertView.findViewById(R.id.tv_item_activity_foot_group);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.groupCb.setChecked(groupBeans.get(groupPosition).checked);
        holder.groupName.setText(groupBeans.get(groupPosition).groupName);
        holder.groupCb.setOnClickListener(new GroupCbOnClick(groupPosition));
        holder.groupCb.setVisibility(showCb ? View.VISIBLE : View.GONE);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder mHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_activity_foot_child, null);
            mHolder = new ChildViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ChildViewHolder) convertView.getTag();
        }
        mHolder.childCb.setChecked(childBeans.get(groupPosition).get(childPosition).checked);
        mHolder.childCb.setVisibility(showCb ? View.VISIBLE : View.GONE);
        mHolder.childCb.setOnClickListener(new ChildCbOnClick(groupPosition, childPosition));

        final TraceBean.DataBean.FootprintlistBean.ItemsBean.ShopinfoBean shop = childBeans.get(groupPosition).get(childPosition).shopInfo;
        loadImageView(shop.shopimage, mHolder.ivStoreLogo);

        if (!StringUtils.isEmpty(shop.shop_label)){
            mHolder.tvLabel.setVisibility(View.VISIBLE);
            mHolder.tvLabel.setText(shop.shop_label);
            mHolder.tvLabel.setBackgroundColor(ContextCompat.getColor(mContext,R.color.label_green));
        }else {
            mHolder.tvLabel.setVisibility(View.GONE);
        }

        //店铺描述
        if (StringUtils.isEmpty(shop.shopdes)) {
            mHolder.llGonggao.setVisibility(View.GONE);
        } else {
            String shopdes = "“" + shop.shopdes + "”";
            mHolder.llGonggao.setVisibility(View.VISIBLE);
            mHolder.tvGonggao.setText(shopdes);
        }
        boolean isTop = false;
        //已打烊
        if (shop.worktime.equals("-1")) {
            isTop = true;
            mHolder.ivShopStatus.setVisibility(View.VISIBLE);
            mHolder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_out_time);
        }
        //休息中
        else if (shop.worktime.equals("0")) {
            mHolder.ivShopStatus.setVisibility(View.VISIBLE);
            mHolder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_rest);
            isTop = true;
        }
        //营业中
        else if (shop.worktime.equals("1")) {
            mHolder.ivShopStatus.setVisibility(View.INVISIBLE);
        }
        //暂停营业
        else if (shop.worktime.equals("-2")) {
            mHolder.ivShopStatus.setVisibility(View.VISIBLE);
            mHolder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_stop);
            isTop = true;
        }
        //停止营业
        else if (shop.worktime.equals("2")) {
            mHolder.ivShopStatus.setVisibility(View.VISIBLE);
            mHolder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_stop);
            isTop = true;
        } else {
            mHolder.ivShopStatus.setVisibility(View.INVISIBLE);
        }
        if (isTop) {
            mHolder.llMain.setAlpha(0.5f);
        } else {
            mHolder.llMain.setAlpha(1);
        }
        //预订单
        if (!"1".equals(shop.worktime)) {
            if (StringUtils.isEmpty(shop.outtime_info)) {
                mHolder.llTip.setVisibility(View.GONE);
                mHolder.llMain.setAlpha(0.5f);
            } else {
                mHolder.llMain.setAlpha(1);
                mHolder.ivShopStatus.setVisibility(View.INVISIBLE);
                mHolder.llTip.setVisibility(View.VISIBLE);
                if (shop.outtime_info.contains(",")) {
                    String s[] = shop.outtime_info.split(",");
                    mHolder.llYu.setText(s[0]);
                    mHolder.tvTip.setText(s[1]);
                } else {
                    if (shop.outtime_info.contains("，")) {
                        String s[] = shop.outtime_info.split("，");
                        mHolder.llYu.setText(s[0]);
                        mHolder.tvTip.setText(s[1]);
                    } else {
                        mHolder.llYu.setText("现在预定");
                        mHolder.tvTip.setText(shop.outtime_info);
                    }
                }
            }
        }else {
            mHolder.llTip.setVisibility(View.GONE);
        }

        mHolder.tvDistance.setVisibility(View.VISIBLE);
        mHolder.tvPrice.setVisibility(View.VISIBLE);
        if (!StringUtils.isEmpty(shop.expected_delivery_times) && "1".equals(shop.is_show_expected_delivery)) {
            String s = shop.expected_delivery_times + "分钟";
            if (!StringUtils.isEmpty(shop.dis) && "1".equals(shop.is_show_distance)) {
                s = s + " | " + shop.dis;
            }
            mHolder.tvDistance.setText(s);
        } else {
            if (!StringUtils.isEmpty(shop.dis) && "1".equals(shop.is_show_distance)) {
                mHolder.tvDistance.setText(shop.dis);
            } else {
                mHolder.tvDistance.setText("");
            }

        }

        mHolder.tvStoreName.setText(shop.shopname);
        UIUtils.setTextViewFakeBold(mHolder.tvStoreName, true);
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
        String price = "起送￥" + FormatUtil.numFormat(shop.basicprice) + "  配送￥" + FormatUtil.numFormat(delivery_fee);
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
        String member = shop.activity_info.member;
        String manzeng = shop.activity_info.manzeng;
        String open_selftake = shop.open_selftake;
        final ArrayList<String> act = new ArrayList<>();
        if (!StringUtils.isEmpty(consume)) {
            act.addAll(shop.activity_info.consume_arr);
        }
        if (!StringUtils.isEmpty(shop_first_order)) {
            act.add(shop_first_order);
        }
        if (!StringUtils.isEmpty(first_order)) {
            act.add(first_order);
        }
        if (!StringUtils.isEmpty(coupon)){
            act.add(coupon);
        }
        if (!StringUtils.isEmpty(shop_discount)) {
            act.add(shop_discount);
        }
        if (!StringUtils.isEmpty(deliveryFee)) {
            act.add(deliveryFee);
        }
        if (!StringUtils.isEmpty(member)) {
            act.add(member);
        }
        if (!StringUtils.isEmpty(manzeng)){
            act.add(manzeng);
        }
        if ("1".equals(open_selftake)){
            act.add("到店自取");
        }
        mHolder.llActivity.setGuideView(mHolder.ivMore);
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
        mHolder.llAct.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(mContext, SelectGoodsActivity3.class);
                intent.putExtra("from_page", "order_list");
                intent.putExtra("shop_id", shop.id);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setShowCb(boolean showCb) {
        this.showCb = showCb;
        notifyDataSetChanged();
    }

    class GroupViewHolder {
        CheckBox groupCb;
        TextView groupName;
    }


    class GroupCbOnClick implements View.OnClickListener {
        private int groupPosition;

        private GroupCbOnClick(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        @Override
        public void onClick(View v) {
            if (v instanceof CheckBox) {
                CheckBox cb = (CheckBox) v;
                groupBeans.get(groupPosition).checked = cb.isChecked();
                List<TraceChildBean> childBeanList = childBeans.get(groupPosition);
                for (TraceChildBean bean : childBeanList) {
                    bean.checked = cb.isChecked();
                }
                cbAll.setChecked(isGroupChecked());
                notifyDataSetChanged();
            }
        }
    }

    class ChildCbOnClick implements View.OnClickListener {
        private int groupPosition;
        private int childPosition;

        private ChildCbOnClick(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        @Override
        public void onClick(View v) {
            if (v instanceof CheckBox) {
                CheckBox cb = (CheckBox) v;
                List<TraceChildBean> childBeanList = childBeans.get(groupPosition);
                TraceChildBean childBean = childBeanList.get(childPosition);
                childBean.checked = cb.isChecked();
                groupBeans.get(groupPosition).checked = isChildChecked(childBeanList);
                cbAll.setChecked(isGroupChecked());
                notifyDataSetChanged();
            }
        }
    }

    private boolean isChildChecked(List<TraceChildBean> childBeanList) {
        for (int i = 0; i < childBeanList.size(); i++) {
            boolean isChecked = childBeanList.get(i).checked;
            if (!isChecked)
                return false;
        }
        return true;
    }

    public boolean isGroupChecked() {
        for (TraceGroupBean bean : groupBeans) {
            if (!bean.checked)
                return false;
        }
        return true;
    }

    public void allChecked(boolean isChecked) {
        for (int i = 0; i < groupBeans.size(); i++) {
            groupBeans.get(i).checked = isChecked;
            for (int j = 0; j < childBeans.get(i).size(); j++) {
                childBeans.get(i).get(j).checked = isChecked;
            }
        }
        notifyDataSetChanged();
    }

    public String getSelectIds() {
        StringBuilder selectIds = new StringBuilder();
        for (int i = 0; i < groupBeans.size(); i++) {
            for (int j = 0; j < childBeans.get(i).size(); j++) {
                if (childBeans.get(i).get(j).checked) {
                    if (!StringUtils.isEmpty(selectIds.toString())) {
                        selectIds.append(",");
                    }
                    selectIds.append(childBeans.get(i).get(j).id);
                }
            }
        }
        return selectIds.toString();
    }

    private void loadImageView(String url, final ImageView view) {
        if (StringUtils.isEmpty(url)){
            view.setImageResource(R.mipmap.store_logo_default);
        }else {
            if (!url.startsWith("http")) {
                url = RetrofitManager.BASE_IMG_URL_SMALL + url;
            }
            //加载网络图片
            UIUtils.glideAppLoadShopImg(mContext,url,R.mipmap.store_logo_default,view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.cb_item_activity_foot_child)
        CheckBox childCb;
        @BindView(R.id.iv_store_logo)
        ImageView ivStoreLogo;
        @BindView(R.id.iv_shop_status)
        ImageView ivShopStatus;
        @BindView(R.id.tv_label)
        TextView tvLabel;
        @BindView(R.id.tv_store_name)
        TextView tvStoreName;
        @BindView(R.id.ll_yu)
        TextView llYu;
        @BindView(R.id.tv_tip)
        TextView tvTip;
        @BindView(R.id.ll_tip)
        LinearLayout llTip;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.tv_star_count)
        TextView tvStarCount;
        @BindView(R.id.tv_sales_count)
        TextView tvSalesCount;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_special_delivery)
        TextView tvSpecialDelivery;
        @BindView(R.id.tv_gonggao)
        NoticeTextView tvGonggao;
        @BindView(R.id.ll_gonggao)
        LinearLayout llGonggao;
        @BindView(R.id.ll_activity)
        ActivityLabelLayout llActivity;
        @BindView(R.id.iv_more)
        ImageView ivMore;
        @BindView(R.id.ll_act)
        LinearLayout llAct;
        @BindView(R.id.ll_main)
        LinearLayout llMain;
        boolean isMore = false;
        View mView;

        ChildViewHolder(View view) {
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
