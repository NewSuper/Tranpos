package com.transpos.market.db.manger;

import  com.transpos.market.base.BaseDBManager;
import  com.transpos.market.base.BaseDao;
import  com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.MemberPointRuleDao;
import  com.transpos.market.entity.MemberPointRule;

import java.util.List;

public class MemberPointRuleDbManger extends BaseDBManager<MemberPointRule> {

    private MemberPointRuleDbManger(){}

    @Override
    protected BaseDao<MemberPointRule> getDataBaseDao() {
        return AppDatabase.getDatabase().getMemberPointRuleDao();
    }

    @Override
    public int deleteAll() {
        MemberPointRuleDao dao = (MemberPointRuleDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<MemberPointRule> loadAll() {
        MemberPointRuleDao dao = (MemberPointRuleDao) baseDao;
        return dao.loadAll();
    }

    public static MemberPointRuleDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static MemberPointRuleDbManger INSTANCE = new MemberPointRuleDbManger();
    }
}
