package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.ProductCodeDao;
import com.transpos.market.entity.ProductCode;

import java.util.List;

public class ProductCodeDbManger extends BaseDBManager<ProductCode> {
    private ProductCodeDbManger(){

    }

    @Override
    protected BaseDao<ProductCode> getDataBaseDao() {
        return AppDatabase.getDatabase().getProductCodeDao();
    }

    public static ProductCodeDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    @Override
    public int deleteAll() {
        ProductCodeDao dao = (ProductCodeDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<ProductCode> loadAll() {
        ProductCodeDao dao = (ProductCodeDao) baseDao;
        return dao.loadAll();
    }

    private static class SingleHolder{
        static ProductCodeDbManger INSTANCE = new ProductCodeDbManger();
    }
}
