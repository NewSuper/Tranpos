package com.newsuper.t.juejinbao.ui.home.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.entity.TabChangeEvent;
import com.newsuper.t.juejinbao.utils.RecyclerViewAdapterHelper;
import com.newsuper.t.juejinbao.utils.ScreenUtils;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MicroVideoListAdapter extends RecyclerViewAdapterHelper<HomeListEntity.DataBean.OtherBean.SmallvideoListBean> {
    private Context context;
    private VideoItenOnclick videoItenOnclick;
    private int type = 0;
    private String textSizeLevel = "middle";

    public MicroVideoListAdapter(Context context, List list) {
        super(context, list);
        this.context = context;
    }

    public void setVideoItenOnclick(VideoItenOnclick videoItenOnclick) {
        this.videoItenOnclick = videoItenOnclick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.model_adapter_micro_video_list, parent, false);
        return new MyViewHolder(view);
    }

    public void setTextSizeLevel(String level) {
        textSizeLevel = level;
        notifyDataSetChanged();
    }

    private void setTextSize(TextView content) {
        switch (textSizeLevel) {
            case "small":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.content_small));
                break;
            case "middle":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.content_middle));
                break;
            case "big":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.content_big));
                break;
            case "large":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.content_lager));
                break;
        }
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {

            if (type == 1) {
                int screenWidth = ScreenUtils.getScreenWidth(context);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)context.getResources().getDimension(R.dimen.ws249dp),(int)context.getResources().getDimension(R.dimen.ws300dp));
//                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) (screenWidth * 0.75f), 972);
                layoutParams.setMargins(10, 0, 10, 0);
                holder.itemView.setLayoutParams(layoutParams);
                ((MyViewHolder) holder).tvHomeName.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).rlBottom.setVisibility(View.GONE);

                ((MyViewHolder) holder).tvHomeName.setText("#" + mList.get(position).getDescription());
                setTextSize(((MyViewHolder) holder).tvHomeName);

                if (position == mList.size() - 1) {
                    ((MyViewHolder) holder).rlMore.setVisibility(View.VISIBLE);
                } else {
                    ((MyViewHolder) holder).rlMore.setVisibility(View.GONE);
                }
                //查看更多
                ((MyViewHolder) holder).rlMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventBus.getDefault().post(new TabChangeEvent(11));
                    }
                });

                ((MyViewHolder) holder).ivRemove.setVisibility(View.GONE);
            }

            Glide.with(context).
                    load(mList.get(position).getImg_url().get(0)).transition(withCrossFade()).

                    into(((MyViewHolder) holder).ivVideoImg);
            ((MyViewHolder) holder).tvAuthorName.setText("#" + mList.get(position).getAuthor());
            ((MyViewHolder) holder).tvContent.setText(mList.get(position).getDescription());
            setTextSize(((MyViewHolder) holder).tvContent);

            if (mList.get(position).getRead_count() > 10000 && mList.get(position).getRead_count() < 100000) {
                ((MyViewHolder) holder).tvPlayNumber.setText(BigDecimal.valueOf(mList.get(position).getRead_count() / 10000).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() + "万次播放");
            } else if (mList.get(position).getRead_count() > 100000) {
                ((MyViewHolder) holder).tvPlayNumber.setText(BigDecimal.valueOf(mList.get(position).getRead_count() / 10000).intValue() + "万次播放");
            } else {
                ((MyViewHolder) holder).tvPlayNumber.setText(BigDecimal.valueOf(mList.get(position).getRead_count()).intValue() + "次播放");
            }
            if (mList.get(position).getDigg_count() > 10000 && mList.get(position).getDigg_count() < 100000) {
                ((MyViewHolder) holder).tvGoodNumber.setText(BigDecimal.valueOf(mList.get(position).getDigg_count() / 10000).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString() + "万次点赞");
            } else if (mList.get(position).getDigg_count() > 100000) {
                ((MyViewHolder) holder).tvGoodNumber.setText(BigDecimal.valueOf(mList.get(position).getDigg_count() / 10000).intValue() + "万次点赞");
            } else {
                ((MyViewHolder) holder).tvGoodNumber.setText(BigDecimal.valueOf(mList.get(position).getDigg_count()).intValue() + "次赞");
            }
            ((MyViewHolder) holder).ivVideoImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (videoItenOnclick != null) {
                        videoItenOnclick.onClick(position, view);
                    }
                }
            });
            ((MyViewHolder) holder).ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   videoItenOnclick.onRemove(position);
                }
            });
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_model_home_micro_video_img)
        ImageView ivVideoImg;
        @BindView(R.id.model_adapter_micro_video_list_name)
        TextView tvAuthorName;
        @BindView(R.id.model_adapter_micro_video_list_content)
        TextView tvContent;
        @BindView(R.id.model_adapter_micro_video_list_play_number)
        TextView tvPlayNumber;
        @BindView(R.id.model_adapter_micro_video_list_good_number)
        TextView tvGoodNumber;
        @BindView(R.id.model_adapter_video_list_more)
        RelativeLayout rlMore;
        @BindView(R.id.model_adapter_micro_video_home_name)
        TextView tvHomeName;
        @BindView(R.id.model_adapter_micro_video_bottom)
        RelativeLayout rlBottom;
        @BindView(R.id.remove_video)
        ImageView ivRemove;
        private final SparseArray<View> views;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.views = new SparseArray<>();
        }

        public <T extends View> T getView(@IdRes int viewId) {
            View view = views.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (T) view;
        }
    }

    public  interface VideoItenOnclick {
        void onClick(int position, View view);

        void onRemove(int position);
    }


}
