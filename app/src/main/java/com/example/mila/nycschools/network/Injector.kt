package com.example.mila.nycschools.network

import com.example.mila.nycschools.model.AppConstants.BASE_URL
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by mila on 4/5/18.
 */
object Injector {


    fun getApi(): Api {
        val repo = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return repo.create(Api::class.java)
    }


}