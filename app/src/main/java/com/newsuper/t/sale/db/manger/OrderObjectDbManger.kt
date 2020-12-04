package com.newsuper.t.sale.db.manger

import com.newsuper.t.sale.base.BaseDBManager
import com.newsuper.t.sale.base.BaseDao
import com.newsuper.t.sale.db.AppDatabase
import com.newsuper.t.sale.db.dao.OrderObjectDao
import com.newsuper.t.sale.entity.OrderObject

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