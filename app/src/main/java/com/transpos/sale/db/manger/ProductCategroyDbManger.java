package com.transpos.sale.db.manger;

import com.transpos.sale.base.BaseDBManager;
import com.transpos.sale.base.BaseDao;
import com.transpos.sale.db.AppDatabase;
import com.transpos.sale.db.dao.ProductCategroyDao;
import com.transpos.sale.entity.ProductCategory;

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
