package com.transpos.market.db.manger

import com.transpos.market.base.BaseDBManager
import com.transpos.market.base.BaseDao
import com.transpos.market.db.AppDatabase
import com.transpos.market.db.dao.OrderPayDao
import com.transpos.market.entity.OrderPay

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