package com.example.moviesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moviesapp.AppRepository;
import com.example.moviesapp.client.RetrofitClient;
import com.example.moviesapp.model.person.PersonDetails;
import com.example.moviesapp.model.person.PersonImages;

public class PersonDetailActivityViewModel extends AndroidViewModel {
    private AppRepository repository;

    public PersonDetailActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public LiveData<PersonDetails> getPersonDetailsLiveData(RetrofitClient retrofitClient, String movieId) {
        return repository.getPersonDetailsMutableLiveData(retrofitClient, movieId);
    }
    public LiveData<PersonImages>getPersonImagesLiveData(RetrofitClient retrofitClient, String movieId)
    {
        return repository.getPersonImagesMutableLiveData(retrofitClient,movieId);
    }
}
