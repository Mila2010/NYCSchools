package com.example.mila.nycschools.presenter;

import com.example.mila.nycschools.ViewPresenterContract;
import com.example.mila.nycschools.model.NYCSchools;
import com.example.mila.nycschools.model.SATResponse;
import com.example.mila.nycschools.model.SchoolResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mila.nycschools.model.Constants.APP_TOKEN;
import static com.example.mila.nycschools.model.Constants.BASE_URL;

/**
 * Created by mila on 3/20/18.
 */

public class Presenter implements ViewPresenterContract.Presenter {

    private ViewPresenterContract.View mView;
    private Observable<List<NYCSchools>> mNYCSchools;
    private CompositeDisposable mDisposables = new CompositeDisposable();

    public Presenter(ViewPresenterContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void connect() {

        getNYCSchools();

        Disposable networkCall = mNYCSchools.subscribeWith(new DisposableObserver<List<NYCSchools>>() {

            @Override
            public void onNext(List<NYCSchools> nycSchools) {
                mView.showList(nycSchools);
            }

            @Override
            public void onError(Throwable e) {
                mView.showError();

            }

            @Override
            public void onComplete() {

            }
        });
        mDisposables.add(networkCall);

    }

    private void getNYCSchools() {

        Retrofit repo = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        Observable<List<SchoolResponse>> schools = repo.create(ApiService.class)
                .getSchools(APP_TOKEN)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


        Observable<List<SATResponse>> sat = repo.create(ApiService.class)
                .getSAT(APP_TOKEN)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


        mNYCSchools = Observable.zip(schools, sat, (val1, val2) -> {

            return combineData(val1, val2);
        });
    }

    private ArrayList<NYCSchools> combineData(List<SchoolResponse> schools, List<SATResponse> sat) {

        Map<String, List<SchoolResponse>> school = schools.stream()
                .collect(Collectors.groupingBy(SchoolResponse::getId));
        return sat.stream()
                .flatMap(two -> school.getOrDefault(two.getId(), Collections.emptyList())
                        .stream().map(one -> new NYCSchools(two, one)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public CompositeDisposable getmDisposable() {return mDisposables;}

    @Override
    public void setClickListener() {
        Disposable onClick = mView.getmAdapter().getClickEvent().subscribe(nycSchool -> mView.showSchool(nycSchool));
        mDisposables.add(onClick);
    }
}
