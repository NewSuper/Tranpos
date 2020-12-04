package com.newsuper.t.markert.db.manger;

import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.MemberLevelCategoryDiscountDao;
import com.newsuper.t.markert.entity.MemberLevelCategoryDiscount;

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
