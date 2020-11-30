package com.newsuper.t.consumer.function.selectgoods.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public abstract class BasePageFragment extends Fragment {

    protected boolean isViewInitiated;//是否初始化视图
    protected boolean isDataInitiated;//是否加载数据
    protected boolean isVisibleToUser;//是否可见
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated=true;
        prepareFetchData();
    }
    //判断fragment是否可见
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser=isVisibleToUser;
        prepareFetchData();
    }

    public boolean prepareFetchData(){
        return prepareFetchData(false);
    }

    //forceUpdate 强制更新
    public boolean prepareFetchData(boolean forceUpdate){
        if(isVisibleToUser&&isViewInitiated&&(!isDataInitiated||forceUpdate)){
            fetchData();
            isDataInitiated=true;
            return true;
        }
        return false;
    }
    public abstract void fetchData();
}
