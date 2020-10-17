package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.LineSalesSetting;

import java.util.List;

@Dao
public interface LineSalesSettingDao extends BaseDao<LineSalesSetting> {

    @Query("DELETE FROM pos_line_sales_setting")
    int deleteAll();


    @Query("SELECT * FROM pos_line_sales_setting")
    List<LineSalesSetting> loadAll();
}
