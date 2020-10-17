package com.transpos.sale.db.manger

import com.transpos.sale.base.BaseDBManager
import com.transpos.sale.base.BaseDao
import com.transpos.sale.db.AppDatabase
import com.transpos.sale.db.dao.OrderItemDao
import com.transpos.sale.entity.OrderItem

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