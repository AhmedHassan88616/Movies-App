package com.example.moviesapp.model.movie.credits;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieCredits {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cast")
    @Expose
    private List<MovieCreditsCast> movieCreditsCast = null;
    @SerializedName("crew")
    @Expose
    private List<MovieCreditsCrew> movieCreditsCrew = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieCreditsCast> getMovieCreditsCast() {
        return movieCreditsCast;
    }

    public void setMovieCreditsCast(List<MovieCreditsCast> movieCreditsCast) {
        this.movieCreditsCast = movieCreditsCast;
    }

    public List<MovieCreditsCrew> getMovieCreditsCrew() {
        return movieCreditsCrew;
    }

    public void setMovieCreditsCrew(List<MovieCreditsCrew> movieCreditsCrew) {
        this.movieCreditsCrew = movieCreditsCrew;
    }

}