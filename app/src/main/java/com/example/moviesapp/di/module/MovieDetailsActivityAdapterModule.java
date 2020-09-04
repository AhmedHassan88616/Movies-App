package com.example.moviesapp.di.module;


import com.example.moviesapp.adapters.movie.MovieCreditsCastAdapter;
import com.example.moviesapp.adapters.movie.MovieCreditsCrewAdapter;
import com.example.moviesapp.adapters.movie.MoviePosterImageAdapter;
import com.example.moviesapp.adapters.movie.MovieProductionCompaniesAdapter;
import com.example.moviesapp.adapters.movie.MovieVideosAdapter;
import com.example.moviesapp.di.scope.ActivityScope;
import com.example.moviesapp.ui.MovieDetailActivity;

import dagger.Module;
import dagger.Provides;

@Module(includes = {MovieDetailsActivityContextModule.class})
public class MovieDetailsActivityAdapterModule {


    @Provides
    @ActivityScope
    public MovieProductionCompaniesAdapter getMovieProductionCompaniesAdapter(MovieDetailActivity movieDetailActivity) {
        return new MovieProductionCompaniesAdapter(movieDetailActivity);
    }


    @Provides
    @ActivityScope
    public MovieCreditsCastAdapter getMovieCreditsCastAdapter(MovieDetailActivity movieDetailActivity) {
        return new MovieCreditsCastAdapter(movieDetailActivity);
    }



    @Provides
    @ActivityScope
    public MovieCreditsCrewAdapter getMovieCreditsCrewAdapter(MovieDetailActivity movieDetailActivity) {
        return new MovieCreditsCrewAdapter(movieDetailActivity);
    }



    @Provides
    @ActivityScope
    public MoviePosterImageAdapter getMoviePosterImageAdapter(MovieDetailActivity movieDetailActivity)
    {
        return new MoviePosterImageAdapter(movieDetailActivity);
    }

    @Provides
    @ActivityScope
    public MovieVideosAdapter getMovieVideosAdapter(MovieDetailActivity movieDetailActivity)
    {
        return new MovieVideosAdapter(movieDetailActivity);
    }
}
