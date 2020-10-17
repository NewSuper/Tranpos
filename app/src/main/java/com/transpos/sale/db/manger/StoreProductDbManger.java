package com.transpos.sale.db.manger;

import com.transpos.sale.base.BaseDBManager;
import com.transpos.sale.base.BaseDao;
import com.transpos.sale.db.AppDatabase;
import com.transpos.sale.db.dao.StoreProductDao;
import com.transpos.sale.entity.StoreProduct;

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
