package com.github.olegschwann.spritzreader.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

// Внятный пример использования базы данных
// https://github.com/lomza/movies-room
// Объяснение @Entity
// https://developer.android.com/training/data-storage/room/defining-data.html

@Entity(tableName = "letters")
public class Letter {
    public Letter(
        @NonNull String id,
        @NonNull String from,
        @NonNull String subject,
        @NonNull String jsonBody
    ) {
        this.id = id;
        this.from = from;
        this.subject = subject;
        this.jsonBody = jsonBody;
    }

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public String id;

    @NonNull
    @ColumnInfo(name = "from")
    public String from; // имя отправителя

    @NonNull
    @ColumnInfo(name = "subject")
    public String subject; // тема письма

    @NonNull
    @ColumnInfo(name = "json_body")
    public String jsonBody; // тело письма, уже размеченное в JSON Spritz формат.
}
