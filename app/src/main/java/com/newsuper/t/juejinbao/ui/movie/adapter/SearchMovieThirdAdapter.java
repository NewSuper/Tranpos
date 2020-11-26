package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.entity.DependentResourcesDataEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.JSMovieSearchWebBean;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.ClickUtil;

import java.util.ArrayList;
import java.util.List;

import static io.paperdb.Paper.book;

public class SearchMovieThirdAdapter<T extends EasyAdapter.TypeBean> extends RecyclerView.Adapter<SearchMovieThirdAdapter.MyViewHolder>{
    private Context context;

    //关键字
    private String key;

    private List<T> items = new ArrayList<>();

    public SearchMovieThirdAdapter(Context context){
        this.context = context;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public void update(List<T> items){
        this.items = items;
        notifyDataSetChanged();
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

//        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(ViewGroup parent) {super(parent);}
        public void setData(Object object) {



        }
    }

    class ItemView extends MyViewHolder implements View.OnClickListener {
        JSMovieSearchWebBean.DataBean dataBean;
        DependentResourcesDataEntity.DataBeanX.DataBean dataBean2;

        ImageView iv;
        TextView tv_name;
        TextView tv_detail;

        RelativeLayout rl_pf;
        TextView tv_year;
        TextView tv_type;


        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_searchmovie_web, parent, false));
            iv = itemView.findViewById(R.id.iv);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_detail = itemView.findViewById(R.id.tv_detail);
            rl_pf = itemView.findViewById(R.id.rl_pf);
            tv_year = itemView.findViewById(R.id.tv_year);
            tv_type = itemView.findViewById(R.id.tv_type);

            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);

            if(object instanceof JSMovieSearchWebBean.DataBean){
                rl_pf.setVisibility(View.GONE);
                dataBean = (JSMovieSearchWebBean.DataBean) object;

                RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.emptystate_pic)
                        .error(R.mipmap.emptystate_pic)
                        .skipMemoryCache(true)
                        .centerCrop()
                        .dontAnimate()

                        .diskCacheStrategy(DiskCacheStrategy.ALL);

                try {
                    if (dataBean.getPic() != null && iv != null) {
                        //带白色边框的圆形图片加载
                        Glide.with(context).load(dataBean.getPic())
                                .apply(options)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(iv);
                    }
                }catch (Exception e){
                    Log.e("zy" , "glide_Exception");}

                tv_name.setText( Utils.getSearchTitle2(context, dataBean.getName(), key));
                tv_detail.setText(dataBean.getActor());


            }else{
                rl_pf.setVisibility(View.VISIBLE);
                dataBean2 = (DependentResourcesDataEntity.DataBeanX.DataBean) object;

                RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.emptystate_pic)
                        .error(R.mipmap.emptystate_pic)
                        .skipMemoryCache(true)
                        .centerCrop()
                        .dontAnimate()

                        .diskCacheStrategy(DiskCacheStrategy.ALL);

                try {
                    if (dataBean2.getVod_pic() != null && iv != null) {
                        //带白色边框的圆形图片加载
                        Glide.with(context).load(dataBean2.getVod_pic())
                                .apply(options)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(iv);
                    }
                }catch (Exception e){
                    Log.e("zy" , "glide_Exception");}

                tv_name.setText(Utils.getSearchTitle2(context, dataBean2.getVod_name(), key));
                tv_detail.setText(dataBean2.getVod_class() + " " + Utils.FormatW(dataBean2.getVod_hits()) + "次播放");

                if(dataBean2.getVod_year() == 0){
                    tv_year.setText("未知");
                }else {
                    tv_year.setText(dataBean2.getVod_year() + "");
                }
                tv_type.setText(dataBean2.getVod_class());

            }



        }

        @Override
        public void onClick(View view) {
            //连点判断
            if (!ClickUtil.isNotFastClick()) {
                return;
            }

            //登录判断
            LoginEntity loginEntity = book().read(PagerCons.USER_DATA);
            if(loginEntity == null) {
                context.startActivity(new Intent(context, GuideLoginActivity.class));
                return;
            }

            if(dataBean != null) {
//                BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_COMMON + "/movie/jkk_detail?path=" + dataBean.getUrl() + "&img=" + dataBean.getPic());
                BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_JKKDETAIL + "?path=" + dataBean.getUrl() + "&img=" + dataBean.getPic());
            }else{
//                BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_COMMON + "/film/detail/" + dataBean2.getVod_id()+ "?ckey=" + dataBean2.ckey);
                BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MOVIEDETAIL + "?id=" + dataBean2.getVod_id()+ "&ckey=" + dataBean2.ckey);
            }
        }


    }

    class FooterView extends MyViewHolder{

        public FooterView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_movie_filter_footer, parent, false));
        }
    }


}
