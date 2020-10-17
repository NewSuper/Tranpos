package com.transpos.sale.db.manger

import com.transpos.sale.base.BaseDBManager
import com.transpos.sale.base.BaseDao
import com.transpos.sale.db.AppDatabase
import com.transpos.sale.db.dao.OrderPayDao
import com.transpos.sale.entity.OrderPay

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