package com.newsuper.t.markert.db.manger;

import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.WorkerDao;
import com.newsuper.t.markert.entity.Worker;

import java.util.List;

public class WorkerDbManger extends BaseDBManager<Worker> {

    private WorkerDbManger(){}

    @Override
    protected BaseDao<Worker> getDataBaseDao() {
        return AppDatabase.getDatabase().getWorkerDao();
    }

    public static WorkerDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    @Override
    public int deleteAll() {
        WorkerDao dao = (WorkerDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<Worker> loadAll() {
        WorkerDao dao = (WorkerDao) baseDao;
        return dao.loadAll();
    }

    private static class SingleHolder{
        static WorkerDbManger INSTANCE = new WorkerDbManger();
    }
}
