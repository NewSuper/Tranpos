package com.transpos.sale.db.manger

import com.transpos.sale.base.BaseDBManager
import com.transpos.sale.base.BaseDao
import com.transpos.sale.db.AppDatabase
import com.transpos.sale.db.dao.OrderObjectDao
import com.transpos.sale.entity.OrderObject

object OrderObjectDbManger : BaseDBManager<OrderObject>() {

    override fun getDataBaseDao(): BaseDao<OrderObject> {
        return AppDatabase.getDatabase().orderObjectDao
    }

    override fun deleteAll(): Int {
        val dao : OrderObjectDao = baseDao as OrderObjectDao
        return dao.deleteAll()
    }

    override fun loadAll(): MutableList<OrderObject> {
        val dao : OrderObjectDao = baseDao as OrderObjectDao
        return dao.loadAll()
    }

    fun checkOrder(tradeNo : String) : OrderObject{
        val dao : OrderObjectDao = baseDao as OrderObjectDao
        return dao.checkOrderByNo(tradeNo)
    }
}