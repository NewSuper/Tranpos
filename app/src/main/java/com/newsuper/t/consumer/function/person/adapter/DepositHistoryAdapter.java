package com.newsuper.t.consumer.function.person.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.DepositHistoryBean;
import com.xunjoy.lewaimai.consumer.function.person.activity.DepositDetailActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public class DepositHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<DepositHistoryBean.DataBean.DatasBean> mList;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    private boolean isLoadAll = false;

    public DepositHistoryAdapter(Context mContext, ArrayList<DepositHistoryBean.DataBean.DatasBean> mList) {
        super();
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View footView = LayoutInflater.from(parent.getContext()).inflate(R.layout.deposit_history_footer_view, parent, false);
            return new FooterViewHolder(footView);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_deposit_history_list, parent, false);
            return new DepositHistoryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder&&mList.size()>9) {
            FooterViewHolder holder1 = (FooterViewHolder) holder;
            if (isLoadAll) {
                holder1.tvLoadMore.setVisibility(View.GONE);
                holder1.llIsAll.setVisibility(View.VISIBLE);
            } else {
                holder1.llIsAll.setVisibility(View.GONE);
                holder1.tvLoadMore.setVisibility(View.VISIBLE);
            }
            return;
        }
        if (holder instanceof DepositHistoryViewHolder) {
            DepositHistoryViewHolder mHolder = (DepositHistoryViewHolder) holder;
            final DepositHistoryBean.DataBean.DatasBean datasBean = mList.get(position);
            mHolder.tvAreaName.setText(datasBean.area_name);
            mHolder.tvAmount.setText(datasBean.money);
            mHolder.tvTime.setText(datasBean.change_date);
            mHolder.tvMoney.setText("当前押金："+datasBean.deposit);
            mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DepositDetailActivity.class);
                    intent.putExtra("id",datasBean.id);
                    mContext.startActivity(intent);
                }
            });
            if (position==mList.size()-1)
                mHolder.vLine.setVisibility(View.GONE);
            else
                mHolder.vLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() == position) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    static class DepositHistoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_deposit_history_list_area_name)
        TextView tvAreaName;
        @BindView(R.id.tv_item_deposit_history_list_amount)
        TextView tvAmount;
        @BindView(R.id.tv_item_deposit_history_list_time)
        TextView tvTime;
        @BindView(R.id.tv_item_deposit_history_list_money)
        TextView tvMoney;
        @BindView(R.id.v_line)
        View vLine;
        View itemView;
        DepositHistoryViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llIsAll;
        TextView tvLoadMore;

        FooterViewHolder(View view) {
            super(view);
            llIsAll = view.findViewById(R.id.ll_is_all);
            tvLoadMore = view.findViewById(R.id.tv_load_more);
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void setIsLoadAll(boolean b) {
        this.isLoadAll = b;
    }
}
