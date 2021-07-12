package com.qx.imlib.handler

import android.util.Log
import com.qx.im.model.ConversationType
import com.qx.im.model.InsertMessageResult
import com.qx.imlib.db.IMDatabaseRepository
import com.qx.imlib.utils.event.EventBusUtil

open class GroupOfflineMessageHandler:HistoryMessageUtilHandler() {
    private  val TAG = "GroupOfflineMessageHand"
    override fun notifyUiUpdate(result: InsertMessageResult) {
        Log.e(TAG, "notifyUiUpdate: 更新UI")
        if (result.messages.size>0){
            //在此叠加消息未读数
            IMDatabaseRepository.instance.addConversationUnReadCount(ConversationType.TYPE_GROUP,
            result.messages[0].from,result.messages[0].to,result.newMessageCount)
            EventBusUtil.postGroupOfflineMessage(result.messages)
        }
    }

    override fun isNeedCheckDeleteTime(): Boolean {
        return false
    }
}