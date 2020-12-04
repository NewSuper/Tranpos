package com.newsuper.t.sale.db.manger;

import com.newsuper.t.sale.base.BaseDBManager;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.db.AppDatabase;
import com.newsuper.t.sale.db.dao.ProductContactDao;
import com.newsuper.t.sale.entity.ProductContact;

import java.util.List;

public class ProductContractDbManger extends BaseDBManager<ProductContact> {
    private ProductContractDbManger(){

    }
    @Override
    protected BaseDao<ProductContact> getDataBaseDao() {
        return AppDatabase.getDatabase().getProcuctContractDao();
    }

    @Override
    public int deleteAll() {
        ProductContactDao dao = (ProductContactDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<ProductContact> loadAll() {
        ProductContactDao dao = (ProductContactDao) baseDao;
        return dao.loadAll();
    }

    public static ProductContractDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static ProductContractDbManger INSTANCE = new ProductContractDbManger();
    }
}
