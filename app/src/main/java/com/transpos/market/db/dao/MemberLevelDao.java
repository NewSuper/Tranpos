package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.MemberLevel;

import java.util.List;

@Dao
public interface MemberLevelDao extends BaseDao<MemberLevel> {

    @Query("DELETE FROM pos_member_level")
    int deleteAll();

    @Query("SELECT * FROM pos_member_level")
    List<MemberLevel> loadAll();
}
