package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.WorkerRoleDao;
import com.transpos.market.entity.WorkRole;

import java.util.List;

public class WorkerRoleDbManager extends BaseDBManager<WorkRole> {
    private WorkerRoleDbManager(){}
    @Override
    protected BaseDao<WorkRole> getDataBaseDao() {
        return AppDatabase.getDatabase().getWorkerRoleDao();
    }

    public static WorkerRoleDbManager getInstance(){
        return SingleHolder.INSTANCE;
    }

    @Override
    public int deleteAll() {
        WorkerRoleDao dao = (WorkerRoleDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<WorkRole> loadAll() {
        WorkerRoleDao dao = (WorkerRoleDao) baseDao;
        return dao.loadAll();
    }

    private static class SingleHolder{
        static WorkerRoleDbManager INSTANCE  = new WorkerRoleDbManager();
    }
}
