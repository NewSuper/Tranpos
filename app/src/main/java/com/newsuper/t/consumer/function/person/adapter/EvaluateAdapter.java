package com.newsuper.t.consumer.function.person.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.EvaluateBean;
import com.xunjoy.lewaimai.consumer.function.person.activity.SignActivity;
import com.xunjoy.lewaimai.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.RatingBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by Administrator on 2019/5/5 0005
 */
public class EvaluateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<EvaluateBean.DataBean.CommentsBean> mList;
    private EvaluateOnClickListener listener;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    private boolean isLoadAll = false;

    public EvaluateAdapter(Context mContext, ArrayList<EvaluateBean.DataBean.CommentsBean> mList, EvaluateOnClickListener listener) {
        super();
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View footView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_comments_footer_view, parent, false);
            FooterViewHolder footerViewHolder =  new FooterViewHolder(footView);
            return footerViewHolder;
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_list, parent, false);
            return new EvaluateViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
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

        if (holder instanceof EvaluateViewHolder) {
            EvaluateViewHolder mHolder = (EvaluateViewHolder) holder;
            final EvaluateBean.DataBean.CommentsBean bean = mList.get(position);
            mHolder.tvTitleName.setText(bean.shopname);
            mHolder.tvName.setText(bean.customer_name);
            mHolder.ratingBar.setStar(!StringUtils.isEmpty(bean.grade) ? Float.parseFloat(bean.grade) : 0f);
            mHolder.tvContent.setText(bean.content);
            mHolder.tvTime.setText(bean.time);
            if (null != bean.imgurl && bean.imgurl.size() > 0) {
                switch (bean.imgurl.size()) {
                    case 1:
                        mHolder.llImgOne.setVisibility(View.VISIBLE);
                        mHolder.llImgTwo.setVisibility(View.GONE);
                        loadImage(bean.imgurl.get(0), mHolder.ivOne);
                        break;
                    case 2:
                        mHolder.llImgOne.setVisibility(View.VISIBLE);
                        mHolder.llImgTwo.setVisibility(View.GONE);
                        loadImage(bean.imgurl.get(0), mHolder.ivOne);
                        loadImage(bean.imgurl.get(1), mHolder.ivTwo);
                        break;
                    case 3:
                        mHolder.llImgOne.setVisibility(View.VISIBLE);
                        mHolder.llImgTwo.setVisibility(View.VISIBLE);
                        loadImage(bean.imgurl.get(0), mHolder.ivOne);
                        loadImage(bean.imgurl.get(1), mHolder.ivTwo);
                        loadImage(bean.imgurl.get(2), mHolder.ivThree);
                        break;
                    case 4:
                        mHolder.llImgOne.setVisibility(View.VISIBLE);
                        mHolder.llImgTwo.setVisibility(View.VISIBLE);
                        loadImage(bean.imgurl.get(0), mHolder.ivOne);
                        loadImage(bean.imgurl.get(1), mHolder.ivTwo);
                        loadImage(bean.imgurl.get(2), mHolder.ivThree);
                        loadImage(bean.imgurl.get(3), mHolder.ivFour);
                        break;
                }
            } else {
                mHolder.llImgOne.setVisibility(View.GONE);
                mHolder.llImgTwo.setVisibility(View.GONE);
            }
            if (!StringUtils.isEmpty(bean.creplycontent)) {
                mHolder.llMerchantReply.setVisibility(View.VISIBLE);
                mHolder.tvReplyContent.setText(bean.creplycontent);
                mHolder.tvReplyTime.setText(bean.creplytime);
            } else {
                mHolder.llMerchantReply.setVisibility(View.GONE);
            }
            mHolder.llTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SelectGoodsActivity3.class);
                    intent.putExtra("from_page", "order_list");
                    intent.putExtra("shop_id", bean.shop_id);
                    mContext.startActivity(intent);
                }
            });
            mHolder.llShareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = RetrofitManager.BASE_URL_H5 + "h5/lwm/waimai/mine-comment-info?admin_id=" + SharedPreferencesUtil.getAdminId()
                            + "&comment_id=" + bean.comment_id;
                    String shopImageUrl = bean.shopimage;
                    if (!shopImageUrl.startsWith("http")) {
                        shopImageUrl = RetrofitManager.BASE_IMG_URL + url;
                    }
                    listener.shareClick(url, bean.content, shopImageUrl);
                }
            });
            mHolder.llDelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.delClick(bean.comment_id);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 1 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() - 1 == position) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    private void loadImage(String url, ImageView imageView) {
        if (!url.startsWith("http")) {
            url = RetrofitManager.BASE_IMG_URL + url;
        }
        //加载网络图片
        UIUtils.glideAppLoad(mContext, url, imageView);
    }

    static class EvaluateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_evaluate_shop_title_name)
        TextView tvTitleName;
        @BindView(R.id.ll_item_evaluate_title)
        LinearLayout llTitle;
        @BindView(R.id.tv_item_evaluate_name)
        TextView tvName;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.tv_item_evaluate_content)
        TextView tvContent;
        @BindView(R.id.tv_item_evaluate_time)
        TextView tvTime;
        @BindView(R.id.iv_item_evaluate_one)
        ImageView ivOne;
        @BindView(R.id.iv_item_evaluate_two)
        ImageView ivTwo;
        @BindView(R.id.ll_item_evaluate_img_one)
        LinearLayout llImgOne;
        @BindView(R.id.iv_item_evaluate_three)
        ImageView ivThree;
        @BindView(R.id.iv_item_evaluate_four)
        ImageView ivFour;
        @BindView(R.id.ll_item_evaluate_img_two)
        LinearLayout llImgTwo;
        @BindView(R.id.tv_item_evaluate_merchant_reply_content)
        TextView tvReplyContent;
        @BindView(R.id.tv_item_evaluate_merchant_reply_time)
        TextView tvReplyTime;
        @BindView(R.id.ll_item_evaluate_merchant_reply)
        LinearLayout llMerchantReply;
        @BindView(R.id.ll_item_evaluate_share_btn)
        LinearLayout llShareBtn;
        @BindView(R.id.ll_item_evaluate_del_btn)
        LinearLayout llDelBtn;
        View itemView;

        EvaluateViewHolder(View itemView) {
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
        notifyDataSetChanged();
    }

    public interface EvaluateOnClickListener {
        void shareClick(String url, String content, String shopImg);

        void delClick(String commentId);
    }
}
