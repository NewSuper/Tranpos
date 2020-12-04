package com.newsuper.t.sale.db.manger;

import com.newsuper.t.sale.base.BaseDBManager;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.db.AppDatabase;
import com.newsuper.t.sale.db.dao.LineSystemSetDao;
import com.newsuper.t.sale.entity.LineSystemSet;

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
