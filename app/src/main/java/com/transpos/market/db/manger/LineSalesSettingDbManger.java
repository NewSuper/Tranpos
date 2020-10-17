package com.transpos.market.db.manger;


import com.transpos.market.base.BaseDBManager;
import com.transpos.market.base.BaseDao;
import com.transpos.market.db.AppDatabase;
import com.transpos.market.db.dao.LineSalesSettingDao;
import com.transpos.market.entity.LineSalesSetting;

import java.util.List;

public class LineSalesSettingDbManger extends BaseDBManager<LineSalesSetting> {

    private LineSalesSettingDbManger(){}

    @Override
    protected BaseDao<LineSalesSetting> getDataBaseDao() {
        return AppDatabase.getDatabase().getLineSalesSettingDao();
    }


    @Override
    public int deleteAll() {
        LineSalesSettingDao dao = (LineSalesSettingDao) baseDao;
        return dao.deleteAll();
    }

    @Override
    public List<LineSalesSetting> loadAll() {
        LineSalesSettingDao dao = (LineSalesSettingDao) baseDao;
        return dao.loadAll();
    }

    public static LineSalesSettingDbManger getInstance(){
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder{
        static LineSalesSettingDbManger INSTANCE = new LineSalesSettingDbManger();
    }

}
