package com.qx.message;


import android.os.Parcel;

import com.google.gson.annotations.Expose;

public class AtToMessage extends MessageContent {

    private String atTo;

    @Expose(serialize = false)
    private int read;

    public String getAtTo() {
        return atTo;
    }

    public void setAtTo(String atTo) {
        this.atTo = atTo;
    }

    public AtToMessage(String atTo, int read) {
        this.atTo = atTo;
        this.read = read;
    }
    protected AtToMessage(Parcel in) {
        atTo = in.readString();
        read = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(atTo);
        dest.writeInt(read);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AtToMessage> CREATOR = new Creator<AtToMessage>() {
        @Override
        public AtToMessage createFromParcel(Parcel in) {
            return new AtToMessage(in);
        }

        @Override
        public AtToMessage[] newArray(int size) {
            return new AtToMessage[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AtToMessage that = (AtToMessage) o;

        if (read != that.read) return false;
        return atTo.equals(that.atTo);
    }

    @Override
    public int hashCode() {
        int result = atTo.hashCode();
        result = 31 * result + read;
        return result;
    }

    @Override
    public String toString() {
        return "AtToMessage{" +
                "atTo='" + atTo + '\'' +
                "read='" + read + '\'' +
                '}';
    }

    public static class ReadState {
        public static final int STATE_UN_READ = 0;
        public static final int STATE_READ = 1;
    }
}
