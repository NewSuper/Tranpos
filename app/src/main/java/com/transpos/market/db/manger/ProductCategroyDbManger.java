package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.ProductCategroyDao;
import com.transpos.market.entity.ProductCategory;

import java.util.List;

public class ProductCategroyDbManger extends BaseDBManager<ProductCategory> {
    private ProductCategroyDbManger(){

    }

    @Override
    protected BaseDao<ProductCategory> getDataBaseDao() {
        return AppDatabase.getDatabase().getProductCategroyDao();
    }

    public static ProductCategroyDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static ProductCategroyDbManger INSTANCE = new ProductCategroyDbManger();
    }

    @Override
    public int deleteAll() {
        ProductCategroyDao dao = (ProductCategroyDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<ProductCategory> loadAll() {
        ProductCategroyDao dao = (ProductCategroyDao) baseDao;
        return dao.loadAll();
    }
}
