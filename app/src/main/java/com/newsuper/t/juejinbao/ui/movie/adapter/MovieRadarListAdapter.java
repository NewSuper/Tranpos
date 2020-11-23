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
import com.juejinchain.android.module.movie.view.ScoreView;

import java.util.ArrayList;
import java.util.List;

public class MovieRadarListAdapter<T extends EasyAdapter.TypeBean> extends RecyclerView.Adapter<MovieRadarListAdapter.MyViewHolder>{
    private Context context;
    private List<T> items = new ArrayList<>();


    public MovieRadarListAdapter(Context context){
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
            case EasyAdapter.TypeBean.HEADER:
                return new HeaderView(viewGroup);
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

    class HeaderView extends MyViewHolder{
        private BeanMovieSearchItem beanMovieSearchItem;

        private ImageView iv;
        private TextView tv_title;
        private ScoreView srv;
        private TextView tv_point;
        private TextView tv_form;
        private TextView tv_actor;

        public HeaderView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_radar_header, parent, false));
            iv = itemView.findViewById(R.id.iv);
            tv_title = itemView.findViewById(R.id.tv_title);
            srv = itemView.findViewById(R.id.scv);
            tv_point = itemView.findViewById(R.id.tv_point);
            tv_form = itemView.findViewById(R.id.tv_form);
            tv_actor = itemView.findViewById(R.id.tv_actor);

        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            beanMovieSearchItem = (BeanMovieSearchItem) object;

            tv_title.setText( beanMovieSearchItem.getTitle());
            tv_form.setText("类型：" + beanMovieSearchItem.getForm());
            tv_actor.setText("演员：" + beanMovieSearchItem.getActor());

            try {
                tv_point.setText(beanMovieSearchItem.getRating() + "");
                srv.setRate((float) beanMovieSearchItem.getRating());
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
                    Glide.with(context).load(beanMovieSearchItem.getImg())
                            .apply(options)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(iv);
                }
            }catch (Exception e){
                Log.e("zy" , "glide_Exception");}
        }
    }

//    class ItemView extends MyViewHolder implements View.OnClickListener {
//
//        private BeanMovieSearchItem beanMovieSearchItem;
//
////        private ImageView iv;
//        private TextView tv_title;
//        private TextView tv_cinema;
//        private TextView tv_form;
////        private TextView tv_actor;
////        private TextView tv_intro;
////        private ImageView iv_level;
//
//        public ItemView(ViewGroup parent) {
//            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_radar_list2, parent, false));
////            iv = itemView.findViewById(R.id.iv);
//            tv_title = itemView.findViewById(R.id.tv_title);
//            tv_cinema = itemView.findViewById(R.id.tv_cinema);
//            tv_form = itemView.findViewById(R.id.tv_form);
////            tv_actor = itemView.findViewById(R.id.tv_actor);
////            tv_intro = itemView.findViewById(R.id.tv_intro);
////            iv_level = itemView.findViewById(R.id.iv_level);
//
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void setData(Object object) {
//            super.setData(object);
//            beanMovieSearchItem = (BeanMovieSearchItem) object;
//
//            tv_title.setText(Utils.getSearchTitle2(context, beanMovieSearchItem.getTitle(), beanMovieSearchItem.getKey()));
//            tv_cinema.setText("来源：" + beanMovieSearchItem.getCinema());
//            tv_form.setText("类型：" + beanMovieSearchItem.getForm());
////            tv_actor.setText("演员：" + beanMovieSearchItem.getActor());
////            tv_intro.setText("简介：" + beanMovieSearchItem.getIntro());
//
////            if(beanMovieSearchItem.getLevel() == 0){
////                iv_level.setVisibility(View.GONE);
////            }else{
////                iv_level.setVisibility(View.VISIBLE);
////            }
//
////            RequestOptions options = new RequestOptions()
////                    .placeholder(R.mipmap.emptystate_pic)
////                    .error(R.mipmap.emptystate_pic)
////                    .skipMemoryCache(true)
////                    .fitCenter().centerCrop()
////                    .dontAnimate()
////                    .diskCacheStrategy(DiskCacheStrategy.ALL);
////            try {
////                if(iv != null) {
////                    Glide.with(context).load(beanMovieSearchItem.getImg())
////                            .apply(options)
////                            .transition(DrawableTransitionOptions.withCrossFade())
////                            .into(iv);
////                }
////            }catch (Exception e){
////                Log.e("zy" , "glide_Exception");}
//        }
//
//        @Override
//        public void onClick(View v) {
////            BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_JKKDETAIL + "?path=" + beanMovieSearchItem.getLink());
//
//            MovieDetailActivity.intentMe(context , beanMovieSearchItem.getLink() ,null);
//        }
//
//    }

    class ItemView extends MyViewHolder implements View.OnClickListener {

        private BeanMovieSearchItem beanMovieSearchItem;

        private ImageView iv;
        private TextView tv_title;
        private TextView tv_cinema;
        private TextView tv_form;
        private TextView tv_actor;
        private TextView tv_intro;
        private ImageView iv_level;

        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_radar_list, parent, false));
            iv = itemView.findViewById(R.id.iv);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_cinema = itemView.findViewById(R.id.tv_cinema);
            tv_form = itemView.findViewById(R.id.tv_form);
            tv_actor = itemView.findViewById(R.id.tv_actor);
            tv_intro = itemView.findViewById(R.id.tv_intro);
            iv_level = itemView.findViewById(R.id.iv_level);

            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            beanMovieSearchItem = (BeanMovieSearchItem) object;

            tv_title.setText(Utils.getSearchTitle2(context, beanMovieSearchItem.getTitle(), beanMovieSearchItem.getKey()));
            tv_cinema.setText("来源：" + beanMovieSearchItem.getCinema());
            tv_form.setText("类型：" + beanMovieSearchItem.getForm());
            tv_actor.setText("演员：" + beanMovieSearchItem.getActor());
            tv_intro.setText("简介：" + beanMovieSearchItem.getIntro());

            if(beanMovieSearchItem.getLevel() == 0){
                iv_level.setVisibility(View.GONE);
            }else{
                iv_level.setVisibility(View.VISIBLE);
            }

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
//            BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_JKKDETAIL + "?path=" + beanMovieSearchItem.getLink());

            MovieDetailActivity.intentMe(context , beanMovieSearchItem.getLink() ,null);
        }

    }

}
