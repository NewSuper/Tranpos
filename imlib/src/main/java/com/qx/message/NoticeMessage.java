package com.qx.message;


import android.os.Parcel;

public class NoticeMessage extends MessageContent {

    private String content;
    private String operateUser;
    private String users;
    private int type;
    private String extra = "";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public NoticeMessage(String content, String operateUser, String users, int type) {
        this.content = content;
        this.operateUser = operateUser;
        this.users = users;
        this.type = type;
    }

    protected NoticeMessage(Parcel in) {
        content = in.readString();
        operateUser = in.readString();
        users = in.readString();
        type = in.readInt();
        extra = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(operateUser);
        dest.writeString(users);
        dest.writeInt(type);
        dest.writeString(extra);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoticeMessage> CREATOR = new Creator<NoticeMessage>() {
        @Override
        public NoticeMessage createFromParcel(Parcel in) {
            return new NoticeMessage(in);
        }

        @Override
        public NoticeMessage[] newArray(int size) {
            return new NoticeMessage[size];
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
        return "NoticeMessage{" +
                "content='" + content + '\'' +
                ", operateUser='" + operateUser + '\'' +
                ", users='" + users + '\'' +
                ", type=" + type +
                ", extra='" + extra + '\'' +
                '}';
    }
}
