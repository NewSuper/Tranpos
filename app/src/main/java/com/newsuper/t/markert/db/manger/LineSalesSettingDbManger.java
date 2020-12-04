package com.newsuper.t.markert.db.manger;


import com.newsuper.t.markert.base.BaseDBManager;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.db.AppDatabase;
import com.newsuper.t.markert.db.dao.LineSalesSettingDao;
import com.newsuper.t.markert.entity.LineSalesSetting;

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
