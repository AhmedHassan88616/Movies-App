package com.example.moviesapp.di.component;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.example.moviesapp.di.module.MovieDetailsActivityAdapterModule;
import com.example.moviesapp.di.qualifier.ActivityContext;
import com.example.moviesapp.di.scope.ActivityScope;
import com.example.moviesapp.ui.MovieDetailActivity;

import dagger.Component;

@ActivityScope
@Component(modules = MovieDetailsActivityAdapterModule.class, dependencies = ApplicationComponent.class)
public interface MovieDetailsActivityComponent {

    @ActivityContext
    Context getContext();

    @ActivityScope
    ViewModelProvider getViewModelProvider();

    void injectMovieDetailsActivity(MovieDetailActivity movieDetailActivity);
}