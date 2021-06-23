package com.qx.message

import android.os.Parcel
import android.os.Parcelable
import com.qx.imlib.utils.UUIDUtil

class Conversation() : Data(), Comparable<Conversation> {

    /**
     * 会话id
     */
    var conversationId: String = UUIDUtil.getUUID() + System.currentTimeMillis()//会话ID自动生成即可

    /**
     * 会话拥有者id，UI层暂时用不上此字段
     */
    var ownerId: String = ""

    /**
     *  会话类型
     */
    var conversationType: String = ""

    /**
     * 会话对象id，可能是用户id、群id、群里id
     */
    var targetId: String = ""

    /**
     * 图标
     */
    var icon: String = ""

    /**
     * 聊天对象名称，可以是群名、用户名、系统消息
     */
    var targetName: String = ""

    /**
     * 草稿内容
     */
    var draft: String = ""


    /**
     * @内容
     */
    var atTo: String = ""

    /**
     * 消息未读数
     */
    var unReadCount: Int = 0

    /**
     * 免打扰,0关闭，1开启
     */
    var noDisturbing: Int = 0

    /**
     * 置顶，0正常（非置顶），1置顶
     */
    var top: Int = 0


    /**
     * 是否已删除，0正常（非删除），1已删除
     */
    var deleted: Int = 0

    /**
     * 聊天背景url
     */
    var background: String? = ""

    /**
     * 最新一条消息
     */
    var lastMessage: Message? = null

    /**
     * 最新一条消息的时间
     */
    var timestamp: Long = System.currentTimeMillis()

    /**
     * 记录清空数据时的最新一条消息的时间，该字段只有在拉取历史消息的时候有用，当拉取的历史消息的时间小于该时间时，则不插入数据库
     */
    var deleteTime: Long = -1L

    /**
     * 时间指示器，默认未-1（当本地数据为空或者本地已完全同步服务器最新数据时，设置为-1），否则为当次从数据库获取的消息中时间最旧的一条消息的时间
     * 如：从服务器上获取第1000-900跳数据（时间按递减排序，第900条记录的时间最旧），则timeIndicator的时间为第900条的时间，以此类推。
     */
    var timeIndicator: Long = -1L

    override fun hashCode(): Int {
        return conversationId.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return conversationId.equals((other as Conversation).conversationId)
    }


    /**
     * 最顶条消息时间记录字段，默认为-1，只有当本地数据库完全同步服务器的数据后，topTime才会更新，此时topTime为最新消息的时间
     */
    var topTime: Long = -1
    override fun compareTo(other: Conversation): Int {
        //两个实例都置顶或都置顶
        if (this.top == other.top) {
            return when {
                this.timestamp - other.timestamp > 0 -> {
                    -1
                }
                this.timestamp - other.timestamp < 0 -> {
                    1
                }
                else -> {
                    0
                }
            }
        } else {
            //只有一个置顶
            return if (this.top == 1) {
                -1
            } else {
                1
            }
        }

    }

    /**
     * 是否是新创建的会话
     */
    var isNew = false

    constructor(parcel: Parcel) : this() {
        conversationId = parcel.readString()!!
        ownerId = parcel.readString()!!
        conversationType = parcel.readString()!!
        targetId = parcel.readString()!!
        icon = parcel.readString()!!
        targetName = parcel.readString()!!
        lastMessage = parcel.readParcelable(Message::class.java.classLoader)
        draft = parcel.readString()!!
        atTo = parcel.readString()!!
        unReadCount = parcel.readInt()
        noDisturbing = parcel.readInt()
        top = parcel.readInt()
        deleted = parcel.readInt()
        background = parcel.readString()
        timestamp = parcel.readLong()
        deleteTime = parcel.readLong()
        timeIndicator = parcel.readLong()
        topTime = parcel.readLong()
        isNew = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(conversationId)
        parcel.writeString(ownerId)
        parcel.writeString(conversationType)
        parcel.writeString(targetId)
        parcel.writeString(icon)
        parcel.writeString(targetName)
        parcel.writeParcelable(lastMessage, flags)
        parcel.writeString(draft)
        parcel.writeString(atTo)
        parcel.writeInt(unReadCount)
        parcel.writeInt(noDisturbing)
        parcel.writeInt(top)
        parcel.writeInt(deleted)
        parcel.writeString(background)
        parcel.writeLong(timestamp)
        parcel.writeLong(deleteTime)
        parcel.writeLong(timeIndicator)
        parcel.writeLong(topTime)
        parcel.writeByte(if (isNew) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Conversation> {
        override fun createFromParcel(parcel: Parcel): Conversation {
            return Conversation(parcel)
        }

        override fun newArray(size: Int): Array<Conversation?> {
            return arrayOfNulls(size)
        }
    }

    object Type {
        /**
         * 单聊
         */
        const val TYPE_PRIVATE = "PRIVATE"

        /**
         * 群聊
         */
        const val TYPE_GROUP = "GROUP"

        /**
         * 聊天室
         */
        const val TYPE_CHAT_ROOM = "CHATROOM"

        /**
         * 系统消息
         */
        const val TYPE_SYSTEM = "SYSTEM"
    }

    object TopState {
        /**
         * 置顶状态
         */
        const val TOP_STATE_ENABLE = 1//置顶已启动
        const val TOP_STATE_DISABLE = 0//置顶已取消
    }

    object NoDisturbingState {
        /**
         * 免打扰状态
         */
        const val NO_DIST_STATE_ENABLE = 1//免打扰已启动
        const val NO_DIST_STATE_DISABLE = 0//免打扰已取消
    }
}