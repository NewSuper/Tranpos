package com.newsuper.t.juejinbao.base;

import android.view.View;

import java.util.List;

public interface MyItemClickListener<T> {
    void onItemClickListener(View view, int postion, List<T> ts);
    void onLongClickListener(View view, int postion);
}
