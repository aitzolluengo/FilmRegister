package com.tzolas.filmregister.data.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String title;

    public String year;
    public boolean watched;
    public float rating;

    public Movie(String title, String year, boolean watched, float rating) {
        this.title = title;
        this.year = year;
        this.watched = watched;
        this.rating = rating;
    }
}
