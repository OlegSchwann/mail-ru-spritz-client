package com.github.olegschwann.spritzreader.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;

// Объяснение @Dao
// https://developer.android.com/training/data-storage/room/accessing-data#java

@Dao
public interface LetterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(List<Letter> letters);

    @Query("select \"id\", \"from\", \"subject\" from letters")
    public List<LetterHeader> loadHeaders();

    @Query("select \"id\", \"json_body\" from letters where \"id\" = :id")
    public LetterBody loadBody(String id);
}
