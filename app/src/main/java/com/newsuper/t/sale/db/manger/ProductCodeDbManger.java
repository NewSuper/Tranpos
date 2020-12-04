package com.newsuper.t.sale.db.manger;

import com.newsuper.t.sale.base.BaseDBManager;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.db.AppDatabase;
import com.newsuper.t.sale.db.dao.ProductCodeDao;
import com.newsuper.t.sale.entity.ProductCode;

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
