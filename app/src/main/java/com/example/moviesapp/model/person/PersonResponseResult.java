package com.example.moviesapp.model.person;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonResponseResult {

    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("known_for_department")
    @Expose
    private String knownForDepartment;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("profile_path")
    @Expose
    private Object profilePath;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("known_for")
    @Expose
    private List<PersonResponseResultsKnownFor> personResponseResultsKnownFor = null;
    @SerializedName("gender")
    @Expose
    private Integer gender;

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public void setKnownForDepartment(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getProfilePath() {
        // create a baseurl for the poster path
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl + profilePath;
    }

    public void setProfilePath(Object profilePath) {
        this.profilePath = profilePath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public List<PersonResponseResultsKnownFor> getPersonResponseResultsKnownFor() {
        return personResponseResultsKnownFor;
    }

    public void setPersonResponseResultsKnownFor(List<PersonResponseResultsKnownFor> personResponseResultsKnownFor) {
        this.personResponseResultsKnownFor = personResponseResultsKnownFor;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

}