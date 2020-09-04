package com.example.moviesapp.di.component;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.example.moviesapp.AppRepository;
import com.example.moviesapp.app.MyApplication;
import com.example.moviesapp.client.RetrofitService;
import com.example.moviesapp.di.module.ContextModule;
import com.example.moviesapp.di.module.RetrofitModule;
import com.example.moviesapp.di.qualifier.ApplicationContext;
import com.example.moviesapp.di.scope.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, RetrofitModule.class})
public interface ApplicationComponent {

    public RetrofitService getRetrofitService();

    public AppRepository getAppRepository();

    @ApplicationContext
    public Context getContext();

    public void injectApplication(MyApplication myApplication);
}