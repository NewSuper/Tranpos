package com.transpos.market.db.manger;

import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.MemberLevelCategoryDiscountDao;
import com.transpos.market.entity.MemberLevelCategoryDiscount;

import java.util.List;

public class MemberLevelCategoryDiscountDbManger extends BaseDBManager<MemberLevelCategoryDiscount> {

    private MemberLevelCategoryDiscountDbManger(){}

    @Override
    protected BaseDao<MemberLevelCategoryDiscount> getDataBaseDao() {
        return AppDatabase.getDatabase().getMemberLevelCategoryDiscountDao();
    }


    @Override
    public int deleteAll() {
        MemberLevelCategoryDiscountDao dao = (MemberLevelCategoryDiscountDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<MemberLevelCategoryDiscount> loadAll() {
        MemberLevelCategoryDiscountDao dao = (MemberLevelCategoryDiscountDao) baseDao;
        return dao.loadAll();
    }

    public static MemberLevelCategoryDiscountDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static MemberLevelCategoryDiscountDbManger INSTANCE = new MemberLevelCategoryDiscountDbManger();
    }
}
