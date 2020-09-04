package com.example.moviesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moviesapp.AppRepository;
import com.example.moviesapp.client.RetrofitClient;
import com.example.moviesapp.model.movie.MovieDetails;
import com.example.moviesapp.model.movie.MovieImages;
import com.example.moviesapp.model.movie.MovieVideos;
import com.example.moviesapp.model.movie.credits.MovieCredits;
import com.example.moviesapp.model.person.PersonDetails;

public class MovieDetailActivityViewModel extends AndroidViewModel {
    private AppRepository repository;

    public MovieDetailActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public LiveData<MovieDetails> getMovieDetailsLiveData(RetrofitClient retrofitClient, String movieId) {
        return repository.getMovieDetailsMutableLiveData(retrofitClient, movieId);
    }
    public LiveData<MovieVideos> getMovieVideosLiveData(RetrofitClient retrofitClient, String movieId) {
        return repository.getMovieVideosMutableLiveData(retrofitClient, movieId);
    }

    public LiveData<MovieImages> getMovieImagesLiveData(RetrofitClient retrofitClient, String movieId) {
        return repository.getMovieImagesMutableLiveData(retrofitClient, movieId);
    }

    public LiveData<MovieCredits> getMovieCreditsLiveData(RetrofitClient retrofitClient, String movieId) {
        return repository.getMovieCreditsMutableLiveData(retrofitClient, movieId);
    }
}
