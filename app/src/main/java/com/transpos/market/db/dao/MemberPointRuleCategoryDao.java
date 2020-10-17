package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;


import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.MemberPointRuleCategory;

import java.util.List;

@Dao
public interface MemberPointRuleCategoryDao extends BaseDao<MemberPointRuleCategory> {

    @Query("DELETE FROM pos_member_point_rule_category")
    int deleteAll();


    @Query("SELECT * FROM pos_member_point_rule_category")
    List<MemberPointRuleCategory> loadAll();
}
