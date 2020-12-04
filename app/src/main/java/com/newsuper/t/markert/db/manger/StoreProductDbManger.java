package com.newsuper.t.markert.db.manger;

import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.StoreProductDao;
import com.newsuper.t.markert.entity.StoreProduct;

import java.util.List;

public class StoreProductDbManger extends BaseDBManager<StoreProduct> {

    private StoreProductDbManger(){}

    @Override
    protected BaseDao<StoreProduct> getDataBaseDao() {
        return AppDatabase.getDatabase().getStoreProductDao();
    }

    @Override
    public int deleteAll() {
        StoreProductDao dao = (StoreProductDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<StoreProduct> loadAll() {
        StoreProductDao dao = (StoreProductDao) baseDao;
        return dao.loadAll();
    }

    public static StoreProductDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static StoreProductDbManger INSTANCE = new StoreProductDbManger();
    }
}
