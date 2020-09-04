package com.example.moviesapp.di.module;

import com.example.moviesapp.adapters.movie.MovieSearchAdapter;
import com.example.moviesapp.adapters.person.PersonSearchAdapter;
import com.example.moviesapp.di.scope.ActivityScope;
import com.example.moviesapp.ui.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module(includes = {MainActivityContextModule.class})
public class MainActivityAdapterModule {

    @Provides
    @ActivityScope
    public PersonSearchAdapter getPersonSearchAdapter(PersonSearchAdapter.PersonSearchClickListener personSearchClickListener) {
        return new PersonSearchAdapter(personSearchClickListener);
    }

    @Provides
    @ActivityScope
    public PersonSearchAdapter.PersonSearchClickListener getPersonSearchClickListener(MainActivity mainActivity) {
        return mainActivity;
    }

    @Provides
    @ActivityScope
    public MovieSearchAdapter getMovieSearchAdapter(MovieSearchAdapter.MovieSearchClickListener movieSearchClickListener) {
        return new MovieSearchAdapter(movieSearchClickListener);
    }

    @Provides
    @ActivityScope
    public MovieSearchAdapter.MovieSearchClickListener getMovieSearchClickListener(MainActivity mainActivity) {
        return mainActivity;
    }
}
