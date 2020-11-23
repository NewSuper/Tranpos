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
import com.juejinchain.android.module.movie.activity.MovieCartonHotActivity;
import com.juejinchain.android.module.movie.activity.MovieDetailActivity;
import com.juejinchain.android.module.movie.activity.MovieMovieHotActivity;
import com.juejinchain.android.module.movie.activity.MovieNewFilterActivity;
import com.juejinchain.android.module.movie.activity.MovieSearchActivity;
import com.juejinchain.android.module.movie.activity.MovieShowHotActivity;
import com.juejinchain.android.module.movie.activity.MovieShownSoonActivity;
import com.juejinchain.android.module.movie.activity.MovieTVHotActivity;
import com.juejinchain.android.module.movie.activity.MovieTabAndListActivity;
import com.juejinchain.android.module.movie.craw.BeanMovieSearchItem;
import com.juejinchain.android.module.movie.entity.MovieIndexRecommendEntity;
import com.juejinchain.android.module.movie.fragment.MovieNewTabFragment;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.module.movie.view.ScoreView;
import com.juejinchain.android.module.my.presenter.PrivacyPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * vip - 影视 - 控制子tab的适配器
 * @param <T>
 */
public class MovieNewTabListAdapter<T extends EasyAdapter.TypeBean> extends RecyclerView.Adapter<MovieNewTabListAdapter.MyViewHolder> {
    private MovieNewTabFragment movieNewTabFragment;
    private List<MovieIndexRecommendEntity.DataBeanX.RecommendBean.DataBean> items = new ArrayList<>();

    private MovieIndexRecommendEntity.DataBeanX dataBeanX;


    public MovieNewTabListAdapter(MovieNewTabFragment movieNewTabFragment) {
        this.movieNewTabFragment = movieNewTabFragment;
    }


    public void update(MovieIndexRecommendEntity.DataBeanX dataBeanX, List<MovieIndexRecommendEntity.DataBeanX.RecommendBean.DataBean> items) {
        this.dataBeanX = dataBeanX;
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
        public MyViewHolder(ViewGroup parent) {
            super(parent);
        }

        public void setData(Object object) {
        }
    }

    class HeaderView extends MyViewHolder {
        private PagerFragmentTabAdapter pagerFragmentTabAdapter;

        private MovieNewTabRankAdapter movieNewTabRankAdapter;
        private RecyclerView rv_rank;

        private SlidingTabLayout stb;
        private ViewPager vp;

        private TextView tv_1;
        private TextView tv_2;
        private TextView tv_3;

        private LinearLayout ll;
        private RelativeLayout rl_zdy;
        private RelativeLayout rl_jjsy;
        private RelativeLayout rl_zds;
        private RelativeLayout rl_bcsjb;
        private TextView tv_more;


        public HeaderView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(movieNewTabFragment.getContext()).inflate(R.layout.item_movie_newtab_header, parent, false));
            stb = itemView.findViewById(R.id.stb);
            vp = itemView.findViewById(R.id.vp);
            rv_rank = itemView.findViewById(R.id.rv_rank);
            tv_1 = itemView.findViewById(R.id.tv_1);
            tv_2 = itemView.findViewById(R.id.tv_2);
            tv_3 = itemView.findViewById(R.id.tv_3);

