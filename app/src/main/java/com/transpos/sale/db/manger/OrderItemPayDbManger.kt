package com.transpos.sale.db.manger

import com.transpos.sale.base.BaseDBManager
import com.transpos.sale.base.BaseDao
import com.transpos.sale.db.AppDatabase
import com.transpos.sale.db.dao.OrderItemPayDao
import com.transpos.sale.entity.OrderItemPay

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