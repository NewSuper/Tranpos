package com.newsuper.t.markert.db.manger;

import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.WorkerModuleDao;
import com.newsuper.t.markert.entity.WorkModule;

import java.util.List;

public class WorkerModuleDbManager extends BaseDBManager<WorkModule> {
    private WorkerModuleDbManager(){}
    @Override
    protected BaseDao<WorkModule> getDataBaseDao() {
        return AppDatabase.getDatabase().getWorkerModuleDao();
    }

    public static WorkerModuleDbManager getInstance(){
        return SingleHolder.INSTANCE;
    }

    @Override
    public int deleteAll() {
        WorkerModuleDao dao = (WorkerModuleDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<WorkModule> loadAll() {
        WorkerModuleDao dao = (WorkerModuleDao) baseDao;
        return dao.loadAll();
    }

    private static class SingleHolder{
        static WorkerModuleDbManager INSTANCE  = new WorkerModuleDbManager();
    }
}
