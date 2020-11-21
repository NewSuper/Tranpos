package com.newsuper.t.juejinbao.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> {
    protected Activity mContext;
    public List<T> mList;
    private MyItemClickListener mOnItemClickListener;
    private int selectItem = 0;

    public BaseAdapter(List<T> list, Activity context) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(getContentView(viewType), parent, false);
        return new BaseHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final BaseHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这个地方一定要判断 不然你没有注册点击事件的时候，点击Item的时候会报错
                if (mOnItemClickListener == null) {
                    return;
                }
                mOnItemClickListener.onItemClickListener(v, position, mList);
            }
        });
        holder.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //这个地方一定要判断 不然你没有注册点击事件的时候，点击Item的时候会报错
                if (mOnItemClickListener == null) {
                    return true;
                }
                mOnItemClickListener.onLongClickListener(v, position);
                return true;
            }
        });
        covert(holder, mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    protected abstract int getContentView(int viewType);

    protected abstract void covert(BaseHolder holder, T data, int position);


    public void setOnItemClickListener(MyItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void updateList(List<T> newList){
        this.mList = newList;
        notifyDataSetChanged();
    }
}
