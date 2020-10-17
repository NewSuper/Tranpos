package com.transpos.sale.db.manger;

import com.transpos.sale.base.BaseDBManager;
import com.transpos.sale.base.BaseDao;
import com.transpos.sale.db.AppDatabase;
import com.transpos.sale.db.dao.PayModeDao;
import com.transpos.sale.entity.PayMode;

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
