package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.LineSystemSetDao;
import com.transpos.market.entity.LineSystemSet;

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
