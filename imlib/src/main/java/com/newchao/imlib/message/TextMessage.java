package com.newchao.imlib.message;


import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class TextMessage extends MessageContent {

    private String content;
    private List<AtToMessage> atToMessageList = new ArrayList<>();
    private String extra = "";
    public List<AtToMessage> getAtToMessageList() {
        return atToMessageList;
    }

    public void setAtToMessageList(List<AtToMessage> atToMessageList) {
        this.atToMessageList = atToMessageList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TextMessage(String content) {
        this.content = content;
    }
    protected TextMessage(Parcel in) {
        content = in.readString();
        atToMessageList = in.readArrayList(AtToMessage.class.getClassLoader());
        extra = in.readString();
        /*if(Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            atToMessageList = in.readArrayList(AtToMessage.class.getClassLoader());
        } else {
            atToMessageList = in.readParcelableList(atToMessageList,AtToMessage.class.getClassLoader());
        }*/

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeList(atToMessageList);
        dest.writeString(extra);
        /*if(Build.VERSION.SDK_INT <Build.VERSION_CODES.P) {
            dest.writeList(atToMessageList);
        } else {
            dest.writeParcelableList(atToMessageList,flags);
        }*/
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TextMessage> CREATOR    = new Creator<TextMessage>() {
        @Override
        public TextMessage createFromParcel(Parcel in) {
            return new TextMessage(in);
        }

        @Override
        public TextMessage[] newArray(int size) {
            return new TextMessage[size];
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
        return "TextMessage{" +
                "content='" + content + '\'' +
                ", atToMessageList=" + atToMessageList +
                ", extra='" + extra + '\'' +
                '}';
    }

    public static TextMessage obtain(String content) {
        return new TextMessage(content);
    }
}
