package com.example.moviesapp.di.component;

import com.example.moviesapp.di.scope.ActivityScope;
import com.example.moviesapp.ui.ImageViewerActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface ImageViewerActivityComponent {
    void injectImageViewerActivity(ImageViewerActivity imageViewerActivity);
}