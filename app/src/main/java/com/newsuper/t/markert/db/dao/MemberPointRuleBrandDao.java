package com.newsuper.t.markert.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.newsuper.t.markert.base.BaseDao;
import com.newsuper.t.markert.entity.MemberPointRuleBrand;

import java.util.List;

@Dao
public interface MemberPointRuleBrandDao extends BaseDao<MemberPointRuleBrand> {

    @Query("DELETE FROM pos_member_point_rule_brand")
    int deleteAll();

    @Query("SELECT * FROM pos_member_point_rule_brand")
    List<MemberPointRuleBrand> loadAll();
}
