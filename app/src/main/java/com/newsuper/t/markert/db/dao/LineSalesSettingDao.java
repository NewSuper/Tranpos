package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.LineSalesSetting;

import java.util.List;

@Dao
public interface LineSalesSettingDao extends BaseDao<LineSalesSetting> {

    @Query("DELETE FROM pos_line_sales_setting")
    int deleteAll();


    @Query("SELECT * FROM pos_line_sales_setting")
    List<LineSalesSetting> loadAll();
}
