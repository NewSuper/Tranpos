package com.transpos.sale.db.manger;

import com.transpos.sale.base.BaseDBManager;
import com.transpos.sale.base.BaseDao;
import com.transpos.sale.db.AppDatabase;
import com.transpos.sale.db.dao.PaymentParameterDao;
import com.transpos.sale.entity.PaymentParameter;

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
