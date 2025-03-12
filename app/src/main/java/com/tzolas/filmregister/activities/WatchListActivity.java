package com.tzolas.filmregister.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tzolas.filmregister.R;
import com.tzolas.filmregister.adapters.MovieAdapter;
import com.tzolas.filmregister.data.database.AppDatabase;
import com.tzolas.filmregister.data.database.entities.Movie;
import java.util.List;

public class WatchListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        recyclerView = findViewById(R.id.recycler_watchlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener la lista de películas pendientes
        movieList = AppDatabase.getInstance(this).movieDao().getWatchlistMovies();

        if (movieList.isEmpty()) {
            Toast.makeText(this, "No hay películas en la Watchlist", Toast.LENGTH_SHORT).show();
        }

        adapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(adapter);
    }
}
