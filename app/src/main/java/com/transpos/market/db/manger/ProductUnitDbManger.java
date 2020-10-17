package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.ProductUnitDao;
import com.transpos.market.entity.ProductUnit;

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
