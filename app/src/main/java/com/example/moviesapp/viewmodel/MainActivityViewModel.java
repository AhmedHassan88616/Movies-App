package com.example.moviesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesapp.AppRepository;
import com.example.moviesapp.client.RetrofitClient;
import com.example.moviesapp.model.movie.MovieResponse;
import com.example.moviesapp.model.movie.MovieResponseResult;
import com.example.moviesapp.model.person.PersonResponse;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private AppRepository repository;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository=new AppRepository(application);
    }

    public LiveData<MovieResponse> getMovieResponseLiveData(RetrofitClient retrofitClient, String finalQuery)
    {
        return repository.getMovieResponseLiveData(retrofitClient,finalQuery);
    }

    public LiveData<PersonResponse>getPersonResponseLiveData(RetrofitClient retrofitClient, String finalQuery)
    {
        return repository.getPersonResponseMutableLiveData(retrofitClient,finalQuery);
    }
}
