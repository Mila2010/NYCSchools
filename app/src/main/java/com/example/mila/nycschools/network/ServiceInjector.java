package com.example.mila.nycschools.network;

import com.example.mila.nycschools.model.AppConstants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mila.nycschools.model.AppConstants.APP_TOKEN;


/**
 * Created by mila on 4/5/18.
 */

public class ServiceInjector {


    public static ApiService inject(){

        Retrofit repo = new Retrofit.Builder()
                .baseUrl(APP_TOKEN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return repo.create(ApiService.class);

    }
}
