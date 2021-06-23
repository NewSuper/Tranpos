// IOnChatNoticeReceivedListener.aidl
package com.newchao.imlib;

// Declare any non-default types here with import statements

interface IOnChatNoticeReceivedListener {
     /**
      * 群全局禁言/解禁
      * @param isEnabled 是否启动，true为禁言，false为解禁
      */
     void onGroupGlobalMute(boolean isEnabled);

     /**
      * 群组成员禁言
      *@param groupId 群组Id
      * @param isEnabled 是否启动，true为禁言，false为解禁
      *
      */

     void onGroupMute(String groupId ,boolean isEnabled);

     /**
      * 群整体禁言。即：该群所有人都不能发消息
      * @param groupId 群组Id
      * @param isEnabled 是否启动，true为禁言，false为解禁
      */
     void onGroupAllMute(String groupId, boolean isEnabled);
     /**
      * 聊天室全局禁言/解禁
      * @param isEnabled 是否启动，true为禁言，false为解禁
      */
     void onChatRoomGlobalMute(boolean isEnabled);

     /**
      * 聊天室成员封禁，即：封禁后会被踢出聊天室，并无法再次进入聊天室，直到解禁
      * @param isEnabled 是否启动，true为封禁，false为解封
      */
     void onChatRoomBan(String chatRoomId, boolean isEnabled);

     /**
      * 聊天室成员禁言
      *@param chatRoomId 聊天室Id
      * @param isEnabled 是否启动，true为禁言，false为解禁
      *
      */
     void onChatRoomMute(String chatRoomId , boolean isEnabled);

     /**
      * 聊天室销毁
      */
     void onChatRoomDestroy();
}