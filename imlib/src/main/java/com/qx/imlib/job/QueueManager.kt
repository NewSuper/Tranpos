package com.qx.imlib.job

import com.qx.imlib.netty.MessageTask
import com.qx.it.protos.S2CCustomMessage

class QueueManager {
    /**
     * @param key targetId 聊天对象id（群id、聊天室id）
     * @param value 队列持有器
     */
    private var mMap = HashMap<String,QueueHolder>()
    internal object Holder {
        val instance = QueueManager()
    }
    companion object{
        val instance =Holder.instance
    }
    fun receive(task:MessageTask){
        var msg = S2CCustomMessage.Msg.parseFrom(task.msg.contents)
        var holder = mMap[msg.to]
        if (holder == null){
            holder = QueueHolder()
            mMap[msg.to] = holder
        }
        holder.take(task)
    }

}