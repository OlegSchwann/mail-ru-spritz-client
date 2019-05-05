package com.github.olegschwann.spritzreader.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;


import java.util.List;

// Объяснение @Dao
// https://developer.android.com/training/data-storage/room/accessing-data#java

@Dao
public interface LetterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(List<Letter> letters);

    @Query("select \"id\", \"from\", \"subject\" from letters")
    public List<letterHeader> loadHeaders();

    @Query("select \"id\", \"json_body\" from letters where \"id\" = :id")
    public LetterBody loadBody(String id);

    class letterHeader {
        @PrimaryKey
        @ColumnInfo(name = "id")
        public String id;

        @ColumnInfo(name = "from")
        public String from; // имя отправителя

        @ColumnInfo(name = "subject")
        public String subject; // тема письма
    }

    class LetterBody {
        @ColumnInfo(name = "id")
        public String id;

        @ColumnInfo(name = "json_body")
        public String jsonBody; // тело письма, уже размеченное в JSON Spritz формат.
    }
}
