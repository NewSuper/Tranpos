package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
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
import com.juejinchain.android.databinding.ViewMovienewtabRankBinding;
import com.juejinchain.android.module.movie.activity.MovieNewTabRankActivity;
import com.juejinchain.android.module.movie.activity.MovieSearchActivity;
import com.juejinchain.android.module.movie.craw.BeanMovieSearchItem;
import com.juejinchain.android.module.movie.entity.MovieIndexRecommendEntity;
import com.juejinchain.android.module.movie.entity.V2PlayListEntity;
import com.juejinchain.android.module.movie.view.ScoreView;

import java.util.ArrayList;
import java.util.List;

public class MovieNewTabAndListAdapter extends RecyclerView.Adapter<MovieNewTabAndListAdapter.MyViewHolder>{
    private Context context;
    private List<V2PlayListEntity.DataBean.TvsBean> items = new ArrayList<>();


    public MovieNewTabAndListAdapter(Context context){
        this.context = context;
    }


    public void update(List<V2PlayListEntity.DataBean.TvsBean> items){
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
        myViewHolder.setData(items.get(i) , i);
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(ViewGroup parent) {super(parent);}
        public void setData(Object object , int position) {}
    }

    class ItemView extends MyViewHolder implements View.OnClickListener {
        private V2PlayListEntity.DataBean.TvsBean dataBean;

        private ImageView iv;
        private TextView tv_title;
        private ScoreView scv;
        private TextView tv_point;
        private TextView tv_info;


        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_newtab_item, parent, false));
            iv = itemView.findViewById(R.id.iv);
            tv_title = itemView.findViewById(R.id.tv_title);
            scv = itemView.findViewById(R.id.scv);
            tv_point = itemView.findViewById(R.id.tv_point);
            tv_info = itemView.findViewById(R.id.tv_info);

            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object , int position) {
            super.setData(object , position);
            dataBean = (V2PlayListEntity.DataBean.TvsBean) object;

            tv_title.setText(dataBean.getTitle());
            try {
                scv.setRate((float) dataBean.getRating().getValue());
            }catch (Exception e){e.printStackTrace();}
            tv_point.setText(dataBean.getRating().getValue() + "");
            tv_info.setText(dataBean.getCard_subtitle());

            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.emptystate_pic)
                    .error(R.mipmap.emptystate_pic)
                    .skipMemoryCache(true)
                    .fitCenter().centerCrop()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            try {
                if(iv != null) {
                    Glide.with(context).load(dataBean.getPic().getNormal())
                            .apply(options)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(iv);
                }
            }catch (Exception e){
                Log.e("zy" , "glide_Exception");}
        }

        @Override
        public void onClick(View v) {
            BeanMovieSearchItem beanMovieSearchItem = new BeanMovieSearchItem();
            beanMovieSearchItem.setImg(dataBean.getPic().getNormal());
            beanMovieSearchItem.setForm("暂无数据");
            beanMovieSearchItem.setActor("暂无数据");
            beanMovieSearchItem.setTitle(dataBean.getTitle());
            try {
                beanMovieSearchItem.setRating(dataBean.getRating().getValue());
            }catch (Exception e){
                e.printStackTrace();
            }
            MovieSearchActivity.intentMe(context , beanMovieSearchItem.getTitle() , beanMovieSearchItem);
        }

    }


}
