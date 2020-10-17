package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.ProductContactDao;
import com.transpos.market.entity.ProductContact;

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
