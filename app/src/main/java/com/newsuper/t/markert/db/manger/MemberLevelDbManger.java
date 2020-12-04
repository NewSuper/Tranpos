package com.newsuper.t.markert.db.manger;


import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.MemberLevelDao;
import com.newsuper.t.markert.entity.MemberLevel;

import java.util.List;

public class MemberLevelDbManger extends BaseDBManager<MemberLevel> {

    private MemberLevelDbManger(){}

    @Override
    protected BaseDao<MemberLevel> getDataBaseDao() {
        return AppDatabase.getDatabase().getMemberLevelDao();
    }

    @Override
    public int deleteAll() {
        MemberLevelDao dao = (MemberLevelDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<MemberLevel> loadAll() {
        MemberLevelDao dao = (MemberLevelDao) baseDao;
        return dao.loadAll();
    }

    public static MemberLevelDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static MemberLevelDbManger INSTANCE = new MemberLevelDbManger();
    }
}
