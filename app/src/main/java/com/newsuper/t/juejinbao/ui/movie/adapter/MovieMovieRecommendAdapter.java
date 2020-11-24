package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieMovieRecommendDataEntity;
import com.newsuper.t.juejinbao.ui.movie.utils.OnClickReturnStringListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

public class MovieMovieRecommendAdapter extends RecyclerView.Adapter<MovieMovieRecommendAdapter.MyViewHolder> {
    private static final String MOVIE_TAG1 = "近期热门";
    private static final String MOVIE_TAG2 = "影院热映";
    private static final String MOVIE_TAG3 = "即将上映";


    private Context context;

    private List<MovieMovieRecommendDataEntity.DataBeanX.DataBean> items = new ArrayList<>();
    private RadioListener radioListener;
    private OnClickReturnStringListener onClickReturnStringListener;

    private String type;
    //标签
    private String category;

    public MovieMovieRecommendAdapter(Context context, String type, RadioListener radioListener, OnClickReturnStringListener onClickReturnStringListener) {
        this.context = context;
        this.radioListener = radioListener;
        this.onClickReturnStringListener = onClickReturnStringListener;
        this.type = type;
    }

    public void update(List<MovieMovieRecommendDataEntity.DataBeanX.DataBean> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void updateAdd(List<MovieMovieRecommendDataEntity.DataBeanX.DataBean> items , List<MovieMovieRecommendDataEntity.DataBeanX.DataBean> addItems){
        this.items = items;
        //需要更新的位置段
        int itemCount = addItems.size();
        //起始更新的位置
        int positionStart = items.size() - itemCount;

        notifyItemRangeInserted(positionStart, itemCount);
        //更新最后一条，防止添加0条时不刷新
        notifyItemChanged(items.size() - 1);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case EasyAdapter.TypeBean.HEADER:
                return new HeadView(viewGroup, type);
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


//    @Override
//    public void onViewRecycled(@NonNull MyViewHolder holder) {
//        super.onViewRecycled(holder);
//        if(holder instanceof ItemView) {
//            ImageView imageView = ((ItemView) holder).iv;
//            if (imageView != null) {
//                Glide.with(context).clear(imageView);
//            }
//        }
//    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(ViewGroup parent) {
            super(parent);
        }

        public void setData(Object object) {

        }
    }

    class HeadView extends MyViewHolder {
        RadioGroup rg_dy;

        RadioGroup rg_dsj;

        RadioButton rb1_dy;

        RadioButton rb1_dsj;

