package com.newsuper.t.markert.db.manger;


import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.MemberPointRuleBrandDao;
import com.newsuper.t.markert.entity.MemberPointRuleBrand;

import java.util.List;

public class MemberPointRuleBrandDbManger extends BaseDBManager<MemberPointRuleBrand> {

    private MemberPointRuleBrandDbManger(){}

    @Override
    protected BaseDao<MemberPointRuleBrand> getDataBaseDao() {
        return AppDatabase.getDatabase().getMemberPointRuleBrandDao();
    }


    @Override
    public int deleteAll() {
        MemberPointRuleBrandDao dao = (MemberPointRuleBrandDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<MemberPointRuleBrand> loadAll() {
        MemberPointRuleBrandDao dao = (MemberPointRuleBrandDao) baseDao;
        return dao.loadAll();
    }

    public static MemberPointRuleBrandDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static MemberPointRuleBrandDbManger INSTANCE = new MemberPointRuleBrandDbManger();
    }
}
