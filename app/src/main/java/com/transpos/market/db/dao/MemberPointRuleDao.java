package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.MemberPointRule;

import java.util.List;

@Dao
public interface MemberPointRuleDao extends BaseDao<MemberPointRule> {

    @Query("DELETE FROM pos_member_point_rule")
    int deleteAll();

    @Query("SELECT * FROM pos_member_point_rule")
    List<MemberPointRule> loadAll();
}
