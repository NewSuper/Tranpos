package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.MemberPointRule;

import java.util.List;

@Dao
public interface MemberPointRuleDao extends BaseDao<MemberPointRule> {

    @Query("DELETE FROM pos_member_point_rule")
    int deleteAll();

    @Query("SELECT * FROM pos_member_point_rule")
    List<MemberPointRule> loadAll();
}
