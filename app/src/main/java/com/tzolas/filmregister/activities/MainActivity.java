package com.tzolas.filmregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.WorkRequest;

import com.tzolas.filmregister.R;
import com.tzolas.filmregister.data.database.AppDatabase;
import com.tzolas.filmregister.data.database.entities.Movie;
import com.tzolas.filmregister.utils.NotificationUtils;
import com.tzolas.filmregister.workers.NotificationWorker;
import com.tzolas.filmregister.workers.ReminderWorker;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // ✅ Solo un setContentView()

        // Inicializar botones
        setupButtons();

        // Crear canal de notificación
        NotificationUtils.createNotificationChannel(this);

        // Programar notificaciones automáticas
        scheduleDailyNotification();
        scheduleDailyReminder();

        // Insertar película de prueba en la base de datos
        insertTestMovie();
    }

    /**
     * Configura los botones de la actividad
     */
    private void setupButtons() {
        Button btnWatchlist = findViewById(R.id.btn_watchlist);
        btnWatchlist.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, WatchListActivity.class); // ✅ Nombres corregidos
            startActivity(intent);
        });

        Button btnHistory = findViewById(R.id.btn_history);
        btnHistory.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        Button btnSearch = findViewById(R.id.btn_search);
        if (btnSearch == null) {
            Log.e("MainActivity", "El botón btn_search no fue encontrado en el layout.");
        } else {
            btnSearch.setOnClickListener(v -> {
                Log.d("MainActivity", "Botón de búsqueda presionado.");
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            });
        }




        Button btnSendNotification = findViewById(R.id.btn_send_notification);
        btnSendNotification.setOnClickListener(v -> {
            NotificationUtils.sendReminderNotification(this, "¡Revisa tu watchlist en FilmRegister!");
        });
    }

    /**
     * Programa una notificación diaria con WorkManager
     */
    private void scheduleDailyNotification() {
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
                NotificationWorker.class, 24, TimeUnit.HOURS)
                .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "daily_notification",
                ExistingPeriodicWorkPolicy.KEEP, // No reprograma si ya existe
                workRequest);
    }

    /**
     * Programa un recordatorio diario con WorkManager
     */
    private void scheduleDailyReminder() {
        WorkRequest workRequest = new PeriodicWorkRequest.Builder(
                ReminderWorker.class, 24, TimeUnit.HOURS)
                .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "daily_reminder",
                ExistingPeriodicWorkPolicy.KEEP,
                (PeriodicWorkRequest) workRequest);
    }

    /**
     * Inserta una película de prueba en la base de datos y la imprime en el Logcat
     */
    private void insertTestMovie() {
        AppDatabase db = AppDatabase.getInstance(this);

        // Insertar película de prueba (solo si no existe)
        if (db.movieDao().getAllMovies().isEmpty()) {
            db.movieDao().insertMovie(new Movie("Interstellar", "2014", false, 0));
        }

        // Mostrar todas las películas en el Logcat
        List<Movie> movies = db.movieDao().getAllMovies();
        for (Movie movie : movies) {
            Log.d("FilmRegister", "Película: " + movie.title + " (" + movie.year + ")");
        }
    }
}
