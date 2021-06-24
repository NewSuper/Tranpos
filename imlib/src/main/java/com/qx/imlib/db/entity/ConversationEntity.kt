package com.qx.imlib.db.entity

import androidx.room.*
import com.qx.imlib.utils.UUIDUtil
import java.io.Serializable

@Entity(tableName = "conversation")
class ConversationEntity :Serializable, Comparable<ConversationEntity>{

    /**
     * 消息id
     */
    @PrimaryKey
    @ColumnInfo(name = "conversation_id")
    var conversationId: String = UUIDUtil.getUUID()//会话ID自动生成即可

    @ColumnInfo(name = "owner_id")
    var ownerId : String = ""
    /**
     * @see com.qx.mvvm.message.ConversationType 会话类型
     */
    @ColumnInfo(name = "conversation_type")
    var conversationType: String = ""

    /**
     * 接收方的id，可能是用户id、群id、群里id
     */
    @ColumnInfo(name = "target_id")
    var targetId: String = ""

    @ColumnInfo(name = "icon")
    var icon: String = ""

    @ColumnInfo(name = "target_name")
    var targetName: String = ""

    /**
     * @内容
     */
    @ColumnInfo(name = "at_to")
    var atTo: String = ""

    /**
     * 草稿内容
     */
    @ColumnInfo(name = "draft")
    var draft: String = ""
    /**
     * 消息未读数
     */
    @ColumnInfo(name = "un_read_count")
    var unReadCount: Int = 0

    /**
     * 免打扰,0关闭，1开启
     */
    @ColumnInfo(name = "no_disturbing")
    var noDisturbing: Int = 0

    /**
     * 置顶，0正常（非置顶），1置顶
     */
    @ColumnInfo(name = "is_top")
    var top: Int = 0


    /**
     * 是否已删除，0正常（非删除），1已删除
     */
    @ColumnInfo(name = "deleted")
    var deleted: Int = 0

    /**
     * 聊天背景url
     */
    @ColumnInfo(name = "background")
    var background: String = ""


    /**
     * 最后一条消息的时间
     */
    @ColumnInfo(name = "timestamp")
    var timestamp: Long = System.currentTimeMillis()

    /**
     * 记录清空数据时的最新一条消息的时间，该字段只有在拉取历史消息的时候有用，当拉取的历史消息的时间小于该时间时，则不插入数据库
     */
    @ColumnInfo(name = "delete_time")
    var deleteTime: Long = -1L

    /**
     * 时间指示器，默认未-1（当本地数据为空或者本地已完全同步服务器最新数据时，设置为-1），否则为当次从数据库获取的消息中时间最旧的一条消息的时间
     * 如：从服务器上获取第1000-900跳数据（时间按递减排序，第900条记录的时间最旧），则timeIndicator的时间为第900条的时间，以此类推。
     */
    @ColumnInfo(name = "time_indicator")
    var timeIndicator: Long = -1L

    override fun hashCode(): Int {
        return conversationId.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return conversationId == (other as ConversationEntity).conversationId
    }

    /**
     * 最顶条消息时间记录字段，默认为-1，只有当本地数据库完全同步服务器的数据后，topTime才会更新，此时topTime为最新消息的时间
     */
    @ColumnInfo(name = "top_time")
    var topTime : Long = -1
    override fun compareTo(other: ConversationEntity): Int {
        //两个实例都置顶或都置顶
        if(this.top == other.top) {
            return when {
                this.timestamp - other.timestamp > 0 -> {
                    -1
                }
                this.timestamp - other.timestamp < 0 -> {
                    1
                }
                else                                 -> {
                    0
                }
            }
        } else {
            //只有一个置顶
            return if(this.top ==1) {
                -1
            } else {
                1
            }
        }

    }

    /**
     * 是否是新创建的会话
     */
    @Ignore
    var isNew = false
}