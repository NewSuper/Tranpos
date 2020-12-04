package com.newsuper.t.markert.db.manger;

import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.ProductDao;
import com.newsuper.t.markert.entity.Product;

import java.util.List;

public class ProductDbManger extends BaseDBManager<Product> {
    private ProductDbManger(){

    }

    @Override
    protected BaseDao<Product> getDataBaseDao() {
        return AppDatabase.getDatabase().getProductDao();
    }
    public static ProductDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static ProductDbManger INSTANCE = new ProductDbManger();
    }

    @Override
    public int deleteAll() {
        ProductDao dao = (ProductDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<Product> loadAll() {
        ProductDao dao = (ProductDao) baseDao;
        return dao.loadAll();
    }

    public Product queryProductByCode(String barcode){
        ProductDao dao = (ProductDao) baseDao;
        return dao.findProductByCode(barcode);
    }
}