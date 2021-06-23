package com.qx.message;


import android.os.Parcel;

public class CustomMessage extends MessageContent {

    private String content;
    private String extra = "";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CustomMessage(String content) {
        this.content = content;
    }
    protected CustomMessage(Parcel in) {
        content = in.readString();
        extra = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(extra);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CustomMessage> CREATOR = new Creator<CustomMessage>() {
        @Override
        public CustomMessage createFromParcel(Parcel in) {
            return new CustomMessage(in);
        }

        @Override
        public CustomMessage[] newArray(int size) {
            return new CustomMessage[size];
        }
    };

    @Override
    public String toString() {
        return "CustomMessage{" +
                "content='" + content + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
