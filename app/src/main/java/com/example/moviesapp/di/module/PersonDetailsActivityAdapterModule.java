package com.example.moviesapp.di.module;

import com.example.moviesapp.adapters.person.PersonProfileImageAdapter;
import com.example.moviesapp.di.scope.ActivityScope;
import com.example.moviesapp.ui.PersonDetailsActivity;

import dagger.Module;
import dagger.Provides;

@Module(includes = {PersonDetailsActivityContextModule.class})
public class PersonDetailsActivityAdapterModule {

    @Provides
    @ActivityScope
    public PersonProfileImageAdapter getPersonProfileImageAdapter(PersonProfileImageAdapter.PersonProfileImageListener personProfileImageListener) {
        return new PersonProfileImageAdapter(personProfileImageListener);
    }

    @Provides
    @ActivityScope
    public PersonProfileImageAdapter.PersonProfileImageListener getPersonProfileImageListener(PersonDetailsActivity personDetailsActivity) {
        return personDetailsActivity;
    }


}

