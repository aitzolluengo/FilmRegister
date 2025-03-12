package com.tzolas.filmregister.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tzolas.filmregister.R;
import com.tzolas.filmregister.activities.MovieDetailActivity;
import com.tzolas.filmregister.data.database.AppDatabase;
import com.tzolas.filmregister.data.database.entities.Movie;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.title);
        holder.year.setText(movie.year);
        holder.status.setText(movie.watched ? "✅ Vista" : "📋 Pendiente");

        // Si la película ha sido vista, mostrar RatingBar
        if (movie.watched) {
            holder.ratingBar.setVisibility(View.VISIBLE);
            holder.ratingBar.setRating(movie.rating);

            holder.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
                if (fromUser) {
                    movie.rating = rating;
                    AppDatabase.getInstance(holder.itemView.getContext()).movieDao().updateMovie(movie);
                    Toast.makeText(holder.itemView.getContext(), "Calificación guardada", Toast.LENGTH_SHORT).show();

                    // 🔹 Notificar al RecyclerView que los datos han cambiado
                    notifyItemChanged(holder.getAdapterPosition());
                }
            });
        } else {
            holder.ratingBar.setVisibility(View.GONE);
        }

        // 📌 Hacer que al hacer clic en una película, se abra MovieDetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
            intent.putExtra("MOVIE_ID", movie.id);
            v.getContext().startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        TextView title, year, status;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            year = itemView.findViewById(R.id.movie_year);
            status = itemView.findViewById(R.id.movie_status);
        }
    }
    public void updateList(List<Movie> newMovies) {
        this.movieList = newMovies;
        notifyDataSetChanged();
    }

}
