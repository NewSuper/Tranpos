package com.newsuper.t.markert.db.manger;

import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.ShortCutsDao;
import com.newsuper.t.markert.entity.ShortCuts;

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
