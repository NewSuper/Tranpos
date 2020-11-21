package com.newsuper.t.juejinbao.base;

import java.lang.ref.WeakReference;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V> {
    private CompositeSubscription mCompositeSubscription;
    private WeakReference<V> mViewRef;

    public void attachModelView(V pView) {
        mViewRef = new WeakReference<>(pView);
    }


    public V getView() {
        if (isAttach()) {
            return mViewRef.get();
        } else {
            return null;
        }
    }

    private boolean isAttach() {
        return null != mViewRef && null != mViewRef.get();
    }


    //增加订阅者
    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    //解绑订阅者
    public void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
