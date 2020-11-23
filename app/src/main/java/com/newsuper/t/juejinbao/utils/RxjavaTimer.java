package com.newsuper.t.juejinbao.utils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxjavaTimer {


    public static Observable<Integer> countdown(int time) {
        if (time < 0) time = 0;

        final int countTime = time;

        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long increaseTime) {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(countTime + 1);

    }

    private static Subscription mSubscription;

    /**
     * milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public static void timer(long milliseconds, final IRxNext next) {
        mSubscription = Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                        //取消订阅
                        cancel();
                    }
                });


    }


    /**
     * 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public static void interval(long milliseconds, final IRxNext next) {
        mSubscription = Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }
                });
    }


    /**
     * 取消订阅
     */
    public static void cancel() {
        if (mSubscription != null) {
            if (!mSubscription.isUnsubscribed()) {

                mSubscription.unsubscribe();
            }
        }
    }


    public interface IRxNext {
        void doNext(long number);
    }

}
