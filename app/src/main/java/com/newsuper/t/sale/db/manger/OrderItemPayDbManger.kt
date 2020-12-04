package com.newsuper.t.sale.db.manger

import com.newsuper.t.sale.base.BaseDBManager
import com.newsuper.t.sale.base.BaseDao
import com.newsuper.t.sale.db.AppDatabase
import com.newsuper.t.sale.db.dao.OrderItemPayDao
import com.newsuper.t.sale.entity.OrderItemPay

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