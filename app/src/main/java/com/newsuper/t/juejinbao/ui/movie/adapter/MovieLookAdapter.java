package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieLooklDataEntity;
import com.newsuper.t.juejinbao.utils.StringUtils;
import com.newsuper.t.juejinbao.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 用于平均分的textview的grid
 */
public class MovieLookAdapter extends RecyclerView.Adapter<MovieLookAdapter.MyViewHolder>{
    private Context context;
    private List<MovieLooklDataEntity.DataBean.UserListBean> items = new ArrayList<>();

    public MovieLookAdapter(Context context){
        this.context = context;
    }

    public void update(List<MovieLooklDataEntity.DataBean.UserListBean> items){
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(ViewGroup parent) {super(parent);}
        public void setData(Object object) {}
    }

    class ItemView extends MyViewHolder{
        private MovieLooklDataEntity.DataBean.UserListBean bean;

        private RoundImageView iv;
        private ImageView iv_j;

        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_look, parent, false));
            iv = itemView.findViewById(R.id.iv);
            iv_j = itemView.findViewById(R.id.iv_j);
        }

        public void setData(Object object) {
            bean = (MovieLooklDataEntity.DataBean.UserListBean) object;

            if(bean == null){
                return;
            }

            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.emptystate_pic)
                    .error(R.mipmap.emptystate_pic)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .dontAnimate()

                    .override(StringUtils.dip2px(context, 30), StringUtils.dip2px(context, 30))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CircleCrop());

            try {
                if (bean.getAvatar() != null && iv != null) {
                    //带白色边框的圆形图片加载
                    Glide.with(context).load(bean.getAvatar())
                            .apply(options)
                            .into(iv);
                }

                //头像加j
                if(!TextUtils.isEmpty(bean.getJ_url())) {
                    iv_j.setVisibility(ViewGroup.VISIBLE);
                    Glide.with(context).load(bean.getJ_url()).into(iv_j);
                }else {
                    iv_j.setVisibility(ViewGroup.GONE);
                }
            }catch (Exception e){
                Log.e("zy" , "glide_Exception");}


        }
    }



}
