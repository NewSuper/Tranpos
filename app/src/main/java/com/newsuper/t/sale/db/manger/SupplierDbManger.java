package com.newsuper.t.sale.db.manger;

import com.newsuper.t.sale.base.BaseDBManager;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.db.AppDatabase;
import com.newsuper.t.sale.db.dao.SupplierDao;
import com.newsuper.t.sale.entity.Supplier;

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
