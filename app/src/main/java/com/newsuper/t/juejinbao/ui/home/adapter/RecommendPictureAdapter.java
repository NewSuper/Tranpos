package com.newsuper.t.juejinbao.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.entity.PictureContentEntity;
import com.newsuper.t.juejinbao.utils.RecyclerViewAdapterHelper;


import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class RecommendPictureAdapter extends RecyclerViewAdapterHelper<PictureContentEntity.DataBean> {
    private RecommendPictureItmeOnCLick recommendPictureItmeOnCLick;
    private Context context;
    private int height;
    private String textSizeLevel = "middle";

    public void setOnPictureItmeOnCLick(RecommendPictureItmeOnCLick recommendPictureItmeOnCLick) {
        this.recommendPictureItmeOnCLick = recommendPictureItmeOnCLick;
    }

    public RecommendPictureAdapter(Context context, List list, int height) {
        super(context, list);
        this.context = context;
        this.height = height;
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_recomment_picture, parent, false);
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
            ((MyViewholder) holder).textView.setText(mList.get(position).getTitle());
//            setTextSize(((MyViewholder) holder).textView);
            if (mList.get(position).getImg_url().size() > 0) {
                Glide.with(context).load(mList.get(position).getImg_url().get(0)).transition(withCrossFade()).into(((MyViewholder) holder).imageView);
            }
            ((MyViewholder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recommendPictureItmeOnCLick.onItemClick(position, mList.get(position).getId());
                }
            });
        }

    }

    public interface RecommendPictureItmeOnCLick {
        void onItemClick(int position, int id);
    }

    private class MyViewholder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private LinearLayout linearLayout;

        public MyViewholder(View view) {
            super(view);
            textView = view.findViewById(R.id.adapter_recomment_picture_title);
            imageView = view.findViewById(R.id.adapter_recomment_picture_img);
            linearLayout = view.findViewById(R.id.adapter_recomment_picture_item);
        }

    }
}
