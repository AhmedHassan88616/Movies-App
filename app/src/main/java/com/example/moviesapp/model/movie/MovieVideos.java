package com.example.moviesapp.model.movie;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieVideos {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MovieVideosResult> movieVideosResults = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieVideosResult> getMovieVideosResults() {
        return movieVideosResults;
    }

    public void setMovieVideosResults(List<MovieVideosResult> movieVideosResults) {
        this.movieVideosResults = movieVideosResults;
    }

}