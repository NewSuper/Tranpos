package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.StoreProductDao;
import com.transpos.market.entity.StoreProduct;

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
