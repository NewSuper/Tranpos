package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.juejinchain.android.R;
import com.juejinchain.android.module.movie.activity.MovieDetailActivity;
import com.juejinchain.android.module.movie.craw.BeanMovieSearchItem;
import com.juejinchain.android.module.movie.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MovieRadarGridAdapter<T extends EasyAdapter.TypeBean> extends RecyclerView.Adapter<MovieRadarGridAdapter.MyViewHolder>{
    private Context context;
    private List<T> items = new ArrayList<>();


    public MovieRadarGridAdapter(Context context){
        this.context = context;
    }


    public void update(List<T> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void add(){
//        this.items.add(t);
        notifyItemInserted(this.items.size() - 1);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case EasyAdapter.TypeBean.ITEM:
                return new ItemView(viewGroup);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getUiType();
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
        private BeanMovieSearchItem beanMovieSearchItem;

        private ImageView iv;
        private TextView tv_name;
        private TextView tv_detail;

        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_radar_grid, parent, false));
            iv = itemView.findViewById(R.id.iv);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_detail = itemView.findViewById(R.id.tv_detail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            beanMovieSearchItem = (BeanMovieSearchItem) object;

            tv_name.setText(Utils.getSearchTitle2(context, beanMovieSearchItem.getTitle(), beanMovieSearchItem.getKey()));
            tv_detail.setText("演员：" + beanMovieSearchItem.getActor());

            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.emptystate_pic)
                    .error(R.mipmap.emptystate_pic)
                    .skipMemoryCache(true)
                    .fitCenter().centerCrop()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            try {
                if(iv != null) {
                    Glide.with(context).load(beanMovieSearchItem.getImg())
                            .apply(options)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(iv);
                }
            }catch (Exception e){
                Log.e("zy" , "glide_Exception");}

        }


        @Override
        public void onClick(View v) {
            MovieDetailActivity.intentMe(context , beanMovieSearchItem.getLink() , null);
        }
    }

}
