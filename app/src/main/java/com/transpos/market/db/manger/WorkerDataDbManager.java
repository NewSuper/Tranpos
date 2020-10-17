package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.WorkerDataDao;
import com.transpos.market.entity.WorkData;

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
