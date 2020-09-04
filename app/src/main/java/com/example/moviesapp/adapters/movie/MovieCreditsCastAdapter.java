package com.example.moviesapp.adapters.movie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.R;
import com.example.moviesapp.adapters.viewholders.MovieCreditsViewHolder;
import com.example.moviesapp.databinding.MovieCreditsLayoutBinding;
import com.example.moviesapp.model.movie.credits.MovieCreditsCast;
import com.example.moviesapp.model.movie.credits.MovieCreditsCrew;
import com.example.moviesapp.ui.PersonDetailsActivity;

import java.util.List;

public class MovieCreditsCastAdapter extends RecyclerView.Adapter<MovieCreditsViewHolder> {

    private MovieCreditsCastListener movieCreditsCastListener;
    private List<MovieCreditsCast> movieCreditsCastList;

    public MovieCreditsCastAdapter(MovieCreditsCastListener movieCreditsCastListener) {
        this.movieCreditsCastListener = movieCreditsCastListener;
    }

    public void setMovieCreditsCastList(List<MovieCreditsCast> movieCreditsCastList) {
        this.movieCreditsCastList = movieCreditsCastList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieCreditsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieCreditsLayoutBinding movieCreditsLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_credits_layout, parent, false);
        return new MovieCreditsViewHolder(movieCreditsLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCreditsViewHolder holder, int position) {
        final MovieCreditsCast movieCreditsCast = movieCreditsCastList.get(position);
        holder.movieCreditsLayoutBinding.setMovieCreditsCast(movieCreditsCast);

        holder.setMovieCreditsImageView((Context) movieCreditsCastListener, movieCreditsCast.getProfilePath());



        holder.movieCreditsLayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(activity, PersonDetailsActivity.class);
                intent.putExtra("id", String.valueOf(movieCreditsCast.getId()));
                activity.startActivity(intent);

                // create some animation to open new activity
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
                movieCreditsCastListener.openPersonDetailsActivity(String.valueOf(movieCreditsCast.getId()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return movieCreditsCastList.size();
    }

    public interface MovieCreditsCastListener {
        void openPersonDetailsActivity(String id);
    }
}
