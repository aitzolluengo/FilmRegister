package com.tzolas.filmregister.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tzolas.filmregister.R;
import com.tzolas.filmregister.adapters.MovieAdapter;
import com.tzolas.filmregister.data.database.AppDatabase;
import com.tzolas.filmregister.data.database.entities.Movie;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EditText searchInput;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        searchInput = findViewById(R.id.search_input);
        recyclerView = findViewById(R.id.recycler_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cargar todas las películas al inicio
        movieList = AppDatabase.getInstance(this).movieDao().getAllMovies();
        adapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(adapter);

        // Filtrar la lista en tiempo real
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMovies(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterMovies(String query) {
        List<Movie> filteredList = AppDatabase.getInstance(this).movieDao().searchMovies("%" + query + "%");
        adapter = new MovieAdapter(filteredList);
        recyclerView.setAdapter(adapter);
    }
}
