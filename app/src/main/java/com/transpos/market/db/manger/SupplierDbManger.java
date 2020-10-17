package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.SupplierDao;
import com.transpos.market.entity.Supplier;

import java.util.List;

public class SupplierDbManger extends BaseDBManager<Supplier> {
    private SupplierDbManger(){}

    @Override
    protected BaseDao<Supplier> getDataBaseDao() {
        return AppDatabase.getDatabase().getSupplierDao();
    }

    @Override
    public int deleteAll() {
        SupplierDao dao = (SupplierDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<Supplier> loadAll() {
        SupplierDao dao = (SupplierDao) baseDao;
        return dao.loadAll();
    }

    public static SupplierDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static SupplierDbManger INSTANCE = new SupplierDbManger();
    }
}
