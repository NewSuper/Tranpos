package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.CommentBean.Comment;
import com.newsuper.t.consumer.function.selectgoods.inter.OnPhotoClickListener;
import com.newsuper.t.consumer.widget.MultiImageViewLayout;
import com.newsuper.t.consumer.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Comment> commentList;
    private Context context;
    private boolean isLoadAll = false;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    private OnPhotoClickListener listener;

    public CommentsAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    public void setOnPhotoClickListener(OnPhotoClickListener listener){
        this.listener=listener;
    }


    @Override
    public int getItemViewType(int position) {
        if(getItemCount()- 1 == position){
            return TYPE_FOOTER;
        }else{
            return TYPE_NORMAL;
        }

    }
    public class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ll_is_all)
        LinearLayout llIsAll;
        @BindView(R.id.tv_load_more)
        TextView tvLoadMore;
        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public class NomalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_custom_name)
        TextView tvCustomName;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.tv_reply)
        TextView tvReply;
        @BindView(R.id.star_grade)
        RatingBar starGrade;
        @BindView(R.id.tv_comment_time)
        TextView tvCommentTime;
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.ll_show_tag)
        LinearLayout llShowTag;
        @BindView(R.id.tv_reply_time)
        TextView tv_reply_time;
        @BindView(R.id.ll_reply)
        LinearLayout ll_reply;

        @BindView(R.id.multi_image)
        MultiImageViewLayout multiImage;

        public NomalViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_FOOTER){
            View footView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_comments_footer_view, parent, false);
            return new FooterViewHolder(footView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_comment, parent, false);
            return new NomalViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FooterViewHolder) {
            FooterViewHolder holder1 = (FooterViewHolder) holder;
            if(position<6){
                holder1.tvLoadMore.setVisibility(View.GONE);
                holder1.llIsAll.setVisibility(View.GONE);
            }else{
                if (isLoadAll) {
                    holder1.tvLoadMore.setVisibility(View.GONE);
                    holder1.llIsAll.setVisibility(View.VISIBLE);
                } else {
                    holder1.llIsAll.setVisibility(View.GONE);
                    holder1.tvLoadMore.setVisibility(View.VISIBLE);
                }
            }

            return;
        }

        NomalViewHolder mholder = (NomalViewHolder) holder;
        final Comment comment = commentList.get(position);
        mholder.tvCustomName.setText(comment.lewaimai_customer_id);
        mholder.tvComment.setText(comment.content);
        if (!TextUtils.isEmpty(comment.creplycontent)) {
            mholder.ll_reply.setVisibility(View.VISIBLE);
            mholder.tvReply.setText(comment.creplycontent);
            mholder.tv_reply_time.setText(comment.time);
        }else {
            mholder.ll_reply.setVisibility(View.GONE);
        }
        mholder.starGrade.setStar(Float.parseFloat(comment.grade));
        mholder.tvCommentTime.setText(comment.time);
        if (null != comment.tag && comment.tag.size() > 0) {
            mholder.llShowTag.setVisibility(View.VISIBLE);
            String tag = "";
            for (int i = 0; i < comment.tag.size(); i++) {
                if (i != comment.tag.size() - 1) {
                    tag += comment.tag.get(i) + "，";
                } else {
                    tag += comment.tag.get(i);
                }
            }
            mholder.tvTag.setText(tag);
        } else {
            mholder.llShowTag.setVisibility(View.GONE);
        }
        mholder.multiImage.setList(comment.imgurl);

        mholder.multiImage.setOnItemClickListener(new MultiImageViewLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int PressImagePosition, float PressX, float PressY) {
                 if(null!=listener){
                     listener.onClickPhoto(PressImagePosition,comment.imgurl);
                 }
            }

            @Override
            public void onItemLongClick(View view, int PressImagePosition, float PressX, float PressY) {

            }
        });

    }
    public void setIsLoadAll(boolean b) {
        this.isLoadAll = b;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return commentList.size()+1;
    }

    public void updateComments(List<Comment> comments) {
        commentList.clear();
        if (null != comments) {
            commentList.addAll(comments);
        }
        notifyDataSetChanged();
    }

}
