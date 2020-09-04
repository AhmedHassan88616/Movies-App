package com.example.moviesapp.di.component;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.example.moviesapp.di.module.MainActivityAdapterModule;
import com.example.moviesapp.di.qualifier.ActivityContext;
import com.example.moviesapp.di.scope.ActivityScope;
import com.example.moviesapp.ui.MainActivity;

import dagger.Component;

@ActivityScope
@Component(modules = MainActivityAdapterModule.class, dependencies = ApplicationComponent.class)
public interface MainActivityComponent {

    @ActivityContext
    Context getContext();

    ViewModelProvider getViewModelProvider();

    void injectMainActivity(MainActivity mainActivity);
}