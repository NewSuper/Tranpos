package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.movie.activity.MovieSearchActivity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieNewRecommendEntity;


import java.util.ArrayList;
import java.util.List;

public class MovieRecommendViewAdapter extends RecyclerView.Adapter<MovieRecommendViewAdapter.MyViewHolder>{
    private Context context;
    private List<MovieNewRecommendEntity.DataBean.ItemsBean> items = new ArrayList<>();

    public MovieRecommendViewAdapter(Context context){
        this.context = context;
    }

    public void update(List<MovieNewRecommendEntity.DataBean.ItemsBean> items){
        this.items = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemView(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i));
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(ViewGroup parent) {super(parent);}
        public void setData(Object object) {}
    }

    class ItemView extends MyViewHolder implements View.OnClickListener {
        MovieNewRecommendEntity.DataBean.ItemsBean itemsBean;

        ImageView iv;
        TextView tv1;
        TextView tv2;

        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_recommend_view2, parent, false));
            iv = itemView.findViewById(R.id.iv);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);

            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            itemsBean = (MovieNewRecommendEntity.DataBean.ItemsBean) object;

            RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.emptystate_pic)
                        .error(R.mipmap.emptystate_pic)
//                    .skipMemoryCache(true)
                    .centerCrop()
                    .dontAnimate()
//                        .override(StringUtils.dip2px(context, 200), StringUtils.dip2px(context, 200))

                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(itemsBean.getThumbnail())
                    .apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iv);
            tv1.setText(itemsBean.getTitle());
            tv2.setText(itemsBean.getSubtitle());

        }

        @Override
        public void onClick(View v) {
            MovieSearchActivity.intentMe(context , itemsBean.getSearch_content() , null);

        }
    }

}
