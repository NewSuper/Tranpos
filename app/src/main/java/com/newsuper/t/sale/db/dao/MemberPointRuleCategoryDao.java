package com.newsuper.t.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.sale.base.BaseDao;
import com.newsuper.t.sale.entity.MemberPointRuleCategory;

import java.util.List;
@Dao
public interface MemberPointRuleCategoryDao extends BaseDao<MemberPointRuleCategory> {

    @Query("DELETE FROM pos_member_point_rule_category")
    int deleteAll();


    @Query("SELECT * FROM pos_member_point_rule_category")
    List<MemberPointRuleCategory> loadAll();
}
