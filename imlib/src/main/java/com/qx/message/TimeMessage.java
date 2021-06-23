package com.qx.message;


import android.os.Parcel;

public class TimeMessage extends MessageContent {

    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public TimeMessage(long time) {
        this.time = time;
    }
    protected TimeMessage(Parcel in) {
        time = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimeMessage> CREATOR = new Creator<TimeMessage>() {
        @Override
        public TimeMessage createFromParcel(Parcel in) {
            return new TimeMessage(in);
        }

        @Override
        public TimeMessage[] newArray(int size) {
            return new TimeMessage[size];
        }
    };

    @Override
    public String toString() {
        return "TimeMessage{" +
                "time='" + time + '\'' +
                '}';
    }
}
