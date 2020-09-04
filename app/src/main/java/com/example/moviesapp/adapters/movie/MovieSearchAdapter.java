package com.example.moviesapp.adapters.movie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.R;
import com.example.moviesapp.databinding.SearchLayoutItemsBinding;
import com.example.moviesapp.model.movie.MovieResponseResult;
import com.example.moviesapp.ui.MovieDetailActivity;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.MovieSearchViewHolder> {
    private MovieSearchClickListener movieSearchClickListener;
    private List<MovieResponseResult> movieResponseResults;

    public MovieSearchAdapter(MovieSearchClickListener movieSearchClickListener) {
        this.movieSearchClickListener = movieSearchClickListener;
    }

    public void setMovieResponseResults(List<MovieResponseResult> movieResponseResults) {
        this.movieResponseResults = movieResponseResults;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchLayoutItemsBinding searchLayoutItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.search_layout_items, parent, false);
        return new MovieSearchViewHolder(searchLayoutItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieSearchViewHolder holder, int position) {
        final MovieResponseResult responseResult = movieResponseResults.get(position);
        holder.searchLayoutItemsBinding.setMovieResponseResult(responseResult);
        holder.setPosterImageView((Context) movieSearchClickListener, responseResult.getPosterPath().toString());



        holder.searchLayoutItemsBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(activity, MovieDetailActivity.class);
                intent.putExtra("id",String.valueOf(responseResult.getId()));
                activity.startActivity(intent);

                activity.overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);*/
                movieSearchClickListener.openMovieDetailsActivity(String.valueOf(responseResult.getId()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return movieResponseResults.size();
    }

    public class MovieSearchViewHolder extends RecyclerView.ViewHolder {

        SearchLayoutItemsBinding searchLayoutItemsBinding;

        public MovieSearchViewHolder(@NonNull SearchLayoutItemsBinding searchLayoutItemsBinding) {
            super(searchLayoutItemsBinding.getRoot());
            this.searchLayoutItemsBinding = searchLayoutItemsBinding;

            RandomTransitionGenerator generator = new RandomTransitionGenerator(1000, new DecelerateInterpolator());
            searchLayoutItemsBinding.posterImageView.setTransitionGenerator(generator);

        }

        public void setPosterImageView(Context context, String posterUrl) {
            Picasso.with(context).load(posterUrl).into(searchLayoutItemsBinding.posterImageView);
        }
    }

    public interface MovieSearchClickListener {
        void openMovieDetailsActivity(String id);
    }
}