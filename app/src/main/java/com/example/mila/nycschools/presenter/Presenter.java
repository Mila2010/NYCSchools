package com.example.mila.nycschools.presenter;

import com.example.mila.nycschools.ViewPresenterContract;
import com.example.mila.nycschools.model.RepositoryImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mila on 3/20/18.
 */

public class Presenter implements ViewPresenterContract.Presenter {

    private ViewPresenterContract.View mView;
    private CompositeDisposable mDisposables = new CompositeDisposable();
    private RepositoryImpl mModel;

    public Presenter(ViewPresenterContract.View view, RepositoryImpl model) {
        this.mView = view;
        mView.setPresenter(this);
        this.mModel = model;
    }

    @Override
    public void connect() {
      mModel.initNYCSchools();
       // Subscribing to observable with Disposable Observer which return disposible subscription

        Disposable networkCall = mModel.getSchools().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(schools -> {mView.showList(schools);}
                ,throwable -> {mView.showError(); });

        //adding Disposable subscription created above to composite disposable object to dispose subscription later on to avoid memory leakage
        mDisposables.add(networkCall);

    }


    @Override
    public CompositeDisposable getmDisposable() {return mDisposables;}

    @Override
    public void setClickListener() {
        //View has an access to click event observable which declared in School Adapter,
        //subscribing to listen for click event and if event appear ask View to show information about school which being clicked
        Disposable onClick = mView.getmAdapter().clickEvent().subscribe(nycSchool -> mView.showSchool(nycSchool));
        //adding Disposable subscription created above to composite disposable object to dispose subscription later on to avoid memory leakage
        mDisposables.add(onClick);
    }
}
