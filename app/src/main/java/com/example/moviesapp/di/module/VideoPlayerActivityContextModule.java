package com.example.moviesapp.di.module;


import android.content.Context;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.moviesapp.di.qualifier.ActivityContext;
import com.example.moviesapp.di.scope.ActivityScope;
import com.example.moviesapp.ui.VideoPlayerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoPlayerActivityContextModule {
    private VideoPlayerActivity videoPlayerActivity;

    public Context context;

    public VideoPlayerActivityContextModule(VideoPlayerActivity videoPlayerActivity) {
        this.videoPlayerActivity = videoPlayerActivity;
        context = videoPlayerActivity;
    }

    @Provides
    @ActivityScope
    public VideoPlayerActivity providesVideoPlayerActivity() {
        return videoPlayerActivity;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideContext() {
        return context;
    }

    @Provides
    @ActivityScope
    ViewModelProvider provideViewModelProvider()
    {
        return new ViewModelProvider((ViewModelStoreOwner) context);
    }

}