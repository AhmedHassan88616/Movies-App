package com.example.moviesapp.di.module;


import android.content.Context;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.moviesapp.di.qualifier.ActivityContext;
import com.example.moviesapp.di.scope.ActivityScope;
import com.example.moviesapp.ui.PersonDetailsActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class PersonDetailsActivityContextModule {
    private PersonDetailsActivity personDetailsActivity;

    public Context context;

    public PersonDetailsActivityContextModule(PersonDetailsActivity personDetailsActivity) {
        this.personDetailsActivity = personDetailsActivity;
        context = personDetailsActivity;
    }

    @Provides
    @ActivityScope
    public PersonDetailsActivity providesPersonDetailActivity() {
        return personDetailsActivity;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideContext() {
        return context;
    }

    @Provides
    @ActivityScope
    ViewModelProvider provideViewModelProvider()
    {
        return new ViewModelProvider((ViewModelStoreOwner) context);
    }

}