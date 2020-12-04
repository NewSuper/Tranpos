package com.newsuper.t.markert.db.manger;

import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.WorkerRoleDao;
import com.newsuper.t.markert.entity.WorkRole;

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