        public HeadView(ViewGroup parent, String type) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_movie_recommend_head, parent, false));

            rg_dy = itemView.findViewById(R.id.rg_dy);
            rg_dsj = itemView.findViewById(R.id.rg_dsj);
            rb1_dy = itemView.findViewById(R.id.rb1_dy);
            rb1_dsj = itemView.findViewById(R.id.rb1_dsj);

            if (type.equals("电影")) {
                rg_dsj.setVisibility(View.GONE);

                rg_dy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                        if (checkedId == R.id.rb1_dy) {
                            radioListener.checkTag(MOVIE_TAG1);
                        } else if (checkedId == R.id.rb2_dy) {
                            radioListener.checkTag(MOVIE_TAG2);
                        } else if (checkedId == R.id.rb3_dy) {
                            radioListener.checkTag(MOVIE_TAG3);
                        }
                    }
                });

                rb1_dy.setChecked(true);
            } else if (type.equals("电视剧")) {
                rg_dy.setVisibility(View.GONE);
                rg_dsj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                        if (checkedId == R.id.rb1_dsj) {
                            radioListener.checkTag("国产剧");
                        } else if (checkedId == R.id.rb2_dsj) {
                            radioListener.checkTag("韩剧");
                        } else if (checkedId == R.id.rb3_dsj) {
                            radioListener.checkTag("日剧");
                        } else if (checkedId == R.id.rb4_dsj) {
                            radioListener.checkTag("美剧");
                        } else if (checkedId == R.id.rb5_dsj) {
                            radioListener.checkTag("港剧");
                        }
                    }
                });

                rb1_dsj.setChecked(true);
            } else {
                rg_dy.setVisibility(View.GONE);
                rg_dsj.setVisibility(View.GONE);
            }


        }

        public void setData(Object object) {

        }

    }

    class ItemView extends MyViewHolder implements View.OnClickListener {

        private MovieMovieRecommendDataEntity.DataBeanX.DataBean bean;

        ImageView iv;
        TextView tv_date;
        TextView tv_name;
        TextView tv_long;
        TextView tv_direct;
        TextView tv_actor;
        ScoreView scv;
        TextView tv_score;

        LinearLayout ll1;

        TextView tv_title;
        TextView tv_time;
        TextView tv_type;
        TextView tv_area;
        TextView tv_like;
        TextView tv_alert;
        TextView tv_zy;

        LinearLayout ll2;


        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_movie_recommend, parent, false));
            iv = itemView.findViewById(R.id.iv);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_long = itemView.findViewById(R.id.tv_long);
            tv_direct = itemView.findViewById(R.id.tv_direct);
            tv_actor = itemView.findViewById(R.id.tv_actor);
            scv = itemView.findViewById(R.id.scv);
            tv_score = itemView.findViewById(R.id.tv_score);

            ll1 = itemView.findViewById(R.id.ll1);


            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_area = itemView.findViewById(R.id.tv_area);
            tv_like = itemView.findViewById(R.id.tv_like);
            tv_alert = itemView.findViewById(R.id.tv_alert);
            tv_zy = itemView.findViewById(R.id.tv_zy);

            ll2 = itemView.findViewById(R.id.ll2);

            itemView.setOnClickListener(this);

        }

        public void setData(Object object) {
            bean = (MovieMovieRecommendDataEntity.DataBeanX.DataBean) object;

            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.emptystate_pic)
                    .error(R.mipmap.emptystate_pic)
                    .skipMemoryCache(true)
                    .fitCenter().centerCrop()
                    .dontAnimate()
                    .override(StringUtils.dip2px(context, 200), StringUtils.dip2px(context, 200))
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            //带白色边框的圆形图片加载
            try {
                if(iv != null && context != null && bean != null) {
                    Glide.with(context).load(TextUtils.isEmpty(bean.getCover()) ? "" : bean.getCover())
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .apply(options)
                            .into(iv);
                }
            }catch (Exception e){
                Log.e("zy" , "glide_Exception");}

            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            //影院热映
            if (category.equals(MOVIE_TAG1)) {
                ll1.setVisibility(View.VISIBLE);
                tv_score.setText(bean.getRate());
                tv_date.setText(DateUtils.showDate(bean.getCreate_time()) + "更新");
                tv_name.setText(bean.getTitle());
                tv_long.setText(bean.getDuration() + "分钟  " + bean.getLocation());
                tv_direct.setText("导演  " + bean.getDirector());
                tv_actor.setText("主演  " + bean.getActor());
                try {
                    scv.setRate(Float.parseFloat(bean.getRate()));
                } catch (Exception e) {
                }
            }
            //近期热门
            else if (category.equals(MOVIE_TAG2)) {
                ll1.setVisibility(View.VISIBLE);
                tv_score.setText(bean.getRate());
                tv_date.setText("");
                tv_name.setText(bean.getTitle());
                tv_long.setText(bean.getDuration() + "分钟  " + bean.getLocation());
                tv_direct.setText("导演  " + bean.getDirector());
                tv_actor.setText("主演  " + bean.getActor());
                try {
                    scv.setRate(Float.parseFloat(bean.getRate()));
                } catch (Exception e) {
                }
            }
            //即将上映
            else if (category.equals(MOVIE_TAG3)) {
                ll2.setVisibility(View.VISIBLE);
                tv_date.setText("");
                tv_time.setText(DateUtils.showYearAndMonAndDay(bean.getRelease_time()));
                tv_title.setText(bean.getTitle());
                tv_type.setText(bean.getExt_class());
                tv_area.setText(bean.getLocation());
                tv_like.setText(Utils.FormatW(bean.getWatch_num()) + "人想看");

                if (bean.getIs_subscription() == 0) {
                    tv_alert.setText("上映提醒");
                    tv_alert.setBackgroundResource(R.drawable.bg_alert);
                    tv_alert.setTextColor(context.getResources().getColor(R.color.app_color));
                } else {
                    tv_alert.setText("√ 已提醒");
                    tv_alert.setBackgroundResource(R.drawable.bg_cancelalert);
                    tv_alert.setTextColor(context.getResources().getColor(R.color.app_text2));
                }

                tv_alert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(!LoginEntity.getIsLogin()){
                            Intent intent = new Intent(context, GuideLoginActivity.class);
                            context.startActivity(intent);
                            return;
                        }

                        Map<String, String> map = new HashMap<>();

                        //订阅
                        if (bean.getIs_subscription() == 0) {
                            map.put("vod_id", bean.getId() + "");
                            map.put("type", "douban");
                            map.put("status", "1");

                            rx.Observable<MovieAlertEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                                    setMovieAlert(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<MovieAlertEntity>()));
                            Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieAlertEntity>() {
                                @Override
                                public void next(MovieAlertEntity lookOrOnTachEntity) {
                                    if (lookOrOnTachEntity.getCode() == 0) {
                                        Toast.makeText(context, "订阅成功", Toast.LENGTH_SHORT).show();
                                        bean.setIs_subscription(1);
                                        try {
                                            bean.setSubscription_id(Integer.parseInt(lookOrOnTachEntity.getData().getId()));
                                        }catch (Exception e){}
                                        notifyDataSetChanged();

                                    } else {
                                        Toast.makeText(context, lookOrOnTachEntity.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void error(String target, Throwable e, String errResponse) {
                                }
                            }, context, false);
                            RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);

                        }
                        //取消订阅
                        else {
                            map.put("id", bean.getSubscription_id() + "");
                            map.put("status", "2");
                            map.put("vod_id", bean.getId() + "");
                            map.put("type", "douban");

                            rx.Observable<MovieCancelEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                                    setMovieCancelAlert(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<MovieCancelEntity>()));
                            Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieCancelEntity>() {
                                @Override
                                public void next(MovieCancelEntity lookOrOnTachEntity) {
                                    if (lookOrOnTachEntity.getCode() == 0) {
                                        Toast.makeText(context, "取消订阅成功", Toast.LENGTH_SHORT).show();
                                        bean.setIs_subscription(0);
                                        notifyDataSetChanged();
                                    }else {
                                        Toast.makeText(context, lookOrOnTachEntity.getMsg(), Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void error(String target, Throwable e, String errResponse) {
                                }
                            }, context, false);
                            RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
                        }


                    }
                });
            }


        }

        @Override
        public void onClick(View view) {
            BeanMovieSearchItem beanMovieSearchItem = new BeanMovieSearchItem();
            beanMovieSearchItem.setImg(bean.getCover());
            beanMovieSearchItem.setForm(bean.getExt_class());
            beanMovieSearchItem.setActor(bean.getActor());
            beanMovieSearchItem.setTitle(bean.getTitle());
            try {
                beanMovieSearchItem.setRating(Double.parseDouble(bean.getRate()));
            }catch (Exception e){
                e.printStackTrace();
            }
            onClickReturnStringListener.onClick(beanMovieSearchItem);
        }
    }

    class FooterView extends MyViewHolder{

        public FooterView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_movie_recommend_footer, parent, false));
        }
    }

    public interface RadioListener {
        void checkTag(String tag);
    }


}
