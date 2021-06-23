package com.newchao.imlib.message;

import android.os.Parcel;
import android.os.Parcelable;

public class SensitiveWordResult implements Parcelable {
    private boolean isBan;
    private String text;

    public SensitiveWordResult(boolean isBan, String text) {
        this.isBan = isBan;
        this.text = text;
    }

    protected SensitiveWordResult(Parcel in) {
        isBan = in.readByte() != 0;
        text = in.readString();
    }

    public static final Creator<SensitiveWordResult> CREATOR = new Creator<SensitiveWordResult>() {
        @Override
        public SensitiveWordResult createFromParcel(Parcel in) {
            return new SensitiveWordResult(in);
        }

        @Override
        public SensitiveWordResult[] newArray(int size) {
            return new SensitiveWordResult[size];
        }
    };

    public boolean isBan() {
        return isBan;
    }

    public void setBan(boolean ban) {
        isBan = ban;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isBan ? 1 : 0));
        dest.writeString(text);
    }
}
