package com.tzolas.filmregister.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.tzolas.filmregister.R;
import com.tzolas.filmregister.data.database.AppDatabase;
import com.tzolas.filmregister.data.database.entities.Movie;

public class MovieDetailActivity extends AppCompatActivity {
    private TextView title, year, status;
    private RatingBar ratingBar;
    private Button btnToggleWatchStatus;
    private Movie movie;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        title = findViewById(R.id.detail_title);
        year = findViewById(R.id.detail_year);
        status = findViewById(R.id.detail_status);
        ratingBar = findViewById(R.id.detail_ratingBar);
        btnToggleWatchStatus = findViewById(R.id.btn_toggle_watch_status);

        db = AppDatabase.getInstance(this);

        // Obtener el ID de la película desde el Intent
        int movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        if (movieId == -1) {
            Toast.makeText(this, "Error al cargar la película", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Obtener la película desde la base de datos
        movie = db.movieDao().getMovieById(movieId);

        if (movie != null) {
            title.setText(movie.title);
            year.setText(movie.year);
            status.setText(movie.watched ? "✅ Vista" : "📋 Pendiente");
            ratingBar.setRating(movie.rating);

            btnToggleWatchStatus.setText(movie.watched ? "Marcar como pendiente" : "Marcar como vista");

            btnToggleWatchStatus.setOnClickListener(v -> {
                movie.watched = !movie.watched;
                db.movieDao().updateMovie(movie);
                status.setText(movie.watched ? "✅ Vista" : "📋 Pendiente");
                btnToggleWatchStatus.setText(movie.watched ? "Marcar como pendiente" : "Marcar como vista");
                Toast.makeText(this, "Estado actualizado", Toast.LENGTH_SHORT).show();
            });

            ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
                if (fromUser) {
                    movie.rating = rating;
                    db.movieDao().updateMovie(movie);
                    Toast.makeText(this, "Calificación guardada", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
