package com.example.moviesapp.model.person;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonImages {

    @SerializedName("profiles")
    @Expose
    private List<PersonImageProfile> personImageProfiles = null;
    @SerializedName("id")
    @Expose
    private Integer id;

    public List<PersonImageProfile> getPersonImageProfiles() {
        return personImageProfiles;
    }

    public void setPersonImageProfiles(List<PersonImageProfile> personImageProfiles) {
        this.personImageProfiles = personImageProfiles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}