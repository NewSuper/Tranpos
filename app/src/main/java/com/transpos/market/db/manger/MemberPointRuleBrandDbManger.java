package com.transpos.market.db.manger;


import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.MemberPointRuleBrandDao;
import com.transpos.market.entity.MemberPointRuleBrand;

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
