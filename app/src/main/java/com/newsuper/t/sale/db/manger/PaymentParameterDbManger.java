package com.newsuper.t.sale.db.manger;

import com.newsuper.t.sale.base.BaseDBManager;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.db.AppDatabase;
import com.newsuper.t.sale.db.dao.PaymentParameterDao;
import com.newsuper.t.sale.entity.PaymentParameter;

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
