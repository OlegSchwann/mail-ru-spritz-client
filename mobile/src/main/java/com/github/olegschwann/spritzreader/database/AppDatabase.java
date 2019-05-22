package com.github.olegschwann.spritzreader.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {MsgDBStored.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase mDb;

    public abstract MsgsDao getMsgsDao();

    public static AppDatabase getInstance(Context context) {
        if (mDb == null) {
            mDb = Room.databaseBuilder(context, AppDatabase.class, "main_db")
                    .allowMainThreadQueries() // TODO() don't forget to remove this line in release! Only for debug purpose!
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mDb;
    }
}
