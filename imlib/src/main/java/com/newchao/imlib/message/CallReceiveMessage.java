package com.newchao.imlib.message;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CallReceiveMessage extends Data implements Parcelable {
    private String roomId;
    private String sendType;
    private String userId;
    private String type;

    private String targetId;
    private short cmd;
    private String extra = "";
    private List<String> members;
    private String param;

    public CallReceiveMessage(short cmd, String roomId, String sendType, String targetId, String type, String userId, List<String> members) {
        this.cmd = cmd;
        this.roomId = roomId;
        this.sendType = sendType;
        this.targetId = targetId;
        this.type = type;
        this.userId = userId;
        this.members = members;
    }


    protected CallReceiveMessage(Parcel in) {
        cmd = (short) in.readInt();
        roomId = in.readString();
        userId = in.readString();
        type = in.readString();
        sendType = in.readString();
        targetId = in.readString();
        extra = in.readString();
        members = new ArrayList<>();
        in.readStringList(members);
        param = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cmd);
        dest.writeString(roomId);
        dest.writeString(userId);
        dest.writeString(type);
        dest.writeString(sendType);
        dest.writeString(targetId);
        dest.writeString(extra);
        dest.writeStringList(members);
        dest.writeString(param);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CallReceiveMessage> CREATOR = new Creator<CallReceiveMessage>() {
        @Override
        public CallReceiveMessage createFromParcel(Parcel in) {
            return new CallReceiveMessage(in);
        }

        @Override
        public CallReceiveMessage[] newArray(int size) {
            return new CallReceiveMessage[size];
        }
    };

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "CallReceiveMessage{" +
                "roomId='" + roomId + '\'' +
                ", sendType='" + sendType + '\'' +
                ", userId='" + userId + '\'' +
                ", type='" + type + '\'' +
                ", targetId='" + targetId + '\'' +
                ", cmd=" + cmd +
                ", extra='" + extra + '\'' +
                ", members=" + members +
                ", param='" + param + '\'' +
                '}';
    }
}
