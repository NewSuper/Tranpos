package com.newsuper.t.juejinbao.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.entity.PictureContentEntity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.RecyclerViewAdapterHelper;
import com.newsuper.t.juejinbao.view.RoundImageView;


import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PictureCountenAdapter extends RecyclerViewAdapterHelper<PictureContentEntity.DataBean> {
    private Context context;
    private PictureItmeOnCLick pictureItmeOnCLick;
    private String textSizeLevel = "middle";

    public PictureCountenAdapter(Context context, List list) {
        super(context, list);
        this.context = context;
    }

    public void setOnPictureItmeOnCLick(PictureItmeOnCLick pictureItmeOnCLick) {
        this.pictureItmeOnCLick = pictureItmeOnCLick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_picture_content, parent, false);
        return new MyViewholder(view);
    }

    public void setTextSizeLevel(String level) {
        textSizeLevel = level;
        notifyDataSetChanged();
    }

    private void setTextSize(TextView content) {
        switch (textSizeLevel) {
            case "small":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_small));
                break;
            case "middle":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_middle));
                break;
            case "big":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_big));
                break;
            case "large":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_lager));
                break;
        }
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewholder) {
            ((MyViewholder) holder).tvTitle.setText(mList.get(position).getTitle());
            setTextSize(((MyViewholder) holder).tvTitle);
            ((MyViewholder) holder).tvAuthor.setText(mList.get(position).getAuthor());
            //是否是最热
            if (TextUtils.isEmpty(mList.get(position).getMark())) {
                ((MyViewholder) holder).tvHot.setVisibility(View.GONE);
                if (mList.get(position).getRead_count() > 10000 && mList.get(position).getRead_count() < 100000) {
                    ((MyViewholder) holder).tvRead.setText(BigDecimal.valueOf(mList.get(position).getRead_count() / 10000).setScale(1, BigDecimal.ROUND_HALF_EVEN).doubleValue() + "w+ 次评论");
                } else if (mList.get(position).getRead_count() > 100000) {
                    ((MyViewholder) holder).tvRead.setText(BigDecimal.valueOf(mList.get(position).getRead_count() / 10000).intValue() + "w+ 评论");
                } else {
                    ((MyViewholder) holder).tvRead.setText(BigDecimal.valueOf(mList.get(position).getRead_count()).intValue() + " 评论");
                }


            } else {
                ((MyViewholder) holder).tvHot.setVisibility(View.VISIBLE);
                if (mList.get(position).getComment_count() > 10000 && mList.get(position).getComment_count() < 100000) {
                    ((MyViewholder) holder).tvRead.setText("阅读 " + BigDecimal.valueOf(mList.get(position).getComment_count() / 10000).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() + "w+");
                } else if (mList.get(position).getComment_count() > 100000) {
                    ((MyViewholder) holder).tvRead.setText("阅读 " + BigDecimal.valueOf(mList.get(position).getComment_count() / 10000).intValue() + "w+");
                } else {
                    ((MyViewholder) holder).tvRead.setText("阅读 " + BigDecimal.valueOf(mList.get(position).getComment_count()).intValue());
                }
            }

            ((MyViewholder) holder).tvTime.setText(Utils.experienceTime(mList.get(position).getPublish_time()));

            switch (mList.get(position).getShowtype()) {
                //3张图
                case 2:
                    ((MyViewholder) holder).llTypeImage.setVisibility(View.VISIBLE);
                    ((MyViewholder) holder).rlTypeImage.setVisibility(View.GONE);
                    Glide.with(context).load(mList.get(position).getImg_url().size() <= 0 ? "" : mList.get(position).getImg_url().get(0)).apply(new RequestOptions().placeholder(R.mipmap.default_img)).transition(withCrossFade()).into(((MyViewholder) holder).ivOne);
                    Glide.with(context).load(mList.get(position).getImg_url().size() <= 1 ? "" : mList.get(position).getImg_url().get(1)).apply(new RequestOptions().placeholder(R.mipmap.default_img)).transition(withCrossFade()).into(((MyViewholder) holder).ivTwo);
                    Glide.with(context).load(mList.get(position).getImg_url().size() <= 2 ? "" : mList.get(position).getImg_url().get(2)).apply(new RequestOptions().placeholder(R.mipmap.default_img)).transition(withCrossFade()).into(((MyViewholder) holder).ivThree);
                    ((MyViewholder) holder).tvImageNum.setText(mList.get(position).getOther().getImg_count() + "图");
                    break;
                //大图
                case 3:
                    ((MyViewholder) holder).llTypeImage.setVisibility(View.GONE);
                    ((MyViewholder) holder).rlTypeImage.setVisibility(View.VISIBLE);

                    Glide.with(context).load(mList.get(position).getImg_url().size() == 0 ? "" : mList.get(position).getImg_url().get(0)).apply(new RequestOptions().placeholder(R.mipmap.default_img)).transition(withCrossFade()).into(((MyViewholder) holder).ivBig);

                    ((MyViewholder) holder).tvBigNum.setText(mList.get(position).getOther().getImg_count() + "图");
                    break;
                default:
                    break;
            }

            ((MyViewholder) holder).llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pictureItmeOnCLick.onItemClick(position, mList.get(position).getId());
                }
            });

        }
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_picture_content_title)
        TextView tvTitle;
        @BindView(R.id.adapter_picture_content_type_one)
        LinearLayout llTypeImage;
        @BindView(R.id.adapter_picture_content_img_one)
        RoundImageView ivOne;
        @BindView(R.id.adapter_picture_content_img_two)
        RoundImageView ivTwo;
        @BindView(R.id.adapter_picture_content_img_three)
        RoundImageView ivThree;
        @BindView(R.id.adapter_picture_content_img_three_num)
        TextView tvImageNum;
        @BindView(R.id.adapter_picture_content_type)
        RelativeLayout rlTypeImage;
        @BindView(R.id.adapter_picture_content_type_two)
        RoundImageView ivBig;
        @BindView(R.id.adapter_picture_content_img_num)
        TextView tvBigNum;
        @BindView(R.id.adapter_picture_content_hot)
        TextView tvHot;
        @BindView(R.id.adapter_picture_content_author)
        TextView tvAuthor;
        @BindView(R.id.adapter_picture_content_red)
        TextView tvRead;
        @BindView(R.id.adapter_picture_content_time)
        TextView tvTime;
        @BindView(R.id.adapter_picture_content_item)
        LinearLayout llItem;

        public MyViewholder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public interface PictureItmeOnCLick {
        void onItemClick(int position, int id);
    }
}
