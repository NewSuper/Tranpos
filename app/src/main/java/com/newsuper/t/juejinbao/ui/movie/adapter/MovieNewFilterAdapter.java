package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.tablayout.SlidingTabLayout;
import com.juejinchain.android.R;
import com.juejinchain.android.module.movie.activity.MovieSearchActivity;
import com.juejinchain.android.module.movie.craw.BeanMovieSearchItem;
import com.juejinchain.android.module.movie.entity.MovieIndexRecommendEntity;
import com.juejinchain.android.module.movie.entity.MovieNewFilterEntity;
import com.juejinchain.android.module.movie.fragment.MovieNewTabFragment;
import com.juejinchain.android.module.movie.view.ScoreView;

import java.util.ArrayList;
import java.util.List;

public class MovieNewFilterAdapter<T extends EasyAdapter.TypeBean> extends RecyclerView.Adapter<MovieNewFilterAdapter.MyViewHolder> {
    private Context context;
    private List<MovieNewFilterEntity.DataBeanX.DataBean > items = new ArrayList<>();



    public MovieNewFilterAdapter(Context context) {
        this.context = context;
    }


    public void update( List<MovieNewFilterEntity.DataBeanX.DataBean > items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void add() {
//        this.items.add(t);
        notifyItemInserted(this.items.size() - 1);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case EasyAdapter.TypeBean.ITEM:
                return new ItemView(viewGroup);
            case EasyAdapter.TypeBean.FOOTER:
                return new FooterView(viewGroup);
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
        public MyViewHolder(ViewGroup parent) {
            super(parent);
        }

        public void setData(Object object) {
        }
    }

    class ItemView extends MyViewHolder implements View.OnClickListener {
        private MovieNewFilterEntity.DataBeanX.DataBean  dataBean;

        private ImageView iv;
        private TextView tv_title;
        private ScoreView scv;
        private TextView tv_point;
        private TextView tv_info;
        private RelativeLayout ll;


        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_newfilter_item, parent, false));
            iv = itemView.findViewById(R.id.iv);
            tv_title = itemView.findViewById(R.id.tv_title);
            scv = itemView.findViewById(R.id.scv);
            tv_point = itemView.findViewById(R.id.tv_point);
            tv_info = itemView.findViewById(R.id.tv_info);
            ll = itemView.findViewById(R.id.ll);

            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            dataBean = (MovieNewFilterEntity.DataBeanX.DataBean ) object;

            tv_title.setText(dataBean.getVod_title());
            try {
                float f = (float) Double.parseDouble(dataBean.getVod_score());
                scv.setRate(f);
                tv_point.setText(dataBean.getVod_score());
                ll.setVisibility(View.VISIBLE);
                if(f <= 0){
                    ll.setVisibility(View.GONE);
                    tv_point.setText("暂无评分");
                }
            }catch (Exception e){e.printStackTrace();}

            tv_info.setText(dataBean.getVod_subtitle());

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

    class FooterView extends MyViewHolder {

        public FooterView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_movie_recommend_footer, parent, false));
        }
    }

}
