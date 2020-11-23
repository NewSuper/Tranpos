package com.newsuper.t.juejinbao.ui.movie.craw;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by flt on 2016/7/29.
 */

public class ThreadPoolInstance {
    ExecutorService cachedThreadPool = null;
    private static ThreadPoolInstance mThreadPoolInstance = null;

    public static ThreadPoolInstance getInstance(){
        if(mThreadPoolInstance == null){
            mThreadPoolInstance = new ThreadPoolInstance();
        }
        return mThreadPoolInstance;
    }

    ThreadPoolInstance(){
        cachedThreadPool = Executors.newCachedThreadPool();
    }
    public void add(Thread t){
        cachedThreadPool.execute(t);
    }
}
