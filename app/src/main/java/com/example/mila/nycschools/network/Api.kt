package com.example.mila.nycschools.network

import com.example.mila.nycschools.model.SATResponse
import com.example.mila.nycschools.model.SchoolResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by mila on 4/5/18.
 */
interface Api {

    @GET("97mf-9njv.json")
    fun getSchools( @Query("\$\$app_token")appToken:String): Observable<List<SchoolResponse>>

    @GET("734v-jeq5.json")
    fun getSAT( @Query("\$\$app_token")appToken:String): Observable<List<SATResponse>>
}