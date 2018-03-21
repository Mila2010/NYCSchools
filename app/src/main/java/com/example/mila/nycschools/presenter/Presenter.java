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
       // Declaring observables
        getNYCSchools();

       // Subscribing to observable with Disposable Observer which return disposible subscription
        Disposable networkCall = mNYCSchools.subscribeWith(new DisposableObserver<List<NYCSchools>>() {

            @Override
            public void onNext(List<NYCSchools> nycSchools) {
                //OnNext called when mNYCSchools observable emits list of NYCSchool objects,
                // then passing list of schools to View to be shown on the screen
                mView.showList(nycSchools);
            }

            @Override
            public void onError(Throwable e) {
                //OnError called when mNYCSchools observable had a trouble to emit list of NYCSchool objects (no internet connection for example),
                // then asking View to show error on screen
                mView.showError();

            }

            @Override
            public void onComplete() {

            }
        });

        //adding Disposable subscription created above to composite disposable object to dispose subscription later on to avoid memory leakage
        mDisposables.add(networkCall);

    }

    private void getNYCSchools() {

        Retrofit repo = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //declaration of schools observable which emits list of schools with general info when receiving it from network
        Observable<List<SchoolResponse>> schools = repo.create(ApiService.class)
                .getSchools(APP_TOKEN)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        //declaration of sat observable which emits list of schools with sat info when receiving it from network
        Observable<List<SATResponse>> sat = repo.create(ApiService.class)
                .getSAT(APP_TOKEN)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
       //combining sat and schools observables and converting to resulting List of NYCSchool objects which contains general info and sat
        mNYCSchools = Observable.zip(schools, sat, this::combineData);
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
        //View has an access to click event observable which declared in School Adapter,
        //subscribing to listen for click event and if event appear ask View to show information about school which being clicked
        Disposable onClick = mView.getmAdapter().getClickEvent().subscribe(nycSchool -> mView.showSchool(nycSchool));

        //adding Disposable subscription created above to composite disposable object to dispose subscription later on to avoid memory leakage
        mDisposables.add(onClick);
    }
}
