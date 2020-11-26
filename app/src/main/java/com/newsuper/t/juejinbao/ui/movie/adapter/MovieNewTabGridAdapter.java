package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.movie.activity.MovieSearchActivity;
import com.newsuper.t.juejinbao.ui.movie.craw.BeanMovieSearchItem;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieIndexRecommendEntity;
import com.newsuper.t.juejinbao.ui.movie.view.ScoreView;

import java.util.ArrayList;
import java.util.List;

public class MovieNewTabGridAdapter<T extends EasyAdapter.TypeBean> extends RecyclerView.Adapter<MovieNewTabGridAdapter.MyViewHolder>{
    private Context context;
    private List<T> items = new ArrayList<>();


    public MovieNewTabGridAdapter(Context context){
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
        private MovieIndexRecommendEntity.DataBeanX.HotPlayBean.ListsBean dataBean;

        private ImageView iv;
        private TextView tv_name;
//        private TextView tv_detail;
        private ScoreView scv;
        private TextView tv_point;
        private RelativeLayout rl_score;

        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_newtab_grid, parent, false));
            iv = itemView.findViewById(R.id.iv);
            tv_name = itemView.findViewById(R.id.tv_name);
//            tv_detail = itemView.findViewById(R.id.tv_detail);

            scv = itemView.findViewById(R.id.scv);
            tv_point = itemView.findViewById(R.id.tv_point);
            rl_score = itemView.findViewById(R.id.rl_score);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            dataBean = (MovieIndexRecommendEntity.DataBeanX.HotPlayBean.ListsBean) object;

            tv_name.setText(dataBean.getVod_title());
//            tv_detail.setText("演员：" + beanMovieSearchItem.getVod_actor());
            try {
                float f = (float) Double.parseDouble(dataBean.getVod_score());
                tv_point.setText(dataBean.getVod_score());
                rl_score.setVisibility(View.VISIBLE);
                scv.setRate(f);

                if(f == 0){
                    rl_score.setVisibility(View.GONE);
                    tv_point.setText("暂无评分");
                }

            }catch (Exception e){e.printStackTrace();}


            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.emptystate_pic)
                    .error(R.mipmap.emptystate_pic)
                    .skipMemoryCache(true)
                    .fitCenter().centerCrop()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            try {
                if(iv != null) {
                    Glide.with(context).load(dataBean.getVod_pic())
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
            beanMovieSearchItem.setImg(dataBean.getVod_pic());
            beanMovieSearchItem.setForm(dataBean.getVod_tag());
            beanMovieSearchItem.setActor(dataBean.getVod_actor());
            beanMovieSearchItem.setTitle(dataBean.getVod_title());
            try {
                beanMovieSearchItem.setRating(Double.parseDouble(dataBean.getVod_score()));
            }catch (Exception e){
                e.printStackTrace();
            }
            MovieSearchActivity.intentMe(context , beanMovieSearchItem.getTitle() , beanMovieSearchItem);

        }
    }

}