            ll = itemView.findViewById(R.id.ll);
            rl_zdy = itemView.findViewById(R.id.rl_zdy);
            rl_jjsy = itemView.findViewById(R.id.rl_jjsy);
            rl_zds = itemView.findViewById(R.id.rl_zds);
            rl_bcsjb = itemView.findViewById(R.id.rl_bcsjb);
            tv_more = itemView.findViewById(R.id.tv_more);

            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //((AutoHeightViewPager)viewPager).resetHeight(position);
                }

                @Override
                public void onPageSelected(int position) {
                    selectUI(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(movieNewTabFragment.type .equals("电影")){
                        MovieMovieHotActivity.intentMe(movieNewTabFragment.getActivity());
                    }else if(movieNewTabFragment.type .equals("电视剧")){
                        MovieTVHotActivity.intentMe(movieNewTabFragment.getActivity());
                    }else if(movieNewTabFragment.type .equals("综艺")){
                        MovieShowHotActivity.intentMe(movieNewTabFragment.getActivity());
                    }else if(movieNewTabFragment.type .equals("动漫")){
                        MovieCartonHotActivity.intentMe(movieNewTabFragment.getActivity());
                    }
//                    MovieTabAndListActivity.intentMe(movieNewTabFragment.getContext());
                }
            });

            rl_bcsjb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieTabAndListActivity.intentMe(movieNewTabFragment.getActivity());
                }
            });

            rl_jjsy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieShownSoonActivity.intentMe(movieNewTabFragment.getActivity());
                }
            });

            rl_zdy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieNewFilterActivity.intentMe(movieNewTabFragment.getActivity() , "电影");
                }
            });

            rl_zds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieNewFilterActivity.intentMe(movieNewTabFragment.getActivity() , "电视剧");

                }
            });

        }

        @Override
        public void setData(Object object) {
            super.setData(object);


            if (pagerFragmentTabAdapter == null) {
                pagerFragmentTabAdapter = new PagerFragmentTabAdapter(movieNewTabFragment.getChildFragmentManager(), dataBeanX.getHot_play());
                vp.setAdapter(pagerFragmentTabAdapter);
                pagerFragmentTabAdapter.notifyDataSetChanged();
                stb.setViewPager(vp);
            }


            if(movieNewTabRankAdapter == null) {
                LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(movieNewTabFragment.getContext());
                linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
                rv_rank.setLayoutManager(linearLayoutManager3);
                //列表适配器
                rv_rank.setAdapter(movieNewTabRankAdapter = new MovieNewTabRankAdapter(movieNewTabFragment.getContext()));
                movieNewTabRankAdapter.update(dataBeanX.getRank());
            }

            try {
                if (dataBeanX.getHot_play().size() > 1) {
                    stb.setVisibility(View.VISIBLE);
                } else {
                    stb.setVisibility(View.GONE);
                }
            }catch (Exception e){e.printStackTrace();}

            if(movieNewTabFragment.type .equals("电影")){
                ll.setVisibility(View.VISIBLE);
                rl_zdy.setVisibility(View.VISIBLE);
                rl_jjsy.setVisibility(View.VISIBLE);
                rl_zds.setVisibility(View.GONE);
                rl_bcsjb.setVisibility(View.GONE);
                tv_1.setText("影院热映");
                tv_2.setText("电影排行榜");
                tv_3.setText("热门电影推荐");
            }else if(movieNewTabFragment.type  .equals( "电视剧")){
                rl_zdy.setVisibility(View.GONE);
                rl_jjsy.setVisibility(View.GONE);
                rl_zds.setVisibility(View.VISIBLE);
                rl_bcsjb.setVisibility(View.VISIBLE);
                ll.setVisibility(View.VISIBLE);
                tv_1.setText("热播新剧");
                tv_2.setText("电视榜单");
                tv_3.setText("为你推荐");
                stb.setVisibility(View.VISIBLE);
            }else if(movieNewTabFragment.type  .equals( "综艺")){
                ll.setVisibility(View.GONE);
                tv_1.setText("热播综艺");
                tv_2.setText("一周综艺榜单");
                tv_3.setText("热门综艺推荐");
                stb.setVisibility(View.VISIBLE);
            }else if(movieNewTabFragment.type  .equals( "动漫")){
                ll.setVisibility(View.GONE);
                tv_1.setText("热门动漫");
                tv_2.setText("动漫榜单");
                tv_3.setText("热门动漫推荐");
                stb.setVisibility(View.VISIBLE);
            }

        }


        private void selectUI(int position) {
            LinearLayout tabsContainer = (LinearLayout) stb.getChildAt(0);
            for (int i = 0; i < tabsContainer.getChildCount(); i++) {
                View tabView = tabsContainer.getChildAt(i);//设置背景图片
                TextView tv_tab_title = tabView.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
                tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, i == position ? 15 : 15);

                TextPaint tp = tv_tab_title.getPaint();
                if (i == position) {
                    tp.setFakeBoldText(true);
                } else {
                    tp.setFakeBoldText(false);
                }
            }
        }
    }


    class ItemView extends MyViewHolder implements View.OnClickListener {
        private MovieIndexRecommendEntity.DataBeanX.RecommendBean.DataBean dataBean;

        private ImageView iv;
        private TextView tv_title;
        private ScoreView scv;
        private TextView tv_point;
        private TextView tv_info;
        private RelativeLayout ll;


        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(movieNewTabFragment.getContext()).inflate(R.layout.item_movie_newtab_item, parent, false));
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
            dataBean = (MovieIndexRecommendEntity.DataBeanX.RecommendBean.DataBean) object;

            tv_title.setText(dataBean.getVod_title());



//            try {
//                scv.setRate((float) Double.parseDouble(dataBean.getVod_score()));
//            }catch (Exception e){e.printStackTrace();}
//            tv_point.setText(dataBean.getVod_score());

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
                    Glide.with(movieNewTabFragment.getContext()).load(dataBean.getVod_pic())
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
            MovieSearchActivity.intentMe(movieNewTabFragment.getActivity() , beanMovieSearchItem.getTitle() , beanMovieSearchItem);
        }

    }

}
