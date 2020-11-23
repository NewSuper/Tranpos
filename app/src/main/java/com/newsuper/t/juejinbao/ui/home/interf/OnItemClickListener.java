package com.newsuper.t.juejinbao.ui.home.interf;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface OnItemClickListener {
    void onItemClick(int position, View view, RecyclerView.ViewHolder vh);
    //type 1 代表28 2代表29
    void tatistical(int id, int type);
}