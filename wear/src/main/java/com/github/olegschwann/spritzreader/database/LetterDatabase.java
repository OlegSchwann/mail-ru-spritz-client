package com.github.olegschwann.spritzreader.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {Letter.class}, version = 1)
public abstract class LetterDatabase extends RoomDatabase {
    public static LetterDatabase databaseFactory(@NonNull Context context){
        return Room
            .databaseBuilder(context, LetterDatabase.class, "letter.sqlite3")
            .allowMainThreadQueries() // Для прототипа.
            .build();
    }

    public abstract LetterDao letterDao();
}
