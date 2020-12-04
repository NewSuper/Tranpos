package com.newsuper.t.markert.db.manger;

import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.ProductUnitDao;
import com.newsuper.t.markert.entity.ProductUnit;

import java.util.List;

public class ProductUnitDbManger extends BaseDBManager<ProductUnit> {

    private ProductUnitDbManger(){

    }

    private static class SingleHolder{
        static ProductUnitDbManger INSTANCE = new ProductUnitDbManger();
    }

    public static ProductUnitDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    @Override
    protected BaseDao<ProductUnit> getDataBaseDao() {
        return AppDatabase.getDatabase().getProductUnitDao();
    }

    @Override
    public int deleteAll() {
        ProductUnitDao dao = (ProductUnitDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<ProductUnit> loadAll() {
        ProductUnitDao dao = (ProductUnitDao) baseDao;
        return dao.loadAll();
    }
}
