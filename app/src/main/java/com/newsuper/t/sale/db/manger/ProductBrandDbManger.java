package com.newsuper.t.sale.db.manger;

import com.newsuper.t.sale.base.BaseDBManager;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.db.AppDatabase;
import com.newsuper.t.sale.db.dao.ProductBrandDao;

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
