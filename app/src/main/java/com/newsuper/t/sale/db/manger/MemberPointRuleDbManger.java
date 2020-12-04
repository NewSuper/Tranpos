package com.newsuper.t.sale.db.manger;

import com.newsuper.t.sale.base.BaseDBManager;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.db.AppDatabase;
import com.newsuper.t.sale.db.dao.MemberPointRuleDao;
import com.newsuper.t.sale.entity.MemberPointRule;

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
