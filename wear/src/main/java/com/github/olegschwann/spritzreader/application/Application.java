package com.github.olegschwann.spritzreader.application;

// Использую Application для инициализации базы данных.
// https://ru-code-android.livejournal.com/4594.html
// Время жизни базы данных теперь совпадает со временем жизни процесса.

import com.github.olegschwann.spritzreader.TestData;
import com.github.olegschwann.spritzreader.database.LetterDatabase;

public class Application extends android.app.Application {
    public LetterDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        this.database = LetterDatabase.databaseFactory(this.getApplicationContext());

        this.database.letterDao().insertUsers(TestData.data); // TODO: Это только для демонстрации.
    }
}
