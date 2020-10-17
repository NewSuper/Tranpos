package com.transpos.sale.db.manger;

import com.transpos.sale.base.BaseDBManager;
import com.transpos.sale.base.BaseDao;
import com.transpos.sale.db.AppDatabase;
import com.transpos.sale.db.dao.LineSystemSetDao;
import com.transpos.sale.entity.LineSystemSet;

import java.util.List;

public class LineSystemSetDbManger extends BaseDBManager<LineSystemSet> {

    private LineSystemSetDbManger(){

    }

    @Override
    protected BaseDao<LineSystemSet> getDataBaseDao() {
        return AppDatabase.getDatabase().getLineSystemSetDao();
    }

    @Override
    public int deleteAll() {
        LineSystemSetDao dao = (LineSystemSetDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<LineSystemSet> loadAll() {
        LineSystemSetDao dao = (LineSystemSetDao) baseDao;
        return dao.loadAll();
    }

    public static LineSystemSetDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static LineSystemSetDbManger INSTANCE = new LineSystemSetDbManger();
    }
}
