package com.example.mila.nycschools.presenter;

import com.example.mila.nycschools.model.SATResponse;
import com.example.mila.nycschools.model.SchoolResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mila on 3/20/18.
 */

public interface ApiService {

    @GET("97mf-9njv.json")
    Observable<List<SchoolResponse>> getSchools(@Query("$$app_token") String appToken);

    @GET("97mf-9njv.json")
    Call<List<SchoolResponse>> getSchool(@Query("$$app_token") String appToken);


    @GET("734v-jeq5.json")
    Observable<List<SATResponse>> getSAT(@Query("$$app_token") String appToken);
}
