package com.transpos.sale.db.manger;

import com.transpos.sale.base.BaseDBManager;
import com.transpos.sale.base.BaseDao;
import com.transpos.sale.db.AppDatabase;
import com.transpos.sale.db.dao.ProductBrandDao;

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
