package com.transpos.sale.thread;


/**
 *
 * {@link SeriesAsyncWorker}中执行的任务实体类<br/>
 * 默认情况下在主UI线程中执行<br/>
 * 假如传入了优先级,则会在{@link ThreadDispatcher}线程调度器中执行
 */

public abstract class Work<LAST_RLT> {
    private Callback callback;
    private boolean doInBackground = false;
    private Priority priority = Priority.NORMAL;
    private long delayTime = 0;

    public abstract void doWork(LAST_RLT lastResult);

    public Work() {
    }

    public Work(boolean doInBackground) {
        this.doInBackground = doInBackground;
    }

    public Work(long delayTime) {
        this.delayTime = delayTime;
    }

    public Work(boolean doInBackground, long delayTime) {
        this.doInBackground = doInBackground;
        this.delayTime = delayTime;
    }

    public Work(Priority priority) {
        doInBackground = true;
        this.priority = priority;
    }

    public Work(Priority priority, long delayTime) {
        doInBackground = true;
        this.priority = priority;
        this.delayTime = delayTime;
    }

    public boolean isDoInBackground() {
        return doInBackground;
    }

    public Priority getPriority() {
        return priority;
    }

    final public void doNext() {
        doNext(null);
    }

    final public void doNext(Object currentResult) {
        if (callback != null) {
            callback.doNext(currentResult);
        }
    }

    final public void interrupt() {
        if (callback != null) {
            callback.interrupt();
        }
    }

    public long getDelayTime() {
        return delayTime;
    }

    void setCallback(Callback callback) {
        this.callback = callback;
    }

    interface Callback<LAST_RLT> {
        void doNext(LAST_RLT lastResult);

        void interrupt();
    }
}
