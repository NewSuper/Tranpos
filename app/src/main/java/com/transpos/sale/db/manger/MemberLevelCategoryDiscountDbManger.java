package com.transpos.sale.db.manger;

import com.transpos.sale.base.BaseDBManager;
import com.transpos.sale.base.BaseDao;
import com.transpos.sale.db.AppDatabase;
import com.transpos.sale.db.dao.MemberLevelCategoryDiscountDao;
import com.transpos.sale.entity.MemberLevelCategoryDiscount;

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
