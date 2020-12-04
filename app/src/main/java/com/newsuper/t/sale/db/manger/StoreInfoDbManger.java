package com.newsuper.t.sale.db.manger;

import com.newsuper.t.sale.base.BaseDBManager;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.db.AppDatabase;
import com.newsuper.t.sale.db.dao.StoreInfoDao;
import com.newsuper.t.sale.entity.StoreInfo;

import java.util.List;

public class StoreInfoDbManger extends BaseDBManager<StoreInfo> {
    private StoreInfoDbManger(){}

    @Override
    protected BaseDao<StoreInfo> getDataBaseDao() {
        return AppDatabase.getDatabase().getStoreInfoDao();
    }

    public static StoreInfoDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    @Override
    public int deleteAll() {
        StoreInfoDao dao = (StoreInfoDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<StoreInfo> loadAll() {
        StoreInfoDao dao = (StoreInfoDao) baseDao;
        return dao.loadAll();
    }

    private static class SingleHolder{
        static StoreInfoDbManger INSTANCE = new StoreInfoDbManger();
    }
}
