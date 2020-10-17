package com.transpos.sale.db.manger;

import com.transpos.sale.base.BaseDBManager;
import com.transpos.sale.base.BaseDao;
import com.transpos.sale.db.AppDatabase;
import com.transpos.sale.db.dao.ProductContactDao;
import com.transpos.sale.entity.ProductContact;

import java.util.List;

public class ProductContractDbManger extends BaseDBManager<ProductContact> {
    private ProductContractDbManger(){

    }
    @Override
    protected BaseDao<ProductContact> getDataBaseDao() {
        return AppDatabase.getDatabase().getProcuctContractDao();
    }

    @Override
    public int deleteAll() {
        ProductContactDao dao = (ProductContactDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<ProductContact> loadAll() {
        ProductContactDao dao = (ProductContactDao) baseDao;
        return dao.loadAll();
    }

    public static ProductContractDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static ProductContractDbManger INSTANCE = new ProductContractDbManger();
    }
}
