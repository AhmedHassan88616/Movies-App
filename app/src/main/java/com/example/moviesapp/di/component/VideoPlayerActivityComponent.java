package com.example.moviesapp.di.component;

import com.example.moviesapp.di.module.VideoPlayerActivityAdapterModule;
import com.example.moviesapp.di.scope.ActivityScope;
import com.example.moviesapp.ui.VideoPlayerActivity;

import dagger.Component;

@ActivityScope
@Component(modules = VideoPlayerActivityAdapterModule.class,dependencies = ApplicationComponent.class)
public interface VideoPlayerActivityComponent {
    void injectVideoPlayerActivity(VideoPlayerActivity videoPlayerActivity);
}