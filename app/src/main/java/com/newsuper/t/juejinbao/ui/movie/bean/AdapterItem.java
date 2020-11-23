package com.newsuper.t.juejinbao.ui.movie.bean;

import com.juejinchain.android.module.movie.entity.MoviePostDataEntity;

import java.util.List;

public class AdapterItem {
    public static final int HEAD = 1;
    public static final int ITEM = 2;

    private int uiType;

    private List<MoviePostDataEntity.DataBean.MovieBean> data;

    public int getUiType() {
        return uiType;
    }

    public void setUiType(int uiType) {
        this.uiType = uiType;
    }

    public Object getData() {
        return data;
    }

    public void setData(List<MoviePostDataEntity.DataBean.MovieBean> data) {
        this.data = data;
    }

    public AdapterItem(int uiType , List<MoviePostDataEntity.DataBean.MovieBean> data) {
        this.uiType = uiType;
        this.data = data;
    }
}
