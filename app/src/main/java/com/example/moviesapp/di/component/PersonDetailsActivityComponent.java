package com.example.moviesapp.di.component;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.example.moviesapp.di.module.PersonDetailsActivityAdapterModule;
import com.example.moviesapp.di.qualifier.ActivityContext;
import com.example.moviesapp.di.scope.ActivityScope;
import com.example.moviesapp.ui.PersonDetailsActivity;

import dagger.Component;

@ActivityScope
@Component(modules = PersonDetailsActivityAdapterModule.class, dependencies = ApplicationComponent.class)
public interface PersonDetailsActivityComponent {

    @ActivityContext
    Context getContext();

    ViewModelProvider getViewModelProvider();

    void injectPersonDetailsActivity(PersonDetailsActivity personDetailsActivity);
}