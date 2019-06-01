package com.github.olegschwann.spritzreader.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

@Dao
public interface MsgsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MsgDBStored... msg);

   // @Query("select * from MsgDBStored where ")
}
