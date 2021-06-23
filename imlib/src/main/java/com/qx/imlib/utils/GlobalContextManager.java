package com.qx.imlib.utils;

import android.content.Context;

public class GlobalContextManager {
    private Context context;

    static class Holder{
        final static GlobalContextManager instance = new GlobalContextManager();
    }

    public static GlobalContextManager getInstance(){
        return Holder.instance;
    }

    public Context getContext() {
        return context;
    }
    public void cacheApplicationContext(Context context){
        this.context = context;
    }
}
