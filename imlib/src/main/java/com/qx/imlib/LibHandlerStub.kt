package com.qx.imlib

import android.content.Context
import com.qx.message.ICustomEventProvider

internal class LibHandlerStub constructor(private val mContext: Context,private val mAppkey:String)
    :IHandler.Stub(){
    override fun connectServer(
        token: String?,
        imServerUrl: String?,
        callback: IConnectStringCallback?
    ) {
        TODO("Not yet implemented")
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }

    override fun setConversationNoDisturbing(
        conversationId: String?,
        isNoDisturbing: Boolean,
        callback: IResultCallback?
    ) {
        TODO("Not yet implemented")
    }

    override fun startCall(
        conversationType: String?,
        targetId: String?,
        roomId: String?,
        callType: String?,
        userIds: MutableList<String>?,
        callback: IResultCallback?
    ) {
        TODO("Not yet implemented")
    }

    override fun acceptCall(roomId: String?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun cancelCall(roomId: String?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun refuseCall(roomId: String?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun hangUp(roomId: String?, userId: String?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun setChatRoom(
        chatRoomId: String?,
        name: String?,
        value: String?,
        autDel: Int,
        callback: IResultCallback?
    ) {
        TODO("Not yet implemented")
    }

    override fun delChatRoom(chatRoomId: String?, name: String?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun getChatRoom(chatRoomId: String?, name: String?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun joinChatRoom(chatRoomId: String?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun exitChatRoom(chatRoomId: String?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun saveOnly(message: com.qx.message.Message?, callback: ISendMessageCallback?) {
        TODO("Not yet implemented")
    }

    override fun sendOnly(message: com.qx.message.Message?, callback: ISendMessageCallback?) {
        TODO("Not yet implemented")
    }

    override fun updateMessageState(messageId: String?, state: Int): Int {
        TODO("Not yet implemented")
    }

    override fun updateMessageStateAndTime(messageId: String?, timestamp: Long, state: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getConversationProperty(
        conversation: com.qx.message.Conversation?,
        callback: IResultCallback?
    ) {
        TODO("Not yet implemented")
    }

    override fun sendHeartBeat() {
        TODO("Not yet implemented")
    }

    override fun sendLogout(pushName: String?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun sendMessageReadReceipt(
        conversationType: String?,
        targetId: String?,
        lastTimestamp: Long,
        callback: IResultCallback?
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteRemoteMessageByMessageId(
        conversationType: String?,
        targetId: String?,
        messageIds: MutableList<String>?,
        callback: IResultCallback?
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteRemoteMessageByTimestamp(
        conversationType: String?,
        targetId: String?,
        timestamp: Long,
        callback: IResultCallback?
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteLocalMessageByTimestamp(
        conversationType: String?,
        targetId: String?,
        timestamp: Long
    ): Int {
        TODO("Not yet implemented")
    }

    override fun updateOriginPath(message: com.qx.message.Message?): Int {
        TODO("Not yet implemented")
    }

    override fun updateLocalPath(message: com.qx.message.Message?): Int {
        TODO("Not yet implemented")
    }

    override fun updateHearUrl(messageId: String?, headUrl: String?): Int {
        TODO("Not yet implemented")
    }

    override fun sendRecall(message: com.qx.message.Message?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun deleteLocalMessageById(messageIds: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun setConversationTop(
        conversationId: String?,
        isTop: Boolean,
        callback: IResultCallback?
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteConversation(conversationId: String?): Int {
        TODO("Not yet implemented")
    }

    override fun deleteConversationByTargetId(type: String?, targetId: String?): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAllConversation(): Int {
        TODO("Not yet implemented")
    }

    override fun searchTextMessage(content: String?): MutableList<com.qx.message.Message> {
        TODO("Not yet implemented")
    }

    override fun searchTextMessageByConversationId(
        content: String?,
        conversationId: String?
    ): MutableList<com.qx.message.Message> {
        TODO("Not yet implemented")
    }

    override fun getMessages(
        conversationType: String?,
        targetId: String?,
        offset: Int,
        pageSize: Int
    ): MutableList<com.qx.message.Message> {
        TODO("Not yet implemented")
    }

    override fun getHistoryMessageFromLocal(
        conversationId: String?,
        conversationType: String?,
        targetId: String?,
        offset: Int,
        pageSize: Int
    ): MutableList<com.qx.message.Message> {
        TODO("Not yet implemented")
    }

    override fun getHistoryMessageFromService(
        conversationType: String?,
        targetId: String?,
        timestamp: Long,
        searchType: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getAllMessages(): MutableList<com.qx.message.Message> {
        TODO("Not yet implemented")
    }

    override fun getMessagesByConversationID(conversationId: String?): MutableList<com.qx.message.Message> {
        TODO("Not yet implemented")
    }

    override fun getLatestMessageByConversationId(
        conversationId: String?,
        ownerId: String?
    ): com.qx.message.Message {
        TODO("Not yet implemented")
    }

    override fun getMessagesByTimestamp(
        conversationType: String?,
        targetId: String?,
        timestamp: Long,
        searchType: Int,
        pageSzie: Int
    ): MutableList<com.qx.message.Message> {
        TODO("Not yet implemented")
    }

    override fun getMessagesByType(
        conversationId: String?,
        types: MutableList<String>?,
        offset: Int,
        pageSize: Int,
        isAll: Boolean,
        isDesc: Boolean
    ): MutableList<com.qx.message.Message> {
        TODO("Not yet implemented")
    }

    override fun getUnReadAtMessages(conversationId: String?): MutableList<com.qx.message.Message> {
        TODO("Not yet implemented")
    }

    override fun getFirstUnReadMessage(conversationId: String?): com.qx.message.Message {
        TODO("Not yet implemented")
    }

    override fun getConversation(
        conversationType: String?,
        targetId: String?
    ): com.qx.message.Conversation {
        TODO("Not yet implemented")
    }

    override fun getAllConversation(): MutableList<com.qx.message.Conversation> {
        TODO("Not yet implemented")
    }

    override fun getConversationInRegion(region: MutableList<String>?): MutableList<com.qx.message.Conversation> {
        TODO("Not yet implemented")
    }

    override fun updateConversationDraft(conversationId: String?, draft: String?): Int {
        TODO("Not yet implemented")
    }

    override fun updateConversationTitle(type: String?, targetId: String?, title: String?): Int {
        TODO("Not yet implemented")
    }

    override fun updateConversationIcon(type: String?, targetId: String?, icon: String?): Int {
        TODO("Not yet implemented")
    }

    override fun updateConversationTitleAndIcon(
        type: String?,
        targetId: String?,
        title: String?,
        icon: String?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun clearMessages(conversationId: String?): Int {
        TODO("Not yet implemented")
    }

    override fun setReceiveMessageListener(listener: IOnReceiveMessageListener?) {
        TODO("Not yet implemented")
    }

    override fun setMessageReceiptListener(listener: IMessageReceiptListener?) {
        TODO("Not yet implemented")
    }

    override fun addOnChatNoticeReceivedListener(listener: IOnChatNoticeReceivedListener?) {
        TODO("Not yet implemented")
    }

    override fun setOnChatRoomMessageReceiveListener(listener: IOnChatRoomMessageReceiveListener?) {
        TODO("Not yet implemented")
    }

    override fun setConversationListener(listener: IConversationListener?) {
        TODO("Not yet implemented")
    }

    override fun setConnectionStatusListener(listener: IConnectionStatusListener?) {
        TODO("Not yet implemented")
    }

    override fun setCallReceiveMessageListener(listener: ICallReceiveMessageListener?) {
        TODO("Not yet implemented")
    }

    override fun switchAudioCall(roomId: String?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun getServerHost(): String {
        TODO("Not yet implemented")
    }

    override fun getCurUserId(): String {
        TODO("Not yet implemented")
    }

    override fun getHttpHost(): String {
        TODO("Not yet implemented")
    }

    override fun getRSAKey(): String {
        TODO("Not yet implemented")
    }

    override fun sendCallError(roomId: String?, targetId: String?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun searchConversations(
        keyWord: String?,
        conversationTypes: Array<out String>?,
        messageTypes: Array<out String>?
    ): MutableList<com.qx.im.model.SearchConversationResult>? {
        TODO("Not yet implemented")
    }

    override fun getAllUnReadCount(region: MutableList<String>?): Int {
        TODO("Not yet implemented")
    }

    override fun getConversationUnReadCount(
        conversationId: String?,
        isIgnoreNoDisturbing: Boolean
    ): Int {
        TODO("Not yet implemented")
    }

    override fun checkSensitiveWord(text: String?): com.qx.message.SensitiveWordResult {
        TODO("Not yet implemented")
    }

    override fun registerCustomEventProvider(provider: ICustomEventProvider?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isMessageExist(messageId: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun checkSendingMessage(): MutableList<com.qx.message.Message> {
        TODO("Not yet implemented")
    }

    override fun updateConversationBackground(url: String?, conversationId: String?): Int {
        TODO("Not yet implemented")
    }

    override fun setUserProperty(
        property: com.qx.message.UserProperty?,
        callback: IResultCallback?
    ) {
        TODO("Not yet implemented")
    }

    override fun rtcJoin(join: com.qx.message.rtc.RTCJoin?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun rtcJoined(joined: com.qx.message.rtc.RTCJoined?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun rtcOffer(offer: com.qx.message.rtc.RTCOffer?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun rtcAnswer(offer: com.qx.message.rtc.RTCOffer?, callback: IResultCallback?) {
        TODO("Not yet implemented")
    }

    override fun rtcCandidate(
        candidate: com.qx.message.rtc.RTCCandidate?,
        callback: IResultCallback?
    ) {
        TODO("Not yet implemented")
    }

    override fun setRTCSignalMessageListener(lisntener: IRTCMessageListener?) {
        TODO("Not yet implemented")
    }

    override fun getRTCConfig(): com.qx.im.model.RTCServerConfig {
        TODO("Not yet implemented")
    }

    override fun updateAtMessageReadState(
        messageId: String?,
        conversationId: String?,
        read: Int
    ): Int {
        TODO("Not yet implemented")
    }

    override fun clearAtMessage(conversationId: String?): Int {
        TODO("Not yet implemented")
    }

    override fun updateCustomMessage(
        conversationId: String?,
        messageId: String?,
        content: String?,
        extra: String?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun rtcVideoParam(
        param: com.qx.message.rtc.RTCVideoParam?,
        callback: IResultCallback?
    ) {
        TODO("Not yet implemented")
    }

    override fun openDebugLog() {
        TODO("Not yet implemented")
    }
}