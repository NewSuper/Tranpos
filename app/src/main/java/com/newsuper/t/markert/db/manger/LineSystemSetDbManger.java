package com.newsuper.t.markert.db.manger;

import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.LineSystemSetDao;
import com.newsuper.t.markert.entity.LineSystemSet;

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
