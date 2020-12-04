package com.newsuper.t.sale.db.manger;

import com.newsuper.t.sale.base.BaseDBManager;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.db.AppDatabase;
import com.newsuper.t.sale.db.dao.ProductSpecDao;
import com.newsuper.t.sale.entity.ProductSpec;

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
