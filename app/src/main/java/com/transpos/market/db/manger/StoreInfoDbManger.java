package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.StoreInfoDao;
import com.transpos.market.entity.StoreInfo;

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
