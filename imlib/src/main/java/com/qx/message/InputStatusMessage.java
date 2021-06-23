package com.qx.message;


import android.os.Parcel;

public class InputStatusMessage extends MessageContent {

    private String content;
    private String extra = "";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public InputStatusMessage(String content) {
        this.content = content;
    }
    protected InputStatusMessage(Parcel in) {
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InputStatusMessage> CREATOR = new Creator<InputStatusMessage>() {
        @Override
        public InputStatusMessage createFromParcel(Parcel in) {
            return new InputStatusMessage(in);
        }

        @Override
        public InputStatusMessage[] newArray(int size) {
            return new InputStatusMessage[size];
        }
    };

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "InputStatusMessage{" +
                "content='" + content + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }
}
