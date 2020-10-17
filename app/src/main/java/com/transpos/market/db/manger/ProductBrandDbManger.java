package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.ProductBrandDao;

import java.util.List;

public class ProductBrandDbManger extends BaseDBManager {

    private ProductBrandDbManger(){}

    private static class SingletonHolder{
        static ProductBrandDbManger INSTANCE = new ProductBrandDbManger();
    }

    public static ProductBrandDbManger getInstance(){
        return SingletonHolder.INSTANCE;
    }

    @Override
    protected BaseDao getDataBaseDao() {
        return AppDatabase.getDatabase().getProductBrandDao();
    }

    @Override
    public int deleteAll() {
        ProductBrandDao dao = (ProductBrandDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List loadAll() {
        ProductBrandDao dao = (ProductBrandDao) baseDao;
        return dao.loadAll();
    }
}
