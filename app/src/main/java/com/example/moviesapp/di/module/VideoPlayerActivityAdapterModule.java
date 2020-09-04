package com.example.moviesapp.di.module;


import com.example.moviesapp.adapters.movie.MovieVideosAdapter;
import com.example.moviesapp.di.scope.ActivityScope;
import com.example.moviesapp.ui.VideoPlayerActivity;

import dagger.Module;
import dagger.Provides;

@Module(includes = {VideoPlayerActivityContextModule.class})
public class VideoPlayerActivityAdapterModule {

    @Provides
    @ActivityScope
    public MovieVideosAdapter getMovieVideosAdapter(MovieVideosAdapter.MovieVideosListener movieVideosListener) {
        return new MovieVideosAdapter(movieVideosListener);
    }

    @Provides
    @ActivityScope
    public MovieVideosAdapter.MovieVideosListener getMovieVideosListener(VideoPlayerActivity videoPlayerActivity) {
        return videoPlayerActivity;
    }


}
