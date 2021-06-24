package com.qx.imlib.utils.event;


import com.qx.imlib.ConversationUtil;
import com.qx.imlib.db.entity.ConversationEntity;
import com.qx.imlib.db.entity.MessageEntity;
import com.qx.imlib.job.JobManagerUtil;
import com.qx.message.Conversation;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class EventBusUtil {
    public static void post(Object object) {
        EventBus.getDefault().post(object);
    }

    public static void postConversationUpdate(List<ConversationEntity> list) {
        List<Conversation> result = new ArrayList<>();
        for (ConversationEntity entity : list) {
            result.add(ConversationUtil.INSTANCE.toConversation(entity));
        }
        postUi(result, BaseEvent.EventName.EVENT_NAME_CONVERSATION_UPDATE);
    }

    public static void postChatNotice(ChatNotice notice) {
        postUi(notice, BaseEvent.EventName.EVENT_NAME_CHAT_NOTICE);
    }

    public static void postMessageRead() {
        postUi(null, BaseEvent.EventName.EVENT_NAME_MESSAGE_READ);
    }

    public static void postChatRoomDestroy() {
        postUi(null, BaseEvent.EventName.EVENT_NAME_CHAT_ROOM_DESTROY);
    }

    public static void postInputStatusMessage(String from) {
        postUi(from, BaseEvent.EventName.EVENT_NAME_INPUT_STATUS_MESSAGE);
    }

    public static void postRecallMessage(MessageEntity entity) {
        postUi(entity, BaseEvent.EventName.EVENT_NAME_RECALL_MESSAGE);
    }

    public static void postMessageStateChanged(MessageEntity entity) {
        postUi(entity, BaseEvent.EventName.EVENT_NAME_MESSAGE_STATE_CHANGED);
    }

    public static void postNewMessageEntity(List<MessageEntity> list) {
        postUi(list, BaseEvent.EventName.EVENT_NAME_NEW_MESSAGE);
    }

    public static void postNewChatRoomMessageEntity(MessageEntity entity) {
        postUi(entity, BaseEvent.EventName.EVENT_NAME_NEW_CHAT_ROOM_MESSAGE);
    }

    public static void postHistoryMessage(List<MessageEntity> list) {
        postUi(list, BaseEvent.EventName.EVENT_NAME_HISTORY_MESSAGE);
    }

    public static void postP2POfflineMessage(List<MessageEntity> list) {
        postUi(list, BaseEvent.EventName.EVENT_NAME_P2P_OFFLINE_MESSAGE);
    }

    public static void postGroupOfflineMessage(List<MessageEntity> list) {
        postUi(list, BaseEvent.EventName.EVENT_NAME_GROUP_OFFLINE_MESSAGE);
    }

    public static void postSystemOfflineMessage(List<MessageEntity> list) {
        postUi(list, BaseEvent.EventName.EVENT_NAME_SYSTEM_OFFLINE_MESSAGE);
    }

    public static void postUi(Object entity, BaseEvent.EventName eventName) {
        BaseEvent even = new BaseEvent(entity, eventName);
        JobManagerUtil.Companion.getInstance().postUiNotify(even);
        // EventBus.getDefault().post(even);
    }


}
