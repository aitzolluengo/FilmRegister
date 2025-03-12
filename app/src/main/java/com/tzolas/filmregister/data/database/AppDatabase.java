package com.tzolas.filmregister.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.tzolas.filmregister.data.database.entities.Movie;
import com.tzolas.filmregister.data.database.daos.MovieDao;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract MovieDao movieDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "film_register_db")
                            .allowMainThreadQueries() // Solo para pruebas (revisar en producción)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

