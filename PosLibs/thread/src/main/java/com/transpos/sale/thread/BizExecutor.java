package com.transpos.sale.thread;

import android.os.Process;
import android.os.SystemClock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.transpos.sale.thread.LogUtil.logE;
import static com.transpos.sale.thread.LogUtil.logI;


class BizExecutor extends ThreadPoolExecutor {
    private static final String TAG = "BizExecutor";
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int POOL_SIZE = Math.max(1, Math.min(CPU_COUNT - 2, 8));
    private static final int KEEP_ALIVE_SECONDS = 10;
    private static final int CHECK_EXEC_TASK_INTERVAL_SECOND = 10;// 检查耗时任务的间隔

    private final Map<Runnable, RunningTask> mTaskCheckTimeFutureMap = new HashMap<>();
    private final ScheduledExecutorService mTaskExecTimeCheckExecutor = Executors.newSingleThreadScheduledExecutor(new PriorityThreadFactory() {
        @Override
        protected int getThreadPriority() {
            return Process.THREAD_PRIORITY_LOWEST;
        }
    });

    BizExecutor(BlockingQueue<Runnable> workQueue) {
        super(POOL_SIZE, POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS, workQueue, new BgThreadFactory());
        logI(TAG, "BizExecutor -> [ CPU_COUNT: %d, POOL_SIZE: %d, KEEP_ALIVE_SECONDS: %d ]",
                CPU_COUNT, POOL_SIZE, KEEP_ALIVE_SECONDS);
    }

    @Override
    protected void beforeExecute(Thread t, final Runnable r) {
        final String taskName = getNameForTask(r);
        logI(TAG, "beforeExecute( runnable: %s ) ", taskName);

        final long startTime = SystemClock.uptimeMillis();
        RunningTask runningTask = new RunningTask(mTaskExecTimeCheckExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                logE(TAG, "CheckExec -> < %s > Task may cause too much time, please review the code!!! TIME_PAST: %s",
                        taskName, SystemClock.uptimeMillis() - startTime);

            }
        }, CHECK_EXEC_TASK_INTERVAL_SECOND, CHECK_EXEC_TASK_INTERVAL_SECOND, TimeUnit.SECONDS), startTime);
        mTaskCheckTimeFutureMap.put(r, runningTask);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        String taskName = getNameForTask(r);
        RunningTask runningTask = mTaskCheckTimeFutureMap.remove(r);
        if (runningTask != null) {
            logI(TAG, "afterExecute( runnable: %s, throwable: %s ) time: %s ms",
                    taskName, t, SystemClock.uptimeMillis() - runningTask.startTime);
            runningTask.future.cancel(true);
        } else {
            logI(TAG, "afterExecute( runnable: %s, throwable: %s ) ", taskName, t);
        }

        if (t != null) {
            logE(TAG, "afterExecute( runnable: %s, throwable: %s ) Exception happened !!! Msg: %s", taskName, t, t.getMessage());
            t.printStackTrace();
        }
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new TaskFuture<>(runnable, value, String.valueOf(runnable));
    }

    private static String getNameForTask(Runnable r) {
        String name;
        if (r instanceof TaskFuture) {
            name = ((TaskFuture) r).name;
        } else {
            name = String.valueOf(r);
        }
        return name;
    }

    private static class TaskFuture<T> extends FutureTask<T> {
        final String name;

        TaskFuture(Runnable runnable, T result, String name) {
            super(runnable, result);
            this.name = name;
        }
    }

    private static class RunningTask {
        final Future<?> future;
        final long startTime;

        RunningTask(Future<?> future, long startTime) {
            this.future = future;
            this.startTime = startTime;
        }
    }
}