package com.newsuper.t.markert.db.manger

import com.newsuper.t.markert.base.BaseDBManager
import com.newsuper.t.markert.base.BaseDao
import com.newsuper.t.markert.db.AppDatabase
import com.newsuper.t.markert.db.dao.OrderItemPayDao
import com.newsuper.t.markert.entity.OrderItemPay


object OrderItemPayDbManger : BaseDBManager<OrderItemPay>() {
    override fun getDataBaseDao(): BaseDao<OrderItemPay> {
        return AppDatabase.getDatabase().orderItemPayDao
    }

    override fun deleteAll(): Int {
        val dao : OrderItemPayDao = baseDao as OrderItemPayDao
        return dao.deleteAll()
    }

    override fun loadAll(): MutableList<OrderItemPay> {
        val dao : OrderItemPayDao = baseDao as OrderItemPayDao
        return dao.loadAll()
    }
}