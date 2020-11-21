package com.newsuper.t.juejinbao.base;

import android.os.Message;

import com.squareup.otto.Bus;

public class BusProvider extends Bus{

    private volatile static BusProvider bus;

    private BusProvider() {
    }

    public static BusProvider getInstance() {
        if (bus == null) {
            synchronized (BusProvider.class) {
                if (bus == null) {
                    bus = new BusProvider();
                }
            }
        }
        return bus;
    }


    public static Message createMessage(int what) {
        Message msg = new Message();
        msg.what = what;
        return msg;
    }

    public static Message createMessage(int what, int arg1) {
        Message msg = new Message();
        msg.what = what;
        msg.arg1 = arg1;
        return msg;
    }

    public static Message createMessage(int what, int arg1, int arg2) {
        Message msg = new Message();
        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        return msg;
    }

    public static Message createMessage(int what, int arg1, int arg2, Object o) {
        Message msg = new Message();
        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.obj = o;
        return msg;
    }

    public static Message createMessage(int what, Object o) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = o;
        return msg;
    }

}
