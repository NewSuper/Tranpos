package com.transpos.sale.db.manger;

import com.transpos.sale.base.BaseDBManager;
import com.transpos.sale.base.BaseDao;
import com.transpos.sale.db.AppDatabase;
import com.transpos.sale.db.dao.LineSalesSettingDao;
import com.transpos.sale.entity.LineSalesSetting;

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
