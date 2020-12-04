package com.newsuper.t.markert.db.manger

import com.newsuper.t.markert.base.BaseDBManager
import com.newsuper.t.markert.base.BaseDao
import com.newsuper.t.markert.db.AppDatabase
import com.newsuper.t.markert.db.dao.OrderObjectDao
import com.newsuper.t.markert.entity.OrderObject

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