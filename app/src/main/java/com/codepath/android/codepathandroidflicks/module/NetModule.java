package com.codepath.android.codepathandroidflicks.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class NetModule {

    @Provides
    @Singleton
    OkHttpClient provideOKHttpClient(){
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new GsonBuilder().create();
    }

}
