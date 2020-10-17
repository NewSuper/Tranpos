package com.transpos.sale.db.manger;

import com.transpos.sale.base.BaseDBManager;
import com.transpos.sale.base.BaseDao;
import com.transpos.sale.db.AppDatabase;
import com.transpos.sale.db.dao.ProductCodeDao;
import com.transpos.sale.entity.ProductCode;

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
