package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.MemberLevel;

import java.util.List;

@Dao
public interface MemberLevelDao extends BaseDao<MemberLevel> {

    @Query("DELETE FROM pos_member_level")
    int deleteAll();

    @Query("SELECT * FROM pos_member_level")
    List<MemberLevel> loadAll();
}
