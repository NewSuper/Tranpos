package com.qx.imlib.db.dao;

import com.qx.imlib.db.entity.UserEntity;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    long insertUser(UserEntity userEntity);

    @Query("SELECT * FROM user WHERE user_id == :userId LIMIT 1")
    UserEntity getUserById(String userId);
}
