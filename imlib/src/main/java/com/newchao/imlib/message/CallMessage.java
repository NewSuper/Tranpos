package com.newchao.imlib.message;


import android.os.Parcel;

public class CallMessage extends MessageContent {


    private String room_id;
    //通话类型, "1"音频， "0"视频
    private String callType;
    // 通话状态
    private int callState;
    private String userIds;
    private String content;
    // 通话时长
    private long duration;
    // 0 正常结束，1 已取消 2 已拒绝 3 连接异常 4 连接断开 5 对方无响应 6 对方忙碌
    private int endType;
    private String extra = "";

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public int getCallState() {
        return callState;
    }

    public void setCallState(int callState) {
        this.callState = callState;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getEndType() {
        return endType;
    }

    public void setEndType(int endType) {
        this.endType = endType;
    }

    public CallMessage(String room_id, String callType, int callState , String userIds , String content , long duration, int endType) {
        this.room_id = room_id;
        this.callType = callType;
        this.callState = callState;
        this.userIds = userIds;
        this.content = content;
        this.duration = duration;
        this.endType =endType;
    }
    protected CallMessage(Parcel in) {
        room_id = in.readString();
        callType = in.readString();
        callState = in.readInt();
        userIds = in.readString();
        content = in.readString();
        duration = in.readLong();
        endType = in.readInt();
        extra = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(room_id);
        dest.writeString(callType);
        dest.writeInt(callState);
        dest.writeString(userIds);
        dest.writeString(content);
        dest.writeLong(duration);
        dest.writeInt(endType);
        dest.writeString(extra);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CallMessage> CREATOR = new Creator<CallMessage>() {
        @Override
        public CallMessage createFromParcel(Parcel in) {
            return new CallMessage(in);
        }

        @Override
        public CallMessage[] newArray(int size) {
            return new CallMessage[size];
        }
    };

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public class CallType {
        public static final String CALL_TYPE_VIDEO = "0";
        public static final String CALL_TYPE_AUDIO = "1";
    }

    @Override
    public String toString() {
        return "CallMessage{" +
                "room_id='" + room_id + '\'' +
                ", callType='" + callType + '\'' +
                ", callState=" + callState +
                ", userIds='" + userIds + '\'' +
                ", content='" + content + '\'' +
                ", duration=" + duration +
                ", endType=" + endType +
                ", extra='" + extra + '\'' +
                '}';
    }
}
