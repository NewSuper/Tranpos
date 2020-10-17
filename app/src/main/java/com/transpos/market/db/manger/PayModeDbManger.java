package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.PayModeDao;
import com.transpos.market.entity.PayMode;

import java.util.List;

public class PayModeDbManger extends BaseDBManager<PayMode> {
    private PayModeDbManger(){}

    @Override
    protected BaseDao<PayMode> getDataBaseDao() {
        return AppDatabase.getDatabase().getPayModeDao();
    }


    @Override
    public int deleteAll() {
        PayModeDao dao = (PayModeDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<PayMode> loadAll() {
        PayModeDao dao = (PayModeDao) baseDao;
        return dao.loadAll();
    }

    public static PayModeDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static PayModeDbManger INSTANCE = new PayModeDbManger();
    }
}
