package com.example.moviesapp.adapters.movie;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.R;
import com.example.moviesapp.adapters.viewholders.ImageViewHolder;
import com.example.moviesapp.databinding.ProfileImagesLayoutBinding;
import com.example.moviesapp.model.movie.MovieBackdropsandPosters;
import com.example.moviesapp.model.movie.MovieImages;
import com.example.moviesapp.model.person.PersonImageProfile;
import com.example.moviesapp.ui.ImageViewerActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviePosterImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    MoviePosterImageListener moviePosterImageListener;
    List<MovieBackdropsandPosters> movieBackdropsandPostersList;

    public MoviePosterImageAdapter(MoviePosterImageListener moviePosterImageListener) {
        this.moviePosterImageListener = moviePosterImageListener;
    }

    public void setMovieBackdropsandPostersList(List<MovieBackdropsandPosters> movieBackdropsandPostersList) {
        this.movieBackdropsandPostersList = movieBackdropsandPostersList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProfileImagesLayoutBinding profileImagesLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.profile_images_layout, parent, false);

        return new ImageViewHolder(profileImagesLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {

        final MovieBackdropsandPosters movieBackdropsandPoster = movieBackdropsandPostersList.get(position);
        holder.profileImagesLayoutBinding.setMovieBackdropsandPoster(movieBackdropsandPoster);
        holder.setProfileImage((Activity) moviePosterImageListener, movieBackdropsandPoster.getFilePath());
        holder.profileImagesLayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent imageViewerIntent = new Intent(activity, ImageViewerActivity.class);
                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(activity, holder.profileImage, ViewCompat.getTransitionName(holder.profileImage));
                imageViewerIntent.putExtra("image_url", movieBackdropsandPoster.getFilePath());
                activity.startActivity(imageViewerIntent, compat.toBundle());*/

                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation((Activity) moviePosterImageListener, holder.profileImagesLayoutBinding.profileImage, ViewCompat.getTransitionName(holder.profileImagesLayoutBinding.profileImage));
                String imageUrl = movieBackdropsandPoster.getFilePath();
                moviePosterImageListener.openImageViewerActivity(imageUrl, compat);

            }
        });
    }

    @Override
    public int getItemCount() {
        return movieBackdropsandPostersList.size();
    }

    public interface MoviePosterImageListener {
        void openImageViewerActivity(String imageUrl, ActivityOptions compat);
    }
}
