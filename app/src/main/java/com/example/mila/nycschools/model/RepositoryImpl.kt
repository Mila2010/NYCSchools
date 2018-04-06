package com.example.mila.nycschools.model

import com.example.mila.nycschools.model.AppConstants.APP_TOKEN
import com.example.mila.nycschools.network.Injector
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.ArrayList
import java.util.function.Function
import java.util.function.Supplier
import java.util.stream.Collectors

/**
 * Created by mila on 4/4/18.
 */
open class RepositoryImpl private constructor(){

    private object Holder { val INSTANCE = RepositoryImpl() }
    private val mApiService = Injector.getApi()
    private var mNYCSchools:Observable<List<NYCSchools>> ?= null
    open fun getSchools():Observable<List<NYCSchools>>? = mNYCSchools


    companion object {

        @JvmStatic
        val INSTANCE: RepositoryImpl by lazy { Holder.INSTANCE }

    }

    open fun initNYCSchools() {

        val schools = mApiService
                .getSchools(APP_TOKEN)

        //declaration of sat observable which emits list of schools with sat info when receiving it from network
        val sat = mApiService
                .getSAT(APP_TOKEN)

        //combining sat and schools observables and converting to resulting List of NYCSchool objects which contains general info and sat
        mNYCSchools = Observable.zip(schools, sat, BiFunction { sc, s -> combineData(sc,s) })

    }

    private fun combineData(schools: List<SchoolResponse>, sat: List<SATResponse>): List<NYCSchools> {

        val school = schools.stream()
                .collect(Collectors.groupingBy(Function<SchoolResponse, String> { it.getId() }))
        return sat.stream()
                .flatMap { two ->
                    (school as java.util.Map<String, List<SchoolResponse>>).getOrDefault(two.id, emptyList())
                            .stream().map { one -> NYCSchools(two, one) }
                }
                .collect(Collectors.toCollection(Supplier<ArrayList<NYCSchools>> { ArrayList() }))
    }



}