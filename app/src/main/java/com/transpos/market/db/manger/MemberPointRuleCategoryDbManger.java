package com.transpos.market.db.manger;



import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.MemberPointRuleCategoryDao;
import com.transpos.market.entity.MemberPointRuleCategory;

import java.util.List;

public class MemberPointRuleCategoryDbManger extends BaseDBManager<MemberPointRuleCategory> {

    private MemberPointRuleCategoryDbManger(){}

    @Override
    protected BaseDao<MemberPointRuleCategory> getDataBaseDao() {
        return AppDatabase.getDatabase().getMemberPointRuleCategoryDao();
    }


    @Override
    public int deleteAll() {
        MemberPointRuleCategoryDao dao = (MemberPointRuleCategoryDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<MemberPointRuleCategory> loadAll() {
        MemberPointRuleCategoryDao dao = (MemberPointRuleCategoryDao) baseDao;
        return dao.loadAll();
    }

    public static MemberPointRuleCategoryDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static MemberPointRuleCategoryDbManger INSTANCE = new MemberPointRuleCategoryDbManger();
    }
}
