package com.newsuper.t.sale.db.manger;

import com.newsuper.t.sale.base.BaseDBManager;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.db.AppDatabase;
import com.newsuper.t.sale.db.dao.ProductUnitDao;
import com.newsuper.t.sale.entity.ProductUnit;

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
