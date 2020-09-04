package com.example.moviesapp;

import android.app.Application;
import android.graphics.Movie;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesapp.client.RetrofitClient;
import com.example.moviesapp.model.movie.MovieDetails;
import com.example.moviesapp.model.movie.MovieImages;
import com.example.moviesapp.model.movie.MovieResponse;
import com.example.moviesapp.model.movie.MovieResponseResult;
import com.example.moviesapp.model.movie.MovieVideos;
import com.example.moviesapp.model.movie.credits.MovieCredits;
import com.example.moviesapp.model.person.PersonDetails;
import com.example.moviesapp.model.person.PersonImages;
import com.example.moviesapp.model.person.PersonResponse;

import java.util.List;
import java.util.Observer;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AppRepository {

    private Application application;
    private MutableLiveData<MovieResponse> movieResponseLiveData;
    private MutableLiveData<PersonResponse> personResponseMutableLiveData;
    private MutableLiveData<PersonDetails> personDetailsMutableLiveData;
    private MutableLiveData<PersonImages> personImagesMutableLiveData;
    private MutableLiveData<MovieDetails> movieDetailsMutableLiveData;
    private MutableLiveData<MovieVideos> movieVideosMutableLiveData;
    private MutableLiveData<MovieImages> movieImagesMutableLiveData;
    private MutableLiveData<MovieCredits> movieCreditsMutableLiveData;

    @Inject
    public AppRepository(Application application) {
        this.application = application;
        movieResponseLiveData = new MutableLiveData<>();
        personResponseMutableLiveData = new MutableLiveData<>();
        personDetailsMutableLiveData = new MutableLiveData<>();
        personImagesMutableLiveData = new MutableLiveData<>();
        movieDetailsMutableLiveData = new MutableLiveData<>();
        movieVideosMutableLiveData = new MutableLiveData<>();
        movieImagesMutableLiveData = new MutableLiveData<>();
        movieCreditsMutableLiveData = new MutableLiveData<>();
    }


    public LiveData<MovieResponse> getMovieResponseLiveData(RetrofitClient retrofitClient, String finalQuery) {


        final Single<MovieResponse> movieResponseSingle = retrofitClient.getMoviesByQuery(finalQuery);
        movieResponseSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MovieResponse movieResponse) {
                        if (movieResponse != null) {
                            movieResponseLiveData.setValue(movieResponse);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "onFailure: asd" + e.getMessage());

                    }
                });
        return movieResponseLiveData;
    }

    public MutableLiveData<PersonResponse> getPersonResponseMutableLiveData(RetrofitClient retrofitClient, String finalQuery) {

        final Single<PersonResponse> personResponseSingle = retrofitClient.getPersonsByQuery(finalQuery);
        personResponseSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PersonResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(PersonResponse personResponse) {
                        if (personResponse != null) {
                            personResponseMutableLiveData.setValue(personResponse);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "onFailure: asd" + e.getMessage());
                    }
                });

        return personResponseMutableLiveData;
    }

    public MutableLiveData<PersonDetails> getPersonDetailsMutableLiveData(RetrofitClient retrofitClient, String id) {

        final Single<PersonDetails> personDetailsSingle = retrofitClient.getPersonDetailsById(id);
        personDetailsSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PersonDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(PersonDetails personDetails) {
                        if (personDetails != null) {
                            personDetailsMutableLiveData.setValue(personDetails);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "onFailure: asd" + e.getMessage());

                    }
                });
        return personDetailsMutableLiveData;
    }

    public MutableLiveData<PersonImages> getPersonImagesMutableLiveData(RetrofitClient retrofitClient, String id) {

        final Single<PersonImages> personImagesSingle = retrofitClient.getPersonImagesById(id);
        personImagesSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PersonImages>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(PersonImages personImages) {
                        if (personImages != null) {
                            personImagesMutableLiveData.setValue(personImages);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "onFailure: asd" + e.getMessage());
                    }
                });
        return personImagesMutableLiveData;
    }

    public MutableLiveData<MovieDetails> getMovieDetailsMutableLiveData(RetrofitClient retrofitClient, String movieId) {
        Single<MovieDetails> movieDetailsSingle = retrofitClient.getMovieDetailsById(movieId);
        movieDetailsSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MovieDetails movieDetails) {
                        if (movieDetails != null) {
                            movieDetailsMutableLiveData.setValue(movieDetails);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "onFailure: asd" + e.getMessage());

                    }
                });

        return movieDetailsMutableLiveData;
    }

    public MutableLiveData<MovieVideos> getMovieVideosMutableLiveData(RetrofitClient retrofitClient, final String movieId) {
        Single<MovieVideos> movieVideosSingle = retrofitClient.getMovieVideosById(movieId);
        movieVideosSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieVideos>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MovieVideos movieVideos) {
                        if (movieVideos != null) {
                            movieVideosMutableLiveData.setValue(movieVideos);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "onFailure: asd" + e.getMessage());

                    }
                });
        return movieVideosMutableLiveData;
    }


    public MutableLiveData<MovieImages> getMovieImagesMutableLiveData(RetrofitClient retrofitClient, final String movieId) {
        Single<MovieImages> movieImagesSingle = retrofitClient.getMovieImagesById(movieId);
        movieImagesSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieImages>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MovieImages movieImages) {
                        if (movieImages != null) {
                            movieImagesMutableLiveData.setValue(movieImages);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "onFailure: asd" + e.getMessage());

                    }
                });
        return movieImagesMutableLiveData;
    }

    public MutableLiveData<MovieCredits> getMovieCreditsMutableLiveData(RetrofitClient retrofitClient, final String movieId) {
        Single<MovieCredits> movieCreditsSingle = retrofitClient.getMovieCreditsById(movieId);
        movieCreditsSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieCredits>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MovieCredits movieCredits) {
                        if (movieCredits != null) {
                            movieCreditsMutableLiveData.setValue(movieCredits);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "onFailure: asd" + e.getMessage());

                    }
                });
        return movieCreditsMutableLiveData;
    }

}
