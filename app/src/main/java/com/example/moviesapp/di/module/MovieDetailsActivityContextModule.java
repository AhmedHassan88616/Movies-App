package com.example.moviesapp.di.module;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.moviesapp.di.qualifier.ActivityContext;
import com.example.moviesapp.di.scope.ActivityScope;
import com.example.moviesapp.ui.MovieDetailActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailsActivityContextModule {
    private MovieDetailActivity movieDetailActivity;

    public Context context;

    public MovieDetailsActivityContextModule(MovieDetailActivity movieDetailActivity) {
        this.movieDetailActivity = movieDetailActivity;
        context = movieDetailActivity;
    }

    @Provides
    @ActivityScope
    public MovieDetailActivity providesMovieDetailActivity() {
        return movieDetailActivity;
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
