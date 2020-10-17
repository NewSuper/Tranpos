package com.transpos.sale.thread;


import android.support.annotation.MainThread;

/**
 *
 * <p>
 * 简化版的对异步顺序执行的封装,支持链式调用,简洁多次异步执行的代码.
 * 设计的思想和RxJava类似.
 */

public class SeriesAsyncWorker implements Work.Callback {
    private WorkNode mHead = null;
    private WorkNode mTail = null;
    private boolean mStarted;

    public static SeriesAsyncWorker create() {
        return new SeriesAsyncWorker();
    }

    public synchronized SeriesAsyncWorker next(Work work) {
        if (mStarted) {
            throw new IllegalStateException("call #next after worker has started");
        }
        if (work == null)
            return this;
        work.setCallback(this);
        final WorkNode appendNode = new WorkNode(work);
        if (mHead == null) {
            mHead = mTail = appendNode;
        }
        appendNode.next = null;
        mTail.next = appendNode;
        mTail = appendNode;
        return this;
    }

    @MainThread
    public void start() {
        start(null);
    }

    @MainThread
    public synchronized boolean start(final Object param) {
        if (mStarted)
            return false;
        mStarted = true;
        ThreadDispatcher.getDispatcher().runOnMain(new Runnable() {
            @Override
            public void run() {
                doWorkInternal(mHead, param);
            }
        });
        return true;
    }

    public void doNext() {
        doNext(null);
    }

    @Override
    public synchronized void doNext(Object lastResult) {
        final WorkNode head = mHead;
        if (head != null && head.next != null) {
            doWorkInternal(head.next, lastResult);
        }
    }

    @Override
    public synchronized void interrupt() {
        reset();
    }

    private synchronized void doWorkInternal(WorkNode node, final Object lastResult) {
        if (node == null) {
            reset();
            return;
        }
        mHead = node;
        final Work work = node.work;
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                work.doWork(lastResult);
            }
        };
        final long delay = work.getDelayTime();
        final boolean doInBackground = work.isDoInBackground();
        if (doInBackground) {
            if (delay > 0) {
                ThreadDispatcher.getDispatcher().postDelayed(runnable, delay, work.getPriority());
            } else {
                ThreadDispatcher.getDispatcher().post(runnable, work.getPriority());
            }
        } else {
            if (delay > 0) {
                ThreadDispatcher.getDispatcher().postOnMainDelayed(runnable, delay);
            } else {
                ThreadDispatcher.getDispatcher().postOnMain(runnable);
            }
        }
    }

    private void reset() {
        mHead = mTail = null;
        mStarted = false;
    }

    private static class WorkNode {
        Work work;
        WorkNode next;

        WorkNode(Work work) {
            this.work = work;
        }
    }
}