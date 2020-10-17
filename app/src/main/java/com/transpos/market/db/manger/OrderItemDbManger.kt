package com.transpos.market.db.manger

import com.transpos.market.base.BaseDBManager
import com.transpos.market.base.BaseDao
import com.transpos.market.db.AppDatabase
import com.transpos.market.db.dao.OrderItemDao
import com.transpos.market.entity.OrderItem


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