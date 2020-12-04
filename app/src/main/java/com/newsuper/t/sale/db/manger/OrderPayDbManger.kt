package com.newsuper.t.sale.db.manger

import com.newsuper.t.sale.base.BaseDBManager
import com.newsuper.t.sale.base.BaseDao
import com.newsuper.t.sale.db.AppDatabase
import com.newsuper.t.sale.db.dao.OrderPayDao
import com.newsuper.t.sale.entity.OrderPay

object OrderPayDbManger : BaseDBManager<OrderPay>() {
    override fun getDataBaseDao(): BaseDao<OrderPay> {
        return AppDatabase.getDatabase().orderPayDao
    }


    override fun deleteAll(): Int {
        val dao : OrderPayDao = baseDao as OrderPayDao
        return dao.deleteAll()
    }


    override fun loadAll(): MutableList<OrderPay> {
        val dao : OrderPayDao = baseDao as OrderPayDao
        return dao.loadAll()
    }

    fun findOderPayInfos(tradeNo: String) : MutableList<OrderPay>{
        val dao : OrderPayDao = baseDao as OrderPayDao
        return dao.findOderPayInfos(tradeNo)
    }
}