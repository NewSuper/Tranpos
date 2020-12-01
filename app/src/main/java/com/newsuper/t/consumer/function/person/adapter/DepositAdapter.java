package com.newsuper.t.consumer.function.person.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.DepositBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public class DepositAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<DepositBean.DataBean> mList;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    private boolean isLoadAll = false;

    public DepositAdapter(Context mContext, ArrayList<DepositBean.DataBean> mList) {
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
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_deposit_list, parent, false);
            return new DepositViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder && mList.size() > 9) {
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
        if (holder instanceof DepositViewHolder) {
            DepositViewHolder mHolder = (DepositViewHolder) holder;
            mHolder.tvArea.setText(mList.get(position).area_name);
            mHolder.tvAmount.setText(mList.get(position).summoney+"");
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

    static class DepositViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_deposit_list_area)
        TextView tvArea;
        @BindView(R.id.tv_deposit_list_amount)
        TextView tvAmount;
        DepositViewHolder(View itemView) {
            super(itemView);
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
