package com.newsuper.t.markert.db.manger

import com.newsuper.t.markert.base.BaseDBManager
import com.newsuper.t.markert.base.BaseDao
import com.newsuper.t.markert.db.AppDatabase
import com.newsuper.t.markert.db.dao.OrderPayDao
import com.newsuper.t.markert.entity.OrderPay

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