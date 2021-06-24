package com.qx.imlib.utils;

import android.content.Context;

public class ContextUtils {
    private Context context;

    static class Holder{
        final static ContextUtils instance = new ContextUtils();
    }

    public static ContextUtils getInstance(){
        return Holder.instance;
    }

    public Context getContext() {
        return context;
    }

    public void cacheApplicationContext(Context context){
        this.context = context;
    }
}
