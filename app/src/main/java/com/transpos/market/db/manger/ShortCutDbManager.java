package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.ShortCutsDao;
import com.transpos.market.entity.ShortCuts;

import java.util.List;

public class ShortCutDbManager extends BaseDBManager<ShortCuts> {
    private ShortCutDbManager(){}
    @Override
    protected BaseDao<ShortCuts> getDataBaseDao() {
        return AppDatabase.getDatabase().getShortCutsDao();
    }

    public static ShortCutDbManager getInstance(){
        return SingleHolder.INSTANCE;
    }

    @Override
    public int deleteAll() {
        ShortCutsDao dao = (ShortCutsDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<ShortCuts> loadAll() {
        ShortCutsDao dao = (ShortCutsDao) baseDao;
        return dao.loadAll();
    }

    private static class SingleHolder{
        static ShortCutDbManager INSTANCE  = new ShortCutDbManager();
    }
}
