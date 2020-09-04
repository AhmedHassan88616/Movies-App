package com.example.moviesapp.model.movie;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieImages {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("backdrops")
    @Expose
    private List<MovieBackdropsandPosters> backdrops = null;
    @SerializedName("posters")
    @Expose
    private List<MovieBackdropsandPosters> moviePosters = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieBackdropsandPosters> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<MovieBackdropsandPosters> backdrops) {
        this.backdrops = backdrops;
    }

    public List<MovieBackdropsandPosters> getMoviePosters() {
        return moviePosters;
    }

    public void setMoviePosters(List<MovieBackdropsandPosters> moviePosters) {
        this.moviePosters = moviePosters;
    }
}