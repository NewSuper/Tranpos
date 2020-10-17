package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.ProductSpecDao;
import com.transpos.market.entity.ProductSpec;

import java.util.List;

public class ProductSpecDbManger extends BaseDBManager<ProductSpec> {

    private ProductSpecDbManger(){}

    @Override
    protected BaseDao<ProductSpec> getDataBaseDao() {
        return AppDatabase.getDatabase().getProductSpecDao();
    }

    @Override
    public int deleteAll() {
        ProductSpecDao dao = (ProductSpecDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<ProductSpec> loadAll() {
        ProductSpecDao dao = (ProductSpecDao) baseDao;
        return dao.loadAll();
    }

    public static ProductSpecDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static ProductSpecDbManger INSTANCE = new ProductSpecDbManger();
    }
}
