package com.example.moviesapp.adapters.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.R;
import com.example.moviesapp.databinding.MovieCreditsLayoutBinding;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

public class MovieCreditsViewHolder extends RecyclerView.ViewHolder {

    public MovieCreditsLayoutBinding movieCreditsLayoutBinding;

    public MovieCreditsViewHolder(@NonNull MovieCreditsLayoutBinding movieCreditsLayoutBinding) {
        super(movieCreditsLayoutBinding.getRoot());
        this.movieCreditsLayoutBinding = movieCreditsLayoutBinding;


    }

    public void setMovieCreditsImageView(Context context, String imageUrl) {
        Picasso.with(context).load(imageUrl).into(movieCreditsLayoutBinding.movieCreditsImageView);
    }
}
