package com.transpos.sale.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.transpos.sale.base.BaseDao;
import com.transpos.sale.entity.MemberLevelCategoryDiscount;

import java.util.List;

@Dao
public interface MemberLevelCategoryDiscountDao extends BaseDao<MemberLevelCategoryDiscount> {

    @Query("DELETE FROM pos_member_level_category_discount")
    int deleteAll();

    @Query("SELECT * FROM pos_member_level_category_discount")
    List<MemberLevelCategoryDiscount> loadAll();
}
