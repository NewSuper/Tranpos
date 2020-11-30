package com.newsuper.t.consumer.manager;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public final class RxBus {
    private final Subject<Object> bus;
    private RxBus() {
        // toSerialized method made bus thread safe
        bus = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstace(){
        return InstanceHolder.RxBus_Default;
    }
    private  static class InstanceHolder{
        public static final RxBus RxBus_Default = new RxBus();
    }

    public void post(Object o){
        bus.onNext(o);
    }
    public <T> Observable<T> toObservable(Class<T> tClass) {
        return bus.ofType(tClass);
    }
    public Observable<Object> toObservable() {
        return bus;
    }
    public boolean hasObervers(){
        return bus.hasObservers();
    }
}
