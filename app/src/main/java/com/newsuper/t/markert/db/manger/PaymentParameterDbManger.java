package com.newsuper.t.markert.db.manger;

import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.PaymentParameterDao;
import com.newsuper.t.markert.entity.PaymentParameter;

import java.util.List;

public class PaymentParameterDbManger extends BaseDBManager<PaymentParameter> {

    private PaymentParameterDbManger(){}

    @Override
    protected BaseDao<PaymentParameter> getDataBaseDao() {
        return AppDatabase.getDatabase().getPaymentParamterDao();
    }


    @Override
    public int deleteAll() {
        PaymentParameterDao dao = (PaymentParameterDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<PaymentParameter> loadAll() {
        PaymentParameterDao dao = (PaymentParameterDao) baseDao;
        return dao.loadAll();
    }

    public static PaymentParameterDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static PaymentParameterDbManger INSTANCE = new PaymentParameterDbManger();
    }
}
