package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.MemberPointRuleCategory;

import java.util.List;

@Dao
public interface MemberPointRuleCategoryDao extends BaseDao<MemberPointRuleCategory> {

    @Query("DELETE FROM pos_member_point_rule_category")
    int deleteAll();


    @Query("SELECT * FROM pos_member_point_rule_category")
    List<MemberPointRuleCategory> loadAll();
}
