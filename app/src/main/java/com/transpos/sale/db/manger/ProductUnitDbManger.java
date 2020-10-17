package com.transpos.sale.db.manger;

import com.transpos.sale.base.BaseDBManager;
import com.transpos.sale.base.BaseDao;
import com.transpos.sale.db.AppDatabase;
import com.transpos.sale.db.dao.ProductUnitDao;
import com.transpos.sale.entity.ProductUnit;

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
