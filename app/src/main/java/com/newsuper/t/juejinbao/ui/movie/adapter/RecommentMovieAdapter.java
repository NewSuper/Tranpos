package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.module.movie.bean.AdapterItem;
import com.juejinchain.android.module.movie.craw.BeanMovieSearchItem;
import com.juejinchain.android.module.movie.entity.MoviePostDataEntity;
import com.juejinchain.android.module.movie.utils.OnClickReturnStringListener;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.module.movie.view.MyGridView;
import com.juejinchain.android.module.movie.view.WrapContentGridViewManager;

import java.util.ArrayList;
import java.util.List;

public class RecommentMovieAdapter extends RecyclerView.Adapter<MyViewHolder>{

//    private static RecyclerView.RecycledViewPool rcyclerViewPool = new RecyclerView.RecycledViewPool();
//    static {
//        rcyclerViewPool.setMaxRecycledViews(0 , 9);
//    }



    private Context context;

    private List<AdapterItem> items = new ArrayList<>();

    private OnClickReturnStringListener onClickReturnStringListener;

    private OnClickPageListener onClickPageListener;


    public RecommentMovieAdapter(Context context , OnClickReturnStringListener onClickReturnStringListener , OnClickPageListener onClickPageListener) {
        this.context = context;
        this.onClickReturnStringListener = onClickReturnStringListener;
        this.onClickPageListener = onClickPageListener;
    }

    public void update(List<AdapterItem> itemList){
        this.items = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case AdapterItem.HEAD:
                return new HeadViewHolder(viewGroup);
            case AdapterItem.ITEM:
                return new ItemViewHolder(viewGroup);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i).getData());
    }


    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getUiType();
    }

    //头部
    public class HeadViewHolder extends MyViewHolder implements View.OnClickListener {
        private List<MoviePostDataEntity.DataBean.MovieBean> beans;
        private TextView tv_type;
        private TextView tv_more;
        public HeadViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_recomment_rv_head, parent, false));
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_more = itemView.findViewById(R.id.tv_more);
            tv_more.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            beans = (List<MoviePostDataEntity.DataBean.MovieBean>) object;
            if(beans != null &&beans.size() > 0) {
                tv_type.setText(beans.get(0).getCategory2());
            }
        }

        @Override
        public void onClick(View view) {
            if(beans != null && beans.size() > 0) {
                onClickPageListener.turnPage(beans.get(0).getCategory1());
            }
        }
    }

    //item
    public class ItemViewHolder extends MyViewHolder implements View.OnClickListener {
        private List<MoviePostDataEntity.DataBean.MovieBean> beans;


//        private MyGridView gv;

        private RecyclerView rv_gv;

        public ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_recomment_rv_item, parent, false));
//            gv = itemView.findViewById(R.id.gv);

            rv_gv = itemView.findViewById(R.id.rv_gv);

        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            beans = (List<MoviePostDataEntity.DataBean.MovieBean>) object;

            if(beans.size() <= 0){
                return;
            }

            MovieRecommendAdapter movieRecommendAdapter = new MovieRecommendAdapter(context , beans.get(0).getCategory1(), new OnClickReturnStringListener() {
                @Override
                public void onClick(BeanMovieSearchItem beanMovieSearchItem) {
                    onClickReturnStringListener.onClick(beanMovieSearchItem);
                }
            });


            WrapContentGridViewManager gridLayoutManager = new WrapContentGridViewManager(context, 3);
            //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
            gridLayoutManager.setOrientation(GridLayout.VERTICAL);
//            gridLayoutManager.setRecycleChildrenOnDetach(true);
            //设置布局管理器， 参数gridLayoutManager对象
            rv_gv.setLayoutManager(gridLayoutManager);
            rv_gv.setNestedScrollingEnabled(false);
            rv_gv.setAdapter(movieRecommendAdapter);
//            rv_gv.setRecycledViewPool(rcyclerViewPool);
            movieRecommendAdapter.update(beans);




        }

        @Override
        public void onClick(View view) {

        }
    }

    public interface OnClickPageListener{
        void turnPage(String type);
    }

}
