package com.newsuper.t.juejinbao.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * g公用的BaseHolder
 */

public class BaseHolder extends RecyclerView.ViewHolder {
    View mView ;
    public BaseHolder(View itemView) {
        super(itemView);
        this.mView=itemView;
    }

    public View getView(){
        return mView;
    }
}
