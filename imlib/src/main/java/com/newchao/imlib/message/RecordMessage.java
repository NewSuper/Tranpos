package com.newchao.imlib.message;

import android.os.Parcel;

import com.newchao.imlib.utils.UUIDUtil;

import java.util.List;

public class RecordMessage extends MessageContent {

    private String messageId;
    /**
     * 被回复的消息
     */
    private List<Message> messages;
    private String extra = "";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(messageId);
        dest.writeList(messages);
        dest.writeString(extra);
    }

    protected RecordMessage(Parcel in) {
        this.messageId = in.readString();
        this.messages = in.readArrayList(Message.class.getClassLoader());
        this.extra = in.readString();
    }

    public RecordMessage(List<Message> messages, String extra) {
        this.messageId = UUIDUtil.getUUID();
        this.messages = messages;
        this.extra = extra;
    }

    public static final Creator<RecordMessage> CREATOR = new Creator<RecordMessage>() {
        @Override
        public RecordMessage createFromParcel(Parcel in) {
            return new RecordMessage(in);
        }

        @Override
        public RecordMessage[] newArray(int size) {
            return new RecordMessage[size];
        }
    };

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
