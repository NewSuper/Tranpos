package com.transpos.sale.thread;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;


/**
 *
 * 线程分发管理的实现类
 */
class DispatcherImpl implements IDispatcher {
    private static final String TAG = "DispatcherImpl";
    private static final Priority DEFAULT_PRIORITY = Priority.NORMAL;
    private static DispatcherImpl sInstance;

    public static DispatcherImpl getInstance() {
        if (sInstance == null) {
            synchronized (DispatcherImpl.class) {
                if (sInstance == null) {
                    sInstance = new DispatcherImpl();
                }
            }
        }
        return sInstance;
    }

    private final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private final BizExecutor mExecutorService;
    private final SerialExecutor mSerialExecutor;
    private final Map<Runnable, Priority> mPriorityMap;
    private final Map<Runnable, Future<?>> mFutureMap;

    private DispatcherImpl() {
        mPriorityMap = new WeakHashMap<>();
        mFutureMap = new WeakHashMap<>();
        mExecutorService = new BizExecutor(new PriorityBlockingQueue<>(11, new Comparator<Runnable>() {
            @Override
            public int compare(Runnable o1, Runnable o2) {
                return getPriority(o1).value() - getPriority(o2).value();
            }

            @NonNull
            private Priority getPriority(Runnable r) {
                Priority priority = mPriorityMap.get(r);
                if (priority == null) {
                    priority = DEFAULT_PRIORITY;
                }
                return priority;
            }
        }));
        mSerialExecutor = new SerialExecutor();
    }

    private void postInternal(Runnable r, Priority priority) {
        if (r == null)
            throw new NullPointerException("Runnable cannot be null");
        if (priority == null) {
            priority = DEFAULT_PRIORITY;
        }
        mPriorityMap.put(r, priority);
        final Future<?> future;
        if (r instanceof Future<?>) {
            mExecutorService.execute(r);
            future = (Future<?>) r;
        } else {
            future = mExecutorService.submit(r);
        }
        mFutureMap.put(r, future);
//        LogUtil.logI(TAG, StringUtil.format("postInternal -> [ run: %s, priority: %s]", r, priority));
    }

    /**
     * 提交一个业务runnable
     */
    @Override
    public void post(Runnable r) {
        post(r, mPriorityMap.get(r));
    }

    /**
     * 提交一个UI相关业务runnable，此业务优先级别最高，请根据业务的紧急程度添加
     */
    @Override
    public void postHighPriority(Runnable r) {
        post(r, Priority.HIGH);
    }

    /**
     * 提交一个普通优先级的后台Work
     */
    @Override
    public void postNormalPriority(Runnable r) {
        post(r, Priority.NORMAL);
    }

    /**
     * 提交一个低优先级的后台Work
     */
    @Override
    public void postLowPriority(Runnable r) {
        post(r, Priority.LOW);
    }

    @Override
    public void post(Runnable r, Priority priority) {
        postInternal(r, priority);
    }

    @Override
    public void postDelayed(Runnable r, long delay) {
        postDelayed(r, delay, mPriorityMap.get(r));
    }

    /**
     * 延时提交业务runnable，runnable需设置好对应业务优先级，默认为Normal
     */
    @Override
    public void postDelayed(final Runnable r, long delay, final Priority priority) {
        if (delay <= 0) {
            postInternal(r, priority);
        } else {
            final RunnableFuture future = mExecutorService.newTaskFor(r, null);
            mFutureMap.put(r, future);
            mMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    postInternal(future, priority);
                }
            }, delay);
        }
    }

    /**
     * 顺序串行任务
     */
    @Override
    public void postSerial(Runnable r) {
        postSerial(r, mPriorityMap.get(r));
    }

    @Override
    public void postSerial(Runnable r, Priority priority) {
        if (r == null)
            throw new NullPointerException("Runnable cannot be null");
        if (priority == null) {
            priority = DEFAULT_PRIORITY;
        }
        mPriorityMap.put(r, priority);
        mSerialExecutor.execute(r);
    }

    @Override
    public void remove(Runnable r) {
        remove(r, false);
    }

    @Override
    public void remove(Runnable r, boolean mayInterruptIfRunning) {
        final Future future = mFutureMap.get(r);
        if (future != null) {
            future.cancel(mayInterruptIfRunning);
        }
    }

    /**
     * 在主线程中执行,如果当前是子线程,则执行{@link #postOnMain(Runnable)}
     */
    @Override
    public void runOnMain(Runnable r) {
        if (r == null)
            throw new NullPointerException("Runnable cannot be null");
        if (Looper.myLooper() == Looper.getMainLooper()) {
            r.run();
            return;
        }
        postOnMain(r);
    }

    /**
     * 提交到主线程Looper的消息队列中执行
     */
    @Override
    public void postOnMain(Runnable r) {
        if (r == null)
            throw new NullPointerException("Runnable cannot be null");
        mMainHandler.post(r);
    }

    @Override
    public void postOnMainDelayed(Runnable r, long delay) {
        if (r == null)
            throw new NullPointerException("Runnable cannot be null");
        mMainHandler.postDelayed(r, delay);
    }

    @Override
    public void removeOnMain(Runnable r) {
        mMainHandler.removeCallbacks(r);
    }

    private class SerialExecutor implements Executor {
        final ArrayDeque<RunnableFuture> mTasks = new ArrayDeque<>();
        RunnableFuture mActive;

        @Override
        public synchronized void execute(@NonNull final Runnable r) {
            final RunnableFuture future = mExecutorService.newTaskFor(new Runnable() {
                @Override
                public void run() {
                    try {
                        r.run();
                    } finally {
                        scheduleNext();
                    }
                }
            }, null);
            mFutureMap.put(r, future);
            mTasks.offer(future);
            if (mActive == null) {
                scheduleNext();
            }
        }

        synchronized void scheduleNext() {
            for (; ; ) {
                mActive = mTasks.poll();
                if (mActive == null)
                    break;
                if (!mActive.isCancelled()) {
                    mExecutorService.execute(mActive);
                    break;
                }
            }
        }
    }
}