package com.qx.imlib.utils.event;

public class BaseEvent {

    private Object eventBody;
    private EventName eventName;

    public BaseEvent(Object eventBody, EventName eventName) {
        this.eventBody = eventBody;
        this.eventName = eventName;
    }

    public EventName getEventName() {
        return eventName;
    }

    public void setEventName(EventName eventName) {
        this.eventName = eventName;
    }

    public Object getEventBody() {
        return eventBody;
    }

    public void setEventBody(Object eventBody) {
        this.eventBody = eventBody;
    }

    public enum EventName {
        EVENT_NAME_CONNECTION_STATUS,
        EVENT_NAME_NEW_MESSAGE,
        EVENT_NAME_NEW_CHAT_ROOM_MESSAGE,
        EVENT_NAME_CONVERSATION_UNREAD_COUNT,
        EVENT_NAME_MESSAGE_STATE_CHANGED,
        EVENT_NAME_RECALL_MESSAGE,
        EVENT_NAME_INPUT_STATUS_MESSAGE,
        EVENT_NAME_MESSAGE_READ,
        EVENT_NAME_HISTORY_MESSAGE,
        EVENT_NAME_P2P_OFFLINE_MESSAGE,
        EVENT_NAME_GROUP_OFFLINE_MESSAGE,
        EVENT_NAME_SYSTEM_OFFLINE_MESSAGE,
        EVENT_NAME_CHATROOM_ATTRIBUTE,
        EVENT_NAME_CHAT_NOTICE,
        EVENT_NAME_CHAT_ROOM_DESTROY,
        EVENT_NAME_USER_BAN,
        EVENT_NAME_CONVERSATION_UPDATE,
    }
}
