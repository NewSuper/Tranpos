package com.newsuper.t.sale.db.manger;

import com.newsuper.t.sale.base.BaseDBManager;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.db.AppDatabase;
import com.newsuper.t.sale.db.dao.MemberPointRuleCategoryDao;
import com.newsuper.t.sale.entity.MemberPointRuleCategory;

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
