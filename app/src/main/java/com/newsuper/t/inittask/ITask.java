package com.newsuper.t.inittask;

import android.os.Process;
import android.support.annotation.IntRange;

import java.util.List;
import java.util.concurrent.Executor;


public interface ITask {
    //优先级的范围，可根据task的重要程度及工作量
    @IntRange(from = Process.THREAD_PRIORITY_FOREGROUND,
            to = Process.THREAD_PRIORITY_LOWEST)
    int priority();

    void run();

    //task执行所在的线程池
    Executor runOn();

    //依赖关系
    List<Class<? extends Task>> dependsOn();

    //异步线程执行的task是否需要在调用wait时等待，默认不用
    boolean needWait();

    //是否在主线程执行
    boolean runOnMainThread();

    //只在主进程执行
    boolean onlyInMainProcess();

    //task主任务完成后需要执行的任务
    Runnable getTailRunnable();

    void setTaskCallBack(TaskCallBack callBack);

    boolean needCall();

}
