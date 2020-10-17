package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.MemberPointRule;

import java.util.List;

@Dao
public interface MemberPointRuleDao extends BaseDao<MemberPointRule> {

    @Query("DELETE FROM pos_member_point_rule")
    int deleteAll();

    @Query("SELECT * FROM pos_member_point_rule")
    List<MemberPointRule> loadAll();
}
