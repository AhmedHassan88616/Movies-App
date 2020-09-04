package com.example.moviesapp.adapters.movie;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codewaves.youtubethumbnailview.ThumbnailLoadingListener;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.example.moviesapp.R;
import com.example.moviesapp.databinding.VideoLayoutBinding;
import com.example.moviesapp.model.movie.MovieVideosResult;
import com.example.moviesapp.ui.VideoPlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MovieVideosViewHolder> {

    MovieVideosListener movieVideosListener;
    List<MovieVideosResult> movieVideosResultList;

    public MovieVideosAdapter(MovieVideosListener movieVideosListener) {
        this.movieVideosListener = movieVideosListener;
    }

    public void setMovieVideosResultList(List<MovieVideosResult> movieVideosResultList) {
        this.movieVideosResultList = movieVideosResultList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieVideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoLayoutBinding videoLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.video_layout, parent, false);
        return new MovieVideosViewHolder(videoLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieVideosViewHolder holder, int position) {

        MovieVideosResult movieVideosResult = movieVideosResultList.get(position);
        holder.videoLayoutBinding.setMovieVideosResult(movieVideosResult);
        String baseUrl = "https://www.youtube.com/watch?v=";
        String videoUrl = baseUrl + movieVideosResult.getKey();
        holder.setThumbnailView(videoUrl);

        holder.videoLayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MovieVideosResult> movieVideosResultArrayList = new ArrayList<>(movieVideosResultList);
                String position = String.valueOf(holder.getAdapterPosition());

                // set animation to open the video
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) movieVideosListener, holder.videoLayoutBinding.videoImageView,
                        ViewCompat.getTransitionName(holder.videoLayoutBinding.videoImageView));
                movieVideosListener.openVideoPlayerActivity(position, movieVideosResultArrayList, compat);

               /* Intent intent = new Intent(activity, VideoPlayerActivity.class);

                // set animation to open the video
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, holder.thumbnailView,
                        ViewCompat.getTransitionName(holder.thumbnailView));

                // put the current video and other videos
                intent.putExtra("position", String.valueOf(holder.getAdapterPosition()));
                intent.putExtra("video", movieVideosResultArrayList);
                activity.startActivity(intent, compat.toBundle());*/


            }
        });
    }

    @Override
    public int getItemCount() {
        return movieVideosResultList.size();
    }


    public class MovieVideosViewHolder extends RecyclerView.ViewHolder {

        VideoLayoutBinding videoLayoutBinding;

        public MovieVideosViewHolder(@NonNull VideoLayoutBinding videoLayoutBinding) {
            super(videoLayoutBinding.getRoot());

            this.videoLayoutBinding = videoLayoutBinding;

        }


        public void setThumbnailView(String videoUrl) {
            videoLayoutBinding.videoImageView.loadThumbnail(videoUrl, new ThumbnailLoadingListener() {
                @Override
                public void onLoadingStarted(@NonNull String url, @NonNull View view) {

                }

                @Override
                public void onLoadingComplete(@NonNull String url, @NonNull View view) {

                }

                @Override
                public void onLoadingCanceled(@NonNull String url, @NonNull View view) {

                }

                @Override
                public void onLoadingFailed(@NonNull String url, @NonNull View view, Throwable error) {
                    Toast.makeText((Activity) movieVideosListener, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public interface MovieVideosListener {
        void openVideoPlayerActivity(String position, ArrayList<MovieVideosResult> movieVideosResultArrayList, ActivityOptionsCompat compat);
    }
}
