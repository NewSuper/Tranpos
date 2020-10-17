package com.transpos.sale.db.manger;

import com.transpos.sale.base.BaseDBManager;
import com.transpos.sale.base.BaseDao;
import com.transpos.sale.db.AppDatabase;
import com.transpos.sale.db.dao.MemberPointRuleCategoryDao;
import com.transpos.sale.entity.MemberPointRuleCategory;

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
