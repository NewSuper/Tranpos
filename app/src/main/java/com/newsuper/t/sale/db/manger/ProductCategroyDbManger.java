package com.newsuper.t.sale.db.manger;

import com.newsuper.t.sale.base.BaseDBManager;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.db.AppDatabase;
import com.newsuper.t.sale.db.dao.ProductCategroyDao;
import com.newsuper.t.sale.entity.ProductCategory;

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
