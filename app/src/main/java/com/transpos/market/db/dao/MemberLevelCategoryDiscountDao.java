package com.transpos.market.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;


import com.transpos.market.base.BaseDao;
import com.transpos.market.entity.MemberLevelCategoryDiscount;

import java.util.List;

@Dao
public interface MemberLevelCategoryDiscountDao extends BaseDao<MemberLevelCategoryDiscount> {

    @Query("DELETE FROM pos_member_level_category_discount")
    int deleteAll();

    @Query("SELECT * FROM pos_member_level_category_discount")
    List<MemberLevelCategoryDiscount> loadAll();
}
