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
import com.example.moviesapp.model.movie.credits.MovieCreditsCrew;
import com.example.moviesapp.ui.PersonDetailsActivity;

import java.util.List;

public class MovieCreditsCrewAdapter extends RecyclerView.Adapter<MovieCreditsViewHolder> {

    private MovieCreditsCrewListener movieCreditsCrewListener;
    private List<MovieCreditsCrew> movieCreditsCrewList;

    public MovieCreditsCrewAdapter(MovieCreditsCrewListener movieCreditsCrewListener) {
        this.movieCreditsCrewListener = movieCreditsCrewListener;
    }

    public void setMovieCreditsCrewList(List<MovieCreditsCrew> movieCreditsCrewList) {
        this.movieCreditsCrewList = movieCreditsCrewList;
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
        final MovieCreditsCrew movieCreditsCrew = movieCreditsCrewList.get(position);
        holder.movieCreditsLayoutBinding.setMovieCreditsCrew(movieCreditsCrew);
        holder.setMovieCreditsImageView((Context) movieCreditsCrewListener, movieCreditsCrew.getProfilePath());


        holder.movieCreditsLayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(activity, PersonDetailsActivity.class);
                intent.putExtra("id", String.valueOf(movieCreditsCrew.getId()));
                activity.startActivity(intent);

                // create some animation to open new activity
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
                movieCreditsCrewListener.openPersonDetailsActivity(String.valueOf(movieCreditsCrew.getId()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return movieCreditsCrewList.size();
    }

    public interface MovieCreditsCrewListener {
        void openPersonDetailsActivity(String id);
    }
}
