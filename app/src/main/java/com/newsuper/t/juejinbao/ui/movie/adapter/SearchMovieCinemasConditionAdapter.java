package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.module.movie.entity.DependentResourcesDataEntity;
import com.juejinchain.android.module.movie.entity.MovieCinamesEntity;

import java.util.ArrayList;
import java.util.List;

public class SearchMovieCinemasConditionAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;

    private List<MovieCinamesEntity.DataBean> items = new ArrayList<>();

    private OnItemClickListener onItemClickListener;


    public SearchMovieCinemasConditionAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void update(List<MovieCinamesEntity.DataBean> itemList) {
        this.items = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case EasyAdapter.TypeBean.ITEM:
                return new ItemViewHolder(viewGroup);
            case EasyAdapter.TypeBean.FOOTER:
                return new FooterViewHolder(viewGroup);
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

    //item
    public class ItemViewHolder extends MyViewHolder implements View.OnClickListener {
        private MovieCinamesEntity.DataBean countBean;

        private TextView tv;

        public ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_searchmoviecinemas_condition, parent, false));
            tv = itemView.findViewById(R.id.tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            countBean = (MovieCinamesEntity.DataBean) object;

            tv.setText(countBean.getTitle());


            if (countBean.isCheck) {
                tv.setTextColor(context.getResources().getColor(R.color.white));
                tv.setBackgroundResource(R.drawable.bg_radio4);
            } else {
                tv.setTextColor(context.getResources().getColor(R.color.app_text3));
                tv.setBackgroundResource(R.drawable.bg_radio5);
            }


        }

        @Override
        public void onClick(View view) {

            for (MovieCinamesEntity.DataBean condition2 : items) {
                condition2.isCheck = condition2.getTitle().equals(countBean.getTitle());
            }
            notifyDataSetChanged();

            onItemClickListener.click(countBean);


        }

    }


    //footer
    public class FooterViewHolder extends MyViewHolder implements View.OnClickListener {

        private MovieCinamesEntity.DataBean countBean;

        private TextView tv;

        public FooterViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_searchmoviecinemas_condition_footer, parent, false));
            tv = itemView.findViewById(R.id.tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            countBean = (MovieCinamesEntity.DataBean) object;

//            tv.setText(countBean.getTitle());

            tv.setTextColor(context.getResources().getColor(R.color.app_text3));
            tv.setBackgroundResource(R.drawable.bg_radio5);
//            if (countBean.isCheck) {
//                tv.setTextColor(context.getResources().getColor(R.color.white));
//                tv.setBackgroundResource(R.drawable.bg_radio4);
//            } else {
//                tv.setTextColor(context.getResources().getColor(R.color.app_text));
//                tv.setBackgroundResource(R.drawable.bg_radio5);
//            }


        }

        @Override
        public void onClick(View view) {
//            for (MovieCinamesEntity.DataBean condition2 : items) {
//                condition2.isCheck = (condition2.getUiType() == EasyAdapter.TypeBean.FOOTER);
//                if(condition2.isCheck) {
//                    onItemClickListener.click(condition2);
//                }
//            }
            onItemClickListener.click(countBean);
            notifyDataSetChanged();


        }
    }


    public interface OnItemClickListener {
        void click(MovieCinamesEntity.DataBean dataBean);
    }


}
