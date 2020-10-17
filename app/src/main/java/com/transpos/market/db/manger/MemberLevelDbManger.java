package com.transpos.market.db.manger;


import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.MemberLevelDao;
import com.transpos.market.entity.MemberLevel;

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
