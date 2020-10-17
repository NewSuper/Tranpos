package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.WorkerModuleDao;
import com.transpos.market.entity.WorkModule;

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
