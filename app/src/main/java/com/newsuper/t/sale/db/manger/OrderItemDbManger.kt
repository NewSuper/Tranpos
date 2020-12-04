package com.newsuper.t.sale.db.manger

import com.newsuper.t.sale.base.BaseDBManager
import com.newsuper.t.sale.base.BaseDao
import com.newsuper.t.sale.db.AppDatabase
import com.newsuper.t.sale.db.dao.OrderItemDao
import com.newsuper.t.sale.entity.OrderItem

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