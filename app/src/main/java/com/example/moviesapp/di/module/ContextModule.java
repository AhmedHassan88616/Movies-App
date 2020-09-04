package com.example.moviesapp.di.module;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.moviesapp.AppRepository;
import com.example.moviesapp.di.qualifier.ApplicationContext;
import com.example.moviesapp.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }



    @Provides
    @ApplicationScope
    AppRepository provideAppRepository()
    {
        return new AppRepository((Application) context.getApplicationContext());
    }
    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context provideContext() {
        return context;
    }


}