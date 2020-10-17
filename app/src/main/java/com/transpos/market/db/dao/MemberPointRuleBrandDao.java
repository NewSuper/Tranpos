package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.MemberPointRuleBrand;

import java.util.List;

@Dao
public interface MemberPointRuleBrandDao extends BaseDao<MemberPointRuleBrand> {

    @Query("DELETE FROM pos_member_point_rule_brand")
    int deleteAll();

    @Query("SELECT * FROM pos_member_point_rule_brand")
    List<MemberPointRuleBrand> loadAll();
}
