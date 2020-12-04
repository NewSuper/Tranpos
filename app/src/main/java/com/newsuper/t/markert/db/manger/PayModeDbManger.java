package com.newsuper.t.markert.db.manger;

import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.PayModeDao;
import com.newsuper.t.markert.entity.PayMode;

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
