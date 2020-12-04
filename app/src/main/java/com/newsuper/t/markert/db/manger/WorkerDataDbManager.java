package com.newsuper.t.markert.db.manger;

import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.WorkerDataDao;
import com.newsuper.t.markert.entity.WorkData;

import java.util.List;

public class WorkerDataDbManager extends BaseDBManager<WorkData> {
    private WorkerDataDbManager(){}
    @Override
    protected BaseDao<WorkData> getDataBaseDao() {
        return AppDatabase.getDatabase().getWorkerDataDao();
    }

    public static WorkerDataDbManager getInstance(){
        return SingleHolder.INSTANCE;
    }

    @Override
    public int deleteAll() {
        WorkerDataDao dao = (WorkerDataDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<WorkData> loadAll() {
        WorkerDataDao dao = (WorkerDataDao) baseDao;
        return dao.loadAll();
    }

    private static class SingleHolder{
        static WorkerDataDbManager INSTANCE  = new WorkerDataDbManager();
    }
}
