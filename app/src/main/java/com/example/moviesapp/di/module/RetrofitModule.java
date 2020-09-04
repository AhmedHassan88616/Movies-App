package com.example.moviesapp.di.module;

import com.example.moviesapp.client.RetrofitService;
import com.example.moviesapp.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    @Provides
    @ApplicationScope
    RetrofitService getApiInterface(Retrofit retrofit) {
        return retrofit.create(RetrofitService.class);
    }

    @Provides
    @ApplicationScope
    Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


}