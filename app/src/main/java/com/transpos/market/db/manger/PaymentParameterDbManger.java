package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.PaymentParameterDao;
import com.transpos.market.entity.PaymentParameter;

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
