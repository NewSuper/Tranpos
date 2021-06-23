package com.qx.im.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.qx.message.Conversation;

public class SearchConversationResult implements Parcelable {

    private Conversation conversation;
    private int matchCount;

    public SearchConversationResult() {

    }

    public SearchConversationResult(Conversation conversation, int matchCount) {
        this.conversation = conversation;
        this.matchCount = matchCount;
    }

    protected SearchConversationResult(Parcel in) {
        conversation = in.readParcelable(Conversation.class.getClassLoader());
        matchCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(conversation, flags);
        dest.writeInt(matchCount);
    }

    public static final Creator<SearchConversationResult> CREATOR = new Creator<SearchConversationResult>() {
        @Override
        public SearchConversationResult createFromParcel(Parcel in) {
            return new SearchConversationResult(in);
        }

        @Override
        public SearchConversationResult[] newArray(int size) {
            return new SearchConversationResult[size];
        }
    };

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
