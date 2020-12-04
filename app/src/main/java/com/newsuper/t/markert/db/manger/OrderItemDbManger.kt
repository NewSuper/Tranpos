package com.newsuper.t.markert.db.manger

import com.newsuper.t.markert.base.BaseDBManager
import com.newsuper.t.markert.base.BaseDao
import com.newsuper.t.markert.db.AppDatabase
import com.newsuper.t.markert.db.dao.OrderItemDao
import com.newsuper.t.markert.entity.OrderItem


object OrderItemDbManger : BaseDBManager<OrderItem>(){


    override fun getDataBaseDao(): BaseDao<OrderItem> {
        return AppDatabase.getDatabase().orderItemDao
    }

    override fun delete(bean: OrderItem?): Int {
        val dao : OrderItemDao = baseDao as OrderItemDao
        return dao.delete(bean)
    }

    override fun loadAll(): MutableList<OrderItem> {
        val dao : OrderItemDao = baseDao as OrderItemDao
        return dao.loadAll()
    }

    fun findOrderItemsByTradeno(tradeNo : String) : MutableList<OrderItem>{
        val dao : OrderItemDao = baseDao as OrderItemDao
        return dao.findByTradeNo(tradeNo)
    }

}