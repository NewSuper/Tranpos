package com.newsuper.t.juejinbao.utils;

import rx.Subscription;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import java.util.concurrent.TimeUnit;


public class RxTimerUtil {
    private static Subscription mSubscription;

    /**
     * milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public static void timer(long milliseconds, final IRxNext next) {
        mSubscription = Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
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

    public interface IRxNext {
        void doNext(long number);

    }

    /**
     * 取消订阅
     */
    public static void cancel() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}



