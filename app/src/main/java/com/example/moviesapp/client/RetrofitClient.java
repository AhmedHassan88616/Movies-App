package com.example.moviesapp.client;

import com.example.moviesapp.model.movie.MovieDetails;
import com.example.moviesapp.model.movie.MovieImages;
import com.example.moviesapp.model.movie.MovieResponse;
import com.example.moviesapp.model.movie.MovieVideos;
import com.example.moviesapp.model.movie.credits.MovieCredits;
import com.example.moviesapp.model.person.PersonDetails;
import com.example.moviesapp.model.person.PersonImages;
import com.example.moviesapp.model.person.PersonResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit;

    private final String API_KEY = "2c38b1309e66446c07f56e37eb052291";
    private RetrofitService retrofitService;
    private static RetrofitClient INSTANCE;

    @Inject
    public RetrofitClient(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    public static Retrofit getClient() {
        if (null == retrofit) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private RetrofitClient() {

        retrofitService = getClient().create(RetrofitService.class);
    }

    public static RetrofitClient getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new RetrofitClient();
        }
        return INSTANCE;
    }

    public Single<MovieResponse> getMoviesByQuery(String finalQuery) {
        return retrofitService.getMoviesByQuery(API_KEY, finalQuery);
    }

    public Single<PersonResponse> getPersonsByQuery(String finalQuery) {
        return retrofitService.getPersonsByQuery(API_KEY, finalQuery);
    }

    public Single<MovieVideos> getMovieVideosById(String movieId) {
        return retrofitService.getMovieVideosById(movieId, API_KEY);
    }

    public Single<MovieImages> getMovieImagesById(String movieId) {
        return retrofitService.getMovieImagesById(movieId, API_KEY);
    }

    public Single<MovieDetails> getMovieDetailsById(String movieId) {
        return retrofitService.getMovieDetailsById(movieId, API_KEY);

    }

    public Single<MovieCredits> getMovieCreditsById(String movieId) {
        return retrofitService.getMovieCreditsById(movieId, API_KEY);

    }


    public Single<PersonDetails> getPersonDetailsById(String id) {
        return retrofitService.getPersonDetailsById(id, API_KEY);
    }

    public Single<PersonImages> getPersonImagesById(String id) {
        return retrofitService.getPersonImagesById(id, API_KEY);
    }
}
