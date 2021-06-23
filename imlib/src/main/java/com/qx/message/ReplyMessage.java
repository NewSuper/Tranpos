package com.qx.message;

import android.os.Parcel;

import com.qx.imlib.utils.UUIDUtil;


public class ReplyMessage extends MessageContent{

    private String messageId;
    /**
     * 被回复的消息
     */
    private Message origin;
    /**
     * 回复的消息
     */
    private Message answer;
    private String extra = "";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(messageId);
        dest.writeParcelable(origin, flags);
        dest.writeParcelable(answer, flags);
        dest.writeString(extra);
    }

    protected ReplyMessage(Parcel in) {
        this.messageId = in.readString();
        this.origin = in.readParcelable(Message.class.getClassLoader());
        this.answer = in.readParcelable(Message.class.getClassLoader());
        this.extra = in.readString();
    }

    public ReplyMessage(Message origin, Message answer, String extra) {
        this.messageId = UUIDUtil.getUUID();
        this.origin = origin;
        this.answer = answer;
        this.extra = extra;
    }

    public static final Creator<ReplyMessage> CREATOR = new Creator<ReplyMessage>() {
        @Override
        public ReplyMessage createFromParcel(Parcel in) {
            return new ReplyMessage(in);
        }

        @Override
        public ReplyMessage[] newArray(int size) {
            return new ReplyMessage[size];
        }
    };

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Message getOrigin() {
        return origin;
    }

    public void setOrigin(Message origin) {
        this.origin = origin;
    }

    public Message getAnswer() {
        return answer;
    }

    public void setAnswer(Message answer) {
        this.answer = answer;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
