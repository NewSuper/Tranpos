package com.newsuper.t.sale.db.manger;

import com.newsuper.t.sale.base.BaseDBManager;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.db.AppDatabase;
import com.newsuper.t.sale.db.dao.MemberLevelCategoryDiscountDao;
import com.newsuper.t.sale.entity.MemberLevelCategoryDiscount;

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
