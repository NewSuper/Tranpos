package com.newsuper.t.markert.db.manger;

import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.SupplierDao;
import com.newsuper.t.markert.entity.Supplier;

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
