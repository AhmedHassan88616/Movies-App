package com.example.moviesapp.client;

import androidx.lifecycle.LiveData;

import com.example.moviesapp.model.movie.MovieDetails;
import com.example.moviesapp.model.movie.MovieImages;
import com.example.moviesapp.model.movie.MovieResponse;
import com.example.moviesapp.model.movie.MovieVideos;
import com.example.moviesapp.model.movie.credits.MovieCredits;
import com.example.moviesapp.model.person.PersonDetails;
import com.example.moviesapp.model.person.PersonImages;
import com.example.moviesapp.model.person.PersonResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("search/movie")
    Single<MovieResponse> getMoviesByQuery(@Query("api_key") String api_key, @Query("query") String query);


    @GET("movie/{movie_id}")
    Single<MovieDetails> getMovieDetailsById(@Path("movie_id") String movieId, @Query("api_key") String apiKey);


    @GET("movie/{movie_id}/credits")
    Single<MovieCredits> getMovieCreditsById(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/images")
    Single<MovieImages> getMovieImagesById(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Single<MovieVideos> getMovieVideosById(@Path("movie_id") String movieId, @Query("api_key") String apiKey);


    @GET("search/person")
    Single<PersonResponse> getPersonsByQuery(@Query("api_key") String api_key, @Query("query") String query);

    @GET("person/{person_id}")
    Single<PersonDetails> getPersonDetailsById(@Path("person_id") String personId, @Query("api_key") String apiKey);

    @GET("person/{person_id}/images")
    Single<PersonImages> getPersonImagesById(@Path("person_id") String personId, @Query("api_key") String apiKey);


}
