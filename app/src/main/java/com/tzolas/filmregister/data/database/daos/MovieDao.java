package com.tzolas.filmregister.data.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;
import com.tzolas.filmregister.data.database.entities.Movie;

@Dao
public interface MovieDao {
    @Insert
    void insertMovie(Movie movie);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM movies ORDER BY title ASC")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM movies WHERE watched = 1 ORDER BY title ASC")
    List<Movie> getWatchedMovies();

    @Query("SELECT * FROM movies WHERE watched = 0 ORDER BY title ASC")
    List<Movie> getWatchlistMovies();

    @Query("SELECT * FROM movies WHERE id = :movieId LIMIT 1")
    Movie getMovieById(int movieId);

    @Query("SELECT * FROM movies WHERE title LIKE :query ORDER BY title ASC")
    List<Movie> searchMovies(String query);

}

