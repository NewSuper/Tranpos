package com.qx.message;

import android.os.Parcel;

/**
 * author : zhangjingming
 * e-mail : jimstin@126.com
 * date   : 2021/1/11 20:31
 * desc   :
 * version: 1.0
 */
public class EventMessage extends MessageContent {

    private String event;
    private String extra = "";

    public EventMessage(String event, String extra) {
        this.event = event;
        this.extra = extra;
    }

    protected EventMessage(Parcel in) {
        this.event = in.readString();
        this.extra = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.event);
        dest.writeString(this.extra);
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public static final Creator<EventMessage> CREATOR = new Creator<EventMessage>() {
        @Override
        public EventMessage createFromParcel(Parcel in) {
            return new EventMessage(in);
        }

        @Override
        public EventMessage[] newArray(int size) {
            return new EventMessage[size];
        }
    };
}
